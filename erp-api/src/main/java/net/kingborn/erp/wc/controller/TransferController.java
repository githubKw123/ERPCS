package net.kingborn.erp.wc.controller;

import net.kingborn.core.controller.BaseController;
import net.kingborn.core.controller.Controller;
import net.kingborn.core.entry.Result;
import net.kingborn.erp.bc.command.*;
import net.kingborn.erp.wc.command.*;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 调拨控制器
 */
@Controller("/transfer")
public class TransferController extends BaseController {

    /**
     * 分页
     */
    @PostMapping("/page")
    public Result page() {
        return doAction(CTransferPage.class);
    }

    /**
     * 调拨单详情
     */
    @PostMapping("/detail")
    public Result detail() {
        return doAction(CTransferDetail.class);
    }

    /**
     * 创建一个新的单据编号
     */
    @PostMapping("/createCode")
    public Result createCode() {
        return doAction(CTransferCreateCode.class);
    }

    /**
     * 调拨单保存
     */
    @PostMapping("/save")
    public Result save() {
        return doAction(CTransferSave.class);
    }

    /**
     * 调拨单删除
     */
    @PostMapping("/delete")
    public Result delete() {
        return doAction(CTransferDelete.class);
    }

    /**
     * 调拨单切换审查
     */
    @PostMapping("/switchCheck")
    public Result switchCheck() {
        return doAction(CTransferSwitchCheck.class);
    }

}
