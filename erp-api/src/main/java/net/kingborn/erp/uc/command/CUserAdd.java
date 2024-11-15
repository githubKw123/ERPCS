package net.kingborn.erp.uc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.core.exception.BizException;
import net.kingborn.erp.interceptor.CurrentUser;
import net.kingborn.erp.rc.service.LogService;
import net.kingborn.erp.util.SimpleValidator;
import net.kingborn.erp.uc.model.User;
import net.kingborn.erp.uc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 新增用户
 */
@Command
public class CUserAdd extends BaseCommand {

    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private LogService logService;

    @Param(required = true)
    private User user;
    @Param
    private CurrentUser currentUser;

    @Override
    protected void init() throws Exception {
        Assert.notBlank(user.getUsername(), "用户名不能为空！");
        if (user.getUsername().length() < 4 || user.getUsername().length() > 20) {
            throw new BizException("用户名长度为4~20个字符！");
        }
        if (!SimpleValidator.validateUserName(user.getUsername())) {
            throw new BizException("用户名只能是中文、英文或数字的组合！");
        }

        Assert.notTrue(SimpleValidator.validateMobile(user.getUsername()), "用户名不能为手机号！");

        Assert.notFalse(SimpleValidator.validatePassword(user.getPassword()), "密码为6~20位！");
        Assert.notFalse(SimpleValidator.validateTruename(user.getName()), "姓名不正确！");
        Assert.notFalse(SimpleValidator.validateMobile(user.getMobile()), "手机号不正确！");
    }

    @Override
    protected void doCommand() throws Exception {
        if (userService.findByUsername(user.getUsername()) != null) {
            throw new BizException("用户名为【" + user.getUsername() + "】的账号已存在！");
        }
        if (userService.findByMobile(user.getMobile()) != null) {
            throw new BizException("手机号为【" + user.getMobile() + "】的账号已存在！");
        }

        // 新增用户
        User persistedUser = new User();
        persistedUser.setUsername(user.getUsername());
        persistedUser.setPassword(encoder.encode(user.getPassword())); // 对密码加密
        persistedUser.setMobile(user.getMobile());
        persistedUser.setName(user.getName());
        userService.save(persistedUser);

        data.put("user", persistedUser);

        // 记录日志
        // logService.logUserAdd(currentUser.getId(), persistedUser.getUsername());
    }
}
