package net.kingborn.erp.bc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.erp.bc.model.Purchase;
import net.kingborn.erp.bc.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 购货单切换审查
 */
@Command
public class CPurchaseSwitchCheck extends BaseCommand {

    @Autowired
    private PurchaseService purchaseService;

    @Param(required = true)
    private String purchaseId;

    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        Purchase purchase = purchaseService.getById(purchaseId);
        Assert.notNull(purchase, "ID为【" + purchaseId + "】的购货单不存在！");

        purchase.setChecked(!purchase.isChecked());
        purchaseService.saveOrUpdate(purchase);

        data.put("purchase", purchase);
    }
}
