package net.kingborn.erp.sc.command;

import com.alibaba.fastjson.JSONObject;
import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.bc.model.Purchase;
import net.kingborn.erp.bc.service.PurchaseService;
import net.kingborn.erp.rc.model.DictItem;
import net.kingborn.erp.rc.service.DictItemService;
import net.kingborn.erp.uc.model.Product;
import net.kingborn.erp.uc.model.Supplier;
import net.kingborn.erp.uc.model.Warehouse;
import net.kingborn.erp.uc.service.ProductService;
import net.kingborn.erp.uc.service.SupplierService;
import net.kingborn.erp.uc.service.WarehouseService;
import net.kingborn.erp.util.SimpleValidator;
import net.kingborn.erp.wc.model.IssueProduct;
import net.kingborn.erp.wc.service.IssueProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 采购明细表
 */
@Command
public class CAnalysisPurchaseDetailList extends BaseCommand {

    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private IssueProductService issueProductService;
    @Autowired
    private ProductService productService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private DictItemService dictItemService;
    @Autowired
    private SupplierService supplierService;

    /** 开始时间 */
    private @Param String startDate;
    /** 结束时间 */
    private @Param String endDate;
    /** 供应商ID列表 */
    private @Param List<String> supplierIdList;
    /** 商品ID列表 */
    private @Param List<String> productIdList;
    /** 仓库ID列表 */
    private @Param List<String> warehouseIdList;

    @Override
    protected void init() throws Exception {
        if (StrKit.notBlank(startDate)) {
            Assert.notFalse(SimpleValidator.validateDate(startDate), "起始时间不正确！");
        }
        if (StrKit.notBlank(endDate)) {
            Assert.notFalse(SimpleValidator.validateDate(endDate), "结束时间不正确！");
        }
    }

    @Override
    protected void doCommand() throws Exception {
        JSONObject query = new JSONObject();
        query.put("startDate", startDate);
        query.put("endDate", endDate);
        query.put("supplierIdList", supplierIdList);
        query.put("productIdList", productIdList);
        query.put("warehouseIdList", warehouseIdList);
        List<IssueProduct> productList = issueProductService.analysisPurchaseList(query);
        for (IssueProduct issueProduct : productList) {
            Purchase purchase = purchaseService.getById(issueProduct.getBusinessId());
            Assert.notNull(purchase, "ID为【" + issueProduct.getBusinessId() + "】的购货单不存在！");

            Product product = productService.getById(issueProduct.getProductId());
            Assert.notNull(product, "ID为【" + issueProduct.getProductId() + "】的商品不存在！");

            issueProduct.put("productName", product.getName());
            issueProduct.put("spec", product.getSpec());
            issueProduct.put("productCode", product.getCode());

            Supplier supplier = supplierService.getById(purchase.getSupplierId());
            Assert.notNull(supplier, "ID为【" + purchase.getSupplierId() + "】的供应商不存在！");
            issueProduct.put("supplierName", supplier.getName());

            DictItem unit = dictItemService.getById(product.getUnitId());
            issueProduct.put("unitName", unit.getName());

            Warehouse warehouse = warehouseService.getById(issueProduct.getWarehouseId());
            Assert.notNull(warehouse, "ID为【" + issueProduct.getWarehouseId() + "】的仓库不存在！");
            issueProduct.put("warehouseName", warehouse.getName());

            issueProduct.put("issueDate", purchase.getIssueDate());
            issueProduct.put("purchaseCode", purchase.getCode());
            issueProduct.put("type", purchase.getType());
        }

        data.put("productList", productList);
    }
}
