package net.kingborn.erp.bc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.erp.bc.model.Purchase;
import net.kingborn.erp.bc.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 获取用户已审核的购货单
 */
@Command
public class CPurchaseFindCheckedListBySupplier extends BaseCommand {

    @Autowired
    private PurchaseService purchaseService;

    @Param(required = true)
    private String supplierId;

    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        List<Purchase> purchaseList = purchaseService.findCheckedListBySupplier(supplierId);
        for (Purchase purchase : purchaseList) {
            purchase.put("verifiedAmount", 0);
            purchase.put("unverifiedAmount", 0);
        }

        data.put("purchaseList", purchaseList);
    }
}
