package net.kingborn.erp.fc.controller;

import net.kingborn.core.controller.BaseController;
import net.kingborn.core.controller.Controller;
import net.kingborn.core.entry.Result;
import net.kingborn.erp.fc.command.*;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 支出控制器
 */
@Controller("/expense")
public class ExpenseController extends BaseController {

    /**
     * 分页
     */
    @PostMapping("/page")
    public Result page() {
        return doAction(CExpensePage.class);
    }
    
    /**
     * 支出单详情
     */
    @PostMapping("/detail")
    public Result detail() {
        return doAction(CExpenseDetail.class);
    }

    /**
     * 创建一个新的单据编号
     */
    @PostMapping("/createCode")
    public Result createCode() {
        return doAction(CExpenseCreateCode.class);
    }

    /**
     * 支出单保存
     */
    @PostMapping("/save")
    public Result save() {
        return doAction(CExpenseSave.class);
    }

    /**
     * 支出单删除
     */
    @PostMapping("/delete")
    public Result delete() {
        return doAction(CExpenseDelete.class);
    }

}
