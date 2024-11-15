package net.kingborn.erp.uc.controller;

import net.kingborn.core.controller.BaseController;
import net.kingborn.core.controller.Controller;
import net.kingborn.core.entry.Result;
import net.kingborn.erp.uc.command.CSettlementAccountDelete;
import net.kingborn.erp.uc.command.CSettlementAccountDetail;
import net.kingborn.erp.uc.command.CSettlementAccountList;
import net.kingborn.erp.uc.command.CSettlementAccountSave;
import org.springframework.web.bind.annotation.PostMapping;


/**
 * 结算账户控制器
 */
@Controller("/settlementAccount")
public class SettlementAccountController extends BaseController {

    /**
     * 结算账户列表
     */
    @PostMapping("/list")
    public Result list() {
        return doAction(CSettlementAccountList.class);
    }

    /**
     * 保存账户
     */
    @PostMapping("/save")
    public Result save() {
        return doAction(CSettlementAccountSave.class);
    }

    /**
     * 账户详情
     */
    @PostMapping("/detail")
    public Result detail() {
        return doAction(CSettlementAccountDetail.class);
    }

    /**
     * 账户删除
     */
    @PostMapping("/delete")
    public Result delete() {
        return doAction(CSettlementAccountDelete.class);
    }

}
