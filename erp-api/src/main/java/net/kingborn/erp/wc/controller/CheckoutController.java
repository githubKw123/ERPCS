package net.kingborn.erp.wc.controller;

import net.kingborn.core.controller.BaseController;
import net.kingborn.core.controller.Controller;
import net.kingborn.core.entry.Result;
import net.kingborn.erp.wc.command.*;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 出库控制器
 */
@Controller("/checkout")
public class CheckoutController extends BaseController {

    /**
     * 分页
     */
    @PostMapping("/page")
    public Result page() {
        return doAction(CCheckoutPage.class);
    }


    /**
     * 出库单详情
     */
    @PostMapping("/detail")
    public Result detail() {
        return doAction(CCheckoutDetail.class);
    }

    /**
     * 创建一个新的单据编号
     */
    @PostMapping("/createCode")
    public Result createCode() {
        return doAction(CCheckoutCreateCode.class);
    }

    /**
     * 出库单保存
     */
    @PostMapping("/save")
    public Result save() {
        return doAction(CCheckoutSave.class);
    }

    /**
     * 出库单删除
     */
    @PostMapping("/delete")
    public Result delete() {
        return doAction(CCheckoutDelete.class);
    }

}
