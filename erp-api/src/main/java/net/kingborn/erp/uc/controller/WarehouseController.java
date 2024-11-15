package net.kingborn.erp.uc.controller;

import net.kingborn.core.controller.BaseController;
import net.kingborn.core.controller.Controller;
import net.kingborn.core.entry.Result;
import net.kingborn.erp.uc.command.*;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 仓库控制器
 */
@Controller("/warehouse")
public class WarehouseController extends BaseController {

    /**
     * 仓库列表
     */
    @PostMapping("/page")
    public Result page() {
        return doAction(CWarehousePage.class);
    }

    /**
     * 仓库保存
     */
    @PostMapping("/save")
    public Result save() {
        return doAction(CWarehouseSave.class);
    }

    /**
     * 仓库详情
     */
    @PostMapping("/detail")
    public Result detail() {
        return doAction(CWarehouseDetail.class);
    }

    /**
     * 仓库删除
     */
    @PostMapping("/delete")
    public Result delete() {
        return doAction(CWarehouseDelete.class);
    }

    /**
     * 仓库启停
     */
    @PostMapping("/switchActive")
    public Result switchActive() {
        return doAction(CWarehouseSwitchActive.class);
    }

}
