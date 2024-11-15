package net.kingborn.erp.bc.controller;

import net.kingborn.core.controller.BaseController;
import net.kingborn.core.controller.Controller;
import net.kingborn.core.entry.Result;
import net.kingborn.erp.bc.command.*;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 购货单控制器
 */
@Controller("/purchase")
public class PurchaseController extends BaseController {

    /**
     * 分页
     */
    @PostMapping("/page")
    public Result page() {
        return doAction(CPurchasePage.class);
    }

    /**
     * 购货单详情
     */
    @PostMapping("/detail")
    public Result detail() {
        return doAction(CPurchaseDetail.class);
    }


    /**
     * 购货单详情
     */
    @PostMapping("/detailbytype")
    public Result detailbytype() {
        return doAction(CPurchaseDetailbytype.class);
    }


    /**
     * 创建一个新的单据编号
     */
    @PostMapping("/createCode")
    public Result createCode() {
        return doAction(CPurchaseCreateCode.class);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public Result save() {
        return doAction(CPurchaseSave.class);
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public Result delete() {
        return doAction(CPurchaseDelete.class);
    }

    /**
     * 切换审查
     */
    @PostMapping("/switchCheck")
    public Result switchCheck() {
        return doAction(CPurchaseSwitchCheck.class);
    }

    @PostMapping("/findCheckedListBySupplier")
    public Result findCheckedListBySupplier() {
        return doAction(CPurchaseFindCheckedListBySupplier.class);
    }

}
