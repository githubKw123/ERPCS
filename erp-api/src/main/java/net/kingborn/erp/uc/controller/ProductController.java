package net.kingborn.erp.uc.controller;

import net.kingborn.core.controller.BaseController;
import net.kingborn.core.controller.Controller;
import net.kingborn.core.entry.Result;
import net.kingborn.erp.bc.command.CPurchaseDetailbytype;
import net.kingborn.erp.uc.command.*;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 商品控制器
 */
@Controller("/product")
public class ProductController extends BaseController {

    /**
     * 分页列表
     */
    @PostMapping("/page")
    public Result page() {
        return doAction(CProductPage.class);
    }

    /**
     * 商品保存
     */
    @PostMapping("/save")
    public Result save() {
        return doAction(CProductSave.class);
    }

    /**
     * 商品详情
     */
    @PostMapping("/detail")
    public Result detail() {
        return doAction(CProductDetail.class);
    }

    /**
     * 商品删除
     */
    @PostMapping("/delete")
    public Result delete() {
        return doAction(CProductDelete.class);
    }

    /**
     * 商品启停
     */
    @PostMapping("/switchActive")
    public Result switchActive() {
        return doAction(CProductSwitchActive.class);
    }

    /**
     * 购货单详情
     */
    @PostMapping("/findbyid")
    public Result findbyid() {
        return doAction(CProductFindbyid.class);
    }

}
