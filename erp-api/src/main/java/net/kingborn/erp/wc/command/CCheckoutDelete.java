package net.kingborn.erp.wc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.erp.wc.model.Checkout;
import net.kingborn.erp.wc.service.CheckoutService;
import net.kingborn.erp.wc.service.IssueProductService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 出库单删除
 */
@Command
public class CCheckoutDelete extends BaseCommand {

    @Autowired
    private CheckoutService checkoutService;
    @Autowired
    private IssueProductService productService;

    @Param(required = true)
    private String checkoutId;
    
    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        Checkout checkout = checkoutService.getById(checkoutId);
        Assert.notNull(checkout, "ID为【" + checkoutId + "】的出库订单不存在！");

        checkoutService.removeById(checkoutId);

        productService.deleteByBusiness(checkoutId);
    }
}
