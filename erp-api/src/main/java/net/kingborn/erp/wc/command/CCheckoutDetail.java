package net.kingborn.erp.wc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.erp.wc.model.Checkout;
import net.kingborn.erp.wc.model.IssueProduct;
import net.kingborn.erp.wc.service.CheckoutService;
import net.kingborn.erp.rc.model.DictItem;
import net.kingborn.erp.rc.service.DictItemService;
import net.kingborn.erp.uc.model.Product;
import net.kingborn.erp.uc.model.Warehouse;
import net.kingborn.erp.uc.service.ProductService;
import net.kingborn.erp.uc.service.WarehouseService;
import net.kingborn.erp.wc.service.IssueProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 出库单详情
 */
@Command
public class CCheckoutDetail extends BaseCommand {

    @Autowired
    private CheckoutService checkoutService;
    @Autowired
    private IssueProductService issueProductService;
    @Autowired
    private ProductService productService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private DictItemService dictItemService;

    @Param(required = true)
    private String checkoutId; // 出库订单ID
    
    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        Checkout checkout = checkoutService.getById(checkoutId);
        Assert.notNull(checkout, "ID为【" + checkoutId + "】的出库订单不存在！");

        List<IssueProduct> productList = issueProductService.findListByBusiness(checkout.getId());
        for (IssueProduct checkoutProduct : productList) {
            Product product = productService.getById(checkoutProduct.getProductId());
            Assert.notNull(product, "ID为【" + checkoutProduct.getProductId() + "】的商品不存在！");
            checkoutProduct.put("productName", product.getName());

            DictItem dictItem = dictItemService.getById(product.getUnitId());
            Assert.notNull(dictItem, "ID为【" + product.getUnitId() + "】的单位不存在！");
            checkoutProduct.put("unitName", dictItem.getName());

            Warehouse warehouse = warehouseService.getById(checkoutProduct.getWarehouseId());
            Assert.notNull(warehouse, "ID为【" + checkoutProduct.getWarehouseId() + "】的仓库不存在！");
            checkoutProduct.put("warehouseName", warehouse.getName());
        }
        checkout.put("productList", productList);

        data.put("checkout", checkout);
    }
}
