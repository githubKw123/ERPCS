package net.kingborn.erp.bc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.erp.bc.model.Purchase;
import net.kingborn.erp.bc.service.PurchaseService;
import net.kingborn.erp.fc.service.AccountRecordService;
import net.kingborn.erp.wc.service.IssueProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 购货单删除
 */
@Command
@Transactional
public class CPurchaseDelete extends BaseCommand {

    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private IssueProductService productService;
    @Autowired
    private AccountRecordService accountService;

    @Param(required = true)
    private String purchaseId;

    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        Purchase purchase = purchaseService.getById(purchaseId);
        Assert.notNull(purchase, "ID为【" + purchaseId + "】的购货单不存在！");

        purchaseService.removeById(purchaseId);

        productService.deleteByBusiness(purchaseId);
        accountService.deleteByBusiness(purchaseId);
    }
}
