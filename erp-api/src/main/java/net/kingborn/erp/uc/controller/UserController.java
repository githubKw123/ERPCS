package net.kingborn.erp.uc.controller;

import net.kingborn.core.controller.BaseController;
import net.kingborn.core.controller.Controller;
import net.kingborn.core.entry.Result;
import net.kingborn.erp.uc.command.*;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 用户控制器
 */
@Controller("/user")
public class UserController extends BaseController {

    /**
     * 新增用户
     *
     * @return
     */
    @PostMapping("/add")
    public Result add() {
        return doAction(CUserAdd.class);
    }

    /**
     * 用户登录
     *
     * @return
     */
    @PostMapping("/login")
    public Result login() {
        return doAction(CUserLogin.class);
    }

    /**
     * 是否启用切换
     *
     * @return
     */
    @PostMapping("/switchActive")
    public Result switchActive() {
        return doAction(CUserSwitchActive.class);
    }

    /**
     * 用户分页列表
     */
    @PostMapping("/page")
    public Result page() {
        return doAction(CUserPage.class);
    }

    @PostMapping("/resetPassword")
    public Result resetPassword() {
        return doAction(CUserResetPassword.class);
    }

}
