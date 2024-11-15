package net.kingborn.erp.wc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.erp.wc.model.IssueProduct;
import net.kingborn.erp.wc.model.Store;
import net.kingborn.erp.wc.service.IssueProductService;
import net.kingborn.erp.wc.service.StoreService;
import net.kingborn.erp.rc.model.DictItem;
import net.kingborn.erp.rc.service.DictItemService;
import net.kingborn.erp.uc.model.Product;
import net.kingborn.erp.uc.model.Warehouse;
import net.kingborn.erp.uc.service.ProductService;
import net.kingborn.erp.uc.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 入库单详情
 */
@Command
public class CStoreDetail extends BaseCommand {

    @Autowired
    private StoreService storeService;
    @Autowired
    private IssueProductService issueProductService;
    @Autowired
    private ProductService productService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private DictItemService dictItemService;

    @Param(required = true)
    private String storeId; // 入库订单ID
    
    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        Store store = storeService.getById(storeId);
        Assert.notNull(store, "ID为【" + storeId + "】的入库订单不存在！");

        List<IssueProduct> productList = issueProductService.findListByBusiness(store.getId());
        for (IssueProduct storeProduct : productList) {
            Product product = productService.getById(storeProduct.getProductId());
            Assert.notNull(product, "ID为【" + storeProduct.getProductId() + "】的商品不存在！");
            storeProduct.put("productName", product.getName());

            storeProduct.put("spec",product.getSpec());

            DictItem dictItem = dictItemService.getById(product.getUnitId());
            Assert.notNull(dictItem, "ID为【" + product.getUnitId() + "】的单位不存在！");
            storeProduct.put("unitName", dictItem.getName());

            Warehouse warehouse = warehouseService.getById(storeProduct.getWarehouseId());
            Assert.notNull(warehouse, "ID为【" + storeProduct.getWarehouseId() + "】的仓库不存在！");
            storeProduct.put("warehouseName", warehouse.getName());
        }
        store.put("productList", productList);

        data.put("store", store);
    }
}
