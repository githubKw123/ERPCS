package net.kingborn.erp.bc.controller;

import net.kingborn.core.controller.BaseController;
import net.kingborn.core.controller.Controller;
import net.kingborn.core.entry.Result;
import net.kingborn.erp.bc.command.*;
import org.springframework.web.bind.annotation.PostMapping;


/**
 * 销售单控制器
 */
@Controller("/sale")
public class SaleController extends BaseController {

    /**
     * 分页
     */
    @PostMapping("/page")
    public Result page() {
        return doAction(CSalePage.class);
    }

    /**
     * 销货单详情
     */
    @PostMapping("/detail")
    public Result detail() {
        return doAction(CSaleDetail.class);
    }

    /**
     * 创建一个新的单据编号
     */
    @PostMapping("/createCode")
    public Result createCode() {
        return doAction(CSaleCreateCode.class);
    }

    /**
     * 销货单保存
     */
    @PostMapping("/save")
    public Result save() {
        return doAction(CSaleSave.class);
    }

    /**
     * 销货单删除
     */
    @PostMapping("/delete")
    public Result delete() {
        return doAction(CSaleDelete.class);
    }

    /**
     * 销货单切换审查
     */
    @PostMapping("/switchCheck")
    public Result switchCheck() {
        return doAction(CSaleSwitchCheck.class);
    }

    @PostMapping("/findCheckedListByCustomer")
    public Result findCheckedListByCustomer() {
        return doAction(CSaleFindCheckedListByCustomer.class);
    }

}
