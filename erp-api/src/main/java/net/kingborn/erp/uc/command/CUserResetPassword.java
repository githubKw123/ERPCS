package net.kingborn.erp.uc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.erp.interceptor.CurrentUser;
import net.kingborn.erp.rc.service.LogService;
import net.kingborn.erp.uc.model.User;
import net.kingborn.erp.uc.service.UserService;
import net.kingborn.erp.util.SimpleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 重置密码
 */
@Command
public class CUserResetPassword extends BaseCommand {

    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private LogService logService;

    @Param(required = true)
    private long userId;
    @Param(required = true)
    private String password;
    @Param
    private CurrentUser currentUser;

    @Override
    protected void init() throws Exception {
        Assert.notFalse(SimpleValidator.validatePassword(password), "密码格式不正确！");
    }

    @Override
    protected void doCommand() throws Exception {
        User user = userService.getById(userId);
        Assert.notNull(user, "ID为【" + userId + "】的用户不存在！");

        user.setPassword(encoder.encode(password));
        userService.saveOrUpdate(user);

        // 记录日志
        logService.logUserResetPassword(currentUser.getId(), user.getUsername());
    }
}
