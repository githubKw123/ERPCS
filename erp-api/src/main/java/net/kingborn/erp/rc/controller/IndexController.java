package net.kingborn.erp.rc.controller;

import net.kingborn.core.controller.BaseController;
import net.kingborn.core.controller.Controller;
import net.kingborn.core.entry.Result;
import net.kingborn.erp.rc.command.*;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 首页控制器
 */
@Controller("/")
public class IndexController extends BaseController {

    /**
     * 获取系统设置
     */
    @PostMapping("/getSystemConfiguration")
    public Result getSystemConfiguration() {
        return doAction(CGetSystemConfiguration.class);
    }

    /**
     * 设置系统设置
     */
    @PostMapping("/setSystemConfiguration")
    public Result setSystemConfiguration() {
        return doAction(CSetSystemConfiguration.class);
    }

}
