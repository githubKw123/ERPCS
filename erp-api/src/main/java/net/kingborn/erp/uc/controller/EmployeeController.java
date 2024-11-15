package net.kingborn.erp.uc.controller;

import net.kingborn.core.controller.BaseController;
import net.kingborn.core.controller.Controller;
import net.kingborn.core.entry.Result;
import net.kingborn.erp.uc.command.*;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 职员控制器
 */
@Controller("/employee")
public class EmployeeController extends BaseController {

    /**
     * 职员列表
     */
    @PostMapping("/page")
    public Result page() {
        return doAction(CEmployeePage.class);
    }

    /**
     * 职员保存
     */
    @PostMapping("/save")
    public Result save() {
        return doAction(CEmployeeSave.class);
    }

    /**
     * 职员详情
     */
    @PostMapping("/detail")
    public Result detail() {
        return doAction(CEmployeeDetail.class);
    }

    /**
     * 职员删除
     */
    @PostMapping("/delete")
    public Result delete() {
        return doAction(CEmployeeDelete.class);
    }

    @PostMapping("/switchActive")
    public Result switchActive() {
        return doAction(CEmployeeSwitchActive.class);
    }

}
