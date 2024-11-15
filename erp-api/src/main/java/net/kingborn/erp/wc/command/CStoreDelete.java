package net.kingborn.erp.wc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.erp.wc.model.Store;
import net.kingborn.erp.wc.service.IssueProductService;
import net.kingborn.erp.wc.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 入库单删除
 */
@Command
public class CStoreDelete extends BaseCommand {

    @Autowired
    private StoreService storeService;
    @Autowired
    private IssueProductService productService;

    @Param(required = true)
    private String storeId;
    
    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        Store store = storeService.getById(storeId);
        Assert.notNull(store, "ID为【" + storeId + "】的入库订单不存在！");

        storeService.removeById(storeId);

        productService.deleteByBusiness(storeId);
    }
}
