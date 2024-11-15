package net.kingborn.erp.uc.controller;

import net.kingborn.core.controller.BaseController;
import net.kingborn.core.controller.Controller;
import net.kingborn.core.entry.Result;
import net.kingborn.erp.uc.command.*;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 客户控制器
 */
@Controller("/customer")
public class CustomerController extends BaseController {

    /**
     * 分页列表
     */
    @PostMapping("/page")
    public Result page() {
        return doAction(CCustomerPage.class);
    }

    /**
     * 联系人列表
     */
    @PostMapping("/contactList")
    public Result contactList() {
        return doAction(CCustomerContactList.class);
    }

    /**
     * 保存客户
     */
    @PostMapping("/save")
    public Result save() {
        return doAction(CCustomerSave.class);
    }

    /**
     * 客户详情
     */
    @PostMapping("/detail")
    public Result detail() {
        return doAction(CCustomerDetail.class);
    }

    /**
     * 客户删除
     */
    @PostMapping("/delete")
    public Result delete() {
        return doAction(CCustomerDelete.class);
    }

    /**
     * 客户启停
     */
    @PostMapping("/switchActive")
    public Result switchActive() {
        return doAction(CCustomerSwitchActive.class);
    }

    /**
     * 导入Excel
     */
    @PostMapping("/importExcel")
    public Result importExcel() {
        return doAction(CCustomerImportExcel.class);
    }

    /**
     * 导出Excel
     */
    @PostMapping("/exportExcel")
    public Result exportExcel() {
        return doAction(CCustomerExportExcel.class);
    }

}
