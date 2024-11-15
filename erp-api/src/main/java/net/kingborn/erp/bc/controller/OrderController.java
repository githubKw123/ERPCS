package net.kingborn.erp.bc.controller;

import net.kingborn.core.controller.BaseController;
import net.kingborn.core.controller.Controller;
import net.kingborn.core.entry.Result;
import net.kingborn.erp.bc.command.*;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 客户订单控制器
 */
@Controller("/order")
public class OrderController extends BaseController {

    /**
     * 分页列表
     */
    @PostMapping("/page")
    public Result page() {
        return doAction(COrderPage.class);
    }

    /**
     * 客户订单详情
     */
    @PostMapping("/detail")
    public Result detail() {
        return doAction(COrderDetail.class);
    }

    /**
     * 创建一个新的单据编号
     */
    @PostMapping("/createCode")
    public Result createCode() {
        return doAction(COrderCreateCode.class);
    }

    /**
     * 客户订单保存
     */
    @PostMapping("/save")
    public Result save() {
        return doAction(COrderSave.class);
    }

    /**
     * 客户订单删除
     */
    @PostMapping("/delete")
    public Result delete() {
        return doAction(COrderDelete.class);
    }

    /**
     * 客户订单切换审查
     */
    @PostMapping("/switchCheck")
    public Result switchCheck() {
        return doAction(COrderSwitchCheck.class);
    }

}
