package net.kingborn.erp.uc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.erp.interceptor.CurrentUser;
import net.kingborn.erp.rc.service.LogService;
import net.kingborn.erp.uc.model.User;
import net.kingborn.erp.uc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 切换是否启用
 */
@Command
public class CUserSwitchActive extends BaseCommand {

    @Autowired
    private UserService userService;
    @Autowired
    private LogService logService;

    @Param(required = true)
    private long userId;
    @Param
    private CurrentUser currentUser;

    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        User user = userService.getById(userId);
        Assert.notNull(user, "ID为【" + userId + "】的用户不存在！");

        user.setActive(!user.isActive());
        userService.saveOrUpdate(user);

        // 记录日志
        if (user.isActive()) {
            logService.logUserActive(currentUser.getId(), user.getUsername());
        } else {
            logService.logUserDeactive(currentUser.getId(), user.getUsername());
        }

        data.put("active", user.isActive());
    }
}
