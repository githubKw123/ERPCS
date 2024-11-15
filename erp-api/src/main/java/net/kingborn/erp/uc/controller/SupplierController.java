package net.kingborn.erp.uc.controller;

import net.kingborn.core.controller.BaseController;
import net.kingborn.core.controller.Controller;
import net.kingborn.core.entry.Result;
import net.kingborn.erp.uc.command.*;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 供应商控制器
 */
@Controller("/supplier")
public class SupplierController extends BaseController {

    /**
     * 分页列表
     */
    @PostMapping("/page")
    public Result page() {
        return doAction(CSupplierPage.class);
    }

    /**
     * 保存供应商
     */
    @PostMapping("/save")
    public Result save() {
        return doAction(CSupplierSave.class);
    }

    /**
     * 供应商详情
     */
    @PostMapping("/detail")
    public Result detail() {
        return doAction(CSupplierDetail.class);
    }

    /**
     * 供应商删除
     */
    @PostMapping("/delete")
    public Result delete() {
        return doAction(CSupplierDelete.class);
    }

    /**
     * 供应商启停
     */
    @PostMapping("/switchActive")
    public Result switchActive() {
        return doAction(CSupplierSwitchActive.class);
    }

    /**
     * 导入Excel
     */
    @PostMapping("/importExcel")
    public Result importExcel() {
        return doAction(CSupplierImportExcel.class);
    }

    /**
     * 导出Excel
     */
    @PostMapping("/exportExcel")
    public Result exportExcel() {
        return doAction(CSupplierExportExcel.class);
    }

}
