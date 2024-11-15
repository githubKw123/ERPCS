package net.kingborn.erp.sc.command;

import com.alibaba.fastjson.JSONObject;
import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.rc.model.DictItem;
import net.kingborn.erp.rc.service.DictItemService;
import net.kingborn.erp.sc.model.PurchaseSupplierSummary;
import net.kingborn.erp.uc.model.Product;
import net.kingborn.erp.uc.model.Supplier;
import net.kingborn.erp.uc.model.Warehouse;
import net.kingborn.erp.uc.service.ProductService;
import net.kingborn.erp.uc.service.SupplierService;
import net.kingborn.erp.uc.service.WarehouseService;
import net.kingborn.erp.util.SimpleValidator;
import net.kingborn.erp.wc.service.IssueProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 采购汇总表（按供应商）
 */
@Command
public class CAnalysisPurchaseSupplierSummaryList extends BaseCommand {

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
    private @Param
    String startDate;
    /** 结束时间 */
    private @Param String endDate;
    /** 供应商ID列表 */
    private @Param
    List<String> supplierIdList;
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
        List<PurchaseSupplierSummary> summaryList = issueProductService.analysisPurchaseListBySupplier(query);

        for (PurchaseSupplierSummary summary : summaryList) {
            Product product = productService.getById(summary.getProductId());
            Assert.notNull(product, "ID为【" + summary.getProductId() + "】的商品不存在！");

            summary.put("productName", product.getName());
            summary.put("spec", product.getSpec());
            summary.put("productCode", product.getCode());

            Supplier supplier = supplierService.getById(summary.getSupplierId());
            Assert.notNull(supplier, "ID为【" + summary.getSupplierId() + "】的供应商不存在！");
            summary.put("supplierName", supplier.getName());

            DictItem unit = dictItemService.getById(product.getUnitId());
            summary.put("unitName", unit.getName());

            Warehouse warehouse = warehouseService.getById(summary.getWarehouseId());
            Assert.notNull(warehouse, "ID为【" + summary.getWarehouseId() + "】的仓库不存在！");
            summary.put("warehouseName", warehouse.getName());
        }

        data.put("summaryList", summaryList);
    }
}
