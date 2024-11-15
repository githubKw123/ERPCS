package net.kingborn.erp.fc.controller;

import net.kingborn.core.controller.BaseController;
import net.kingborn.core.controller.Controller;
import net.kingborn.core.entry.Result;
import net.kingborn.erp.fc.command.*;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 收入控制器
 */
@Controller("/income")
public class IncomeController extends BaseController {

    /**
     * 分页
     */
    @PostMapping("/page")
    public Result page() {
        return doAction(CIncomePage.class);
    }
    
    /**
     * 收入单详情
     */
    @PostMapping("/detail")
    public Result detail() {
        return doAction(CIncomeDetail.class);
    }

    /**
     * 创建一个新的单据编号
     */
    @PostMapping("/createCode")
    public Result createCode() {
        return doAction(CIncomeCreateCode.class);
    }

    /**
     * 收入单保存
     */
    @PostMapping("/save")
    public Result save() {
        return doAction(CIncomeSave.class);
    }

    /**
     * 收入单删除
     */
    @PostMapping("/delete")
    public Result delete() {
        return doAction(CIncomeDelete.class);
    }

}
