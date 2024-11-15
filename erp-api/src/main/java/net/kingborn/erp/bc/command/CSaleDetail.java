package net.kingborn.erp.bc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.erp.bc.model.Sale;
import net.kingborn.erp.wc.model.IssueProduct;
import net.kingborn.erp.wc.service.IssueProductService;
import net.kingborn.erp.bc.service.SaleService;
import net.kingborn.erp.rc.model.DictItem;
import net.kingborn.erp.rc.service.DictItemService;
import net.kingborn.erp.uc.model.Product;
import net.kingborn.erp.uc.model.Warehouse;
import net.kingborn.erp.uc.service.ProductService;
import net.kingborn.erp.uc.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 销售单详情
 */
@Command
public class CSaleDetail extends BaseCommand {

    @Autowired
    private SaleService saleService;
    @Autowired
    private IssueProductService issueProductService;
    @Autowired
    private ProductService productService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private DictItemService dictItemService;

    @Param(required = true)
    private String saleId; // 销货订单ID
    
    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        Sale sale = saleService.getById(saleId);
        Assert.notNull(sale, "ID为【" + saleId + "】的销售订单不存在！");

        List<IssueProduct> productList = issueProductService.findListByBusiness(sale.getId());
        for (IssueProduct orderProduct : productList) {
            Product product = productService.getById(orderProduct.getProductId());
            Assert.notNull(product, "ID为【" + orderProduct.getProductId() + "】的商品不存在！");
            orderProduct.put("productName", product.getName());

            DictItem dictItem = dictItemService.getById(product.getUnitId());
            Assert.notNull(dictItem, "ID为【" + product.getUnitId() + "】的单位不存在！");
            orderProduct.put("unitName", dictItem.getName());

            Warehouse warehouse = warehouseService.getById(orderProduct.getWarehouseId());
            Assert.notNull(warehouse, "ID为【" + orderProduct.getWarehouseId() + "】的仓库不存在！");
            orderProduct.put("warehouseName", warehouse.getName());
        }
        sale.put("productList", productList);

        data.put("sale", sale);
    }
}
