package net.kingborn.erp.bc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.erp.bc.model.Purchase;
import net.kingborn.erp.bc.service.PurchaseService;
import net.kingborn.erp.fc.model.AccountRecord;
import net.kingborn.erp.fc.service.AccountRecordService;
import net.kingborn.erp.rc.model.DictItem;
import net.kingborn.erp.rc.service.DictItemService;
import net.kingborn.erp.uc.model.Product;
import net.kingborn.erp.uc.model.Supplier;
import net.kingborn.erp.uc.model.Warehouse;
import net.kingborn.erp.uc.service.ProductService;
import net.kingborn.erp.uc.service.SupplierService;
import net.kingborn.erp.uc.service.WarehouseService;
import net.kingborn.erp.wc.model.IssueProduct;
import net.kingborn.erp.wc.service.IssueProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Command
public class CPurchaseDetailbytype extends BaseCommand {
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private IssueProductService issueProductService;
    @Autowired
    private AccountRecordService accountService;
    @Autowired
    private ProductService productService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private DictItemService dictItemService;


    @Param(required = true)
    private String type; // 购货ID

    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {

        List<IssueProduct> productList = issueProductService.findListBytype(type);
        for (IssueProduct issueProduct : productList) {
            Product product = productService.getById(issueProduct.getProductId());
            Assert.notNull(product, "ID为【" + issueProduct.getProductId() + "】的商品不存在！");
            issueProduct.put("productName", product.getName());

            DictItem dictItem = dictItemService.getById(product.getUnitId());
            Assert.notNull(dictItem, "ID为【" + product.getUnitId() + "】的单位不存在！");
            issueProduct.put("unitName", dictItem.getName());

            issueProduct.put("spec", product.getSpec());

            Warehouse warehouse = warehouseService.getById(issueProduct.getWarehouseId());
            Assert.notNull(warehouse, "ID为【" + issueProduct.getWarehouseId() + "】的仓库不存在！");
            issueProduct.put("warehouseName", warehouse.getName());

            Supplier supplier = supplierService.getById(issueProduct.getSupplierId());
            Assert.notNull(supplier, "ID为【" + issueProduct.getSupplierId() + "】的供应商不存在！");
            issueProduct.put("supplierName", supplier.getName());
        }
        data.put("productList", productList);
    }

}
