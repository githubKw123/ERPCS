package net.kingborn.erp.rc.controller;

import net.kingborn.core.controller.BaseController;
import net.kingborn.core.controller.Controller;
import net.kingborn.core.entry.Result;
import net.kingborn.erp.rc.command.CLogPage;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 日志控制器
 */
@Controller("/log")
public class LogController extends BaseController {

    /**
     * 分页列表
     */
    @PostMapping("/page")
    public Result page() {
        return doAction(CLogPage.class);
    }

}
