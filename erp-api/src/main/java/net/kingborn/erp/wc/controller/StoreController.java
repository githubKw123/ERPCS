package net.kingborn.erp.wc.controller;

import net.kingborn.core.controller.BaseController;
import net.kingborn.core.controller.Controller;
import net.kingborn.core.entry.Result;
import net.kingborn.erp.wc.command.*;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 入库控制器
 */
@Controller("/store")
public class StoreController extends BaseController {

    /**
     * 分页
     */
    @PostMapping("/page")
    public Result page() {
        return doAction(CStorePage.class);
    }


    /**
     * 入库单详情
     */
    @PostMapping("/detail")
    public Result detail() {
        return doAction(CStoreDetail.class);
    }

    /**
     * 创建一个新的单据编号
     */
    @PostMapping("/createCode")
    public Result createCode() {
        return doAction(CStoreCreateCode.class);
    }

    /**
     * 入库单保存
     */
    @PostMapping("/save")
    public Result save() {
        return doAction(CStoreSave.class);
    }

    /**
     * 入库单删除
     */
    @PostMapping("/delete")
    public Result delete() {
        return doAction(CStoreDelete.class);
    }

}
