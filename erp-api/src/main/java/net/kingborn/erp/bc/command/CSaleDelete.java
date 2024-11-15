package net.kingborn.erp.bc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.erp.bc.model.Sale;
import net.kingborn.erp.wc.service.IssueProductService;
import net.kingborn.erp.bc.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 销售单删除
 */
@Command
public class CSaleDelete extends BaseCommand {

    @Autowired
    private SaleService saleService;
    @Autowired
    private IssueProductService productService;

    @Param(required = true)
    private String saleId;
    
    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        Sale sale = saleService.getById(saleId);
        Assert.notNull(sale, "ID为【" + saleId + "】的销售订单不存在！");

        saleService.removeById(saleId);

        productService.deleteByBusiness(saleId);
    }
}
