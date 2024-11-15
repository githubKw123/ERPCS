package net.kingborn.erp.sc.command;

import com.alibaba.fastjson.JSONObject;
import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.rc.model.DictItem;
import net.kingborn.erp.rc.service.DictItemService;
import net.kingborn.erp.uc.model.Product;
import net.kingborn.erp.uc.model.Warehouse;
import net.kingborn.erp.uc.service.ProductService;
import net.kingborn.erp.uc.service.WarehouseService;
import net.kingborn.erp.util.SimpleValidator;
import net.kingborn.erp.wc.model.StockRecord;
import net.kingborn.erp.wc.service.StockRecordService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品库存余额表
 */
@Command
public class CAnalysisStockAmountList extends BaseCommand {

    @Autowired
    private StockRecordService recordService;
    @Autowired
    private ProductService productService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private DictItemService dictItemService;

    /** 结束时间 */
    private @Param
    String endDate;
    /** 商品ID列表 */
    private @Param
    List<String> productIdList;
    /** 仓库ID列表 */
    private @Param List<String> warehouseIdList;

    private JSONObject query;
    private List<JSONObject> warehouseProductList = new ArrayList<>();
    private JSONObject warehouseProduct;

    @Override
    protected void init() throws Exception {
        if (StrKit.notBlank(endDate)) {
            Assert.notFalse(SimpleValidator.validateDate(endDate), "结束时间不正确！");
        }

        query = new JSONObject();
        query.put("endDate", endDate);
        query.put("productIdList", productIdList);
        query.put("warehouseIdList", warehouseIdList);
    }

    @Override
    protected void doCommand() throws Exception {
        List<String> productIdList = recordService.distinctProductIdList(query);

        for (String productId : productIdList) {
            warehouseProduct = new JSONObject();

            Product product = productService.getById(productId);
            Assert.notNull(product, "ID为【" + productId + "】的商品不存在！");

            warehouseProduct.put("productName", product.getName());
            warehouseProduct.put("spec", product.getSpec());
            warehouseProduct.put("productCode", product.getCode());

            DictItem unit = dictItemService.getById(product.getUnitId());
            warehouseProduct.put("unitName", unit.getName());

            composeWarehouseStockInfo(productId);

            warehouseProductList.add(warehouseProduct);
        }

        List<Warehouse> warehouseList = warehouseService.findByIdList(warehouseIdList);

        data.put("productList", warehouseProductList);
        data.put("warehouseList", warehouseList);
    }

    /**
     * 组装各个仓库的库存信息
     *
     * @param productId
     */
    private void composeWarehouseStockInfo(String productId) {
        // 获取最近的各仓库出入仓记录
        List<String> warehouseIdList = recordService.distinctWarehouseIdListByProduct(query, productId);

        Map<String, JSONObject> warehouseAmountMapping = new HashMap<>();
        JSONObject warehouseAmount;
        double totalAmount = 0.0;
        double totalQuantity = 0.0;
        for (String warehouseId : warehouseIdList) {
            warehouseAmount = new JSONObject();

            StockRecord record = recordService.findLatestRecord(query, productId, warehouseId);
            warehouseAmount.put("warehouseId", warehouseId);
            warehouseAmount.put("warehouseName", warehouseService.getNameById(warehouseId));
            warehouseAmount.put("currentQuantity", record.getCurrentQuantity());
            warehouseAmount.put("currentAmount", record.getCurrentQuantity() * record.getPrice());
            warehouseAmountMapping.put(warehouseId, warehouseAmount);

            totalAmount += warehouseAmount.getDoubleValue("currentAmount");
            totalQuantity += warehouseAmount.getDoubleValue("currentQuantity");
        }
        // 所有仓库总量
        JSONObject totalWarehouseAmount = new JSONObject();
        totalWarehouseAmount.put("totalAmount", totalAmount);
        totalWarehouseAmount.put("totalQuantity", totalQuantity);
        warehouseAmountMapping.put("total", totalWarehouseAmount);

        warehouseProduct.put("warehouseAmountMapping", warehouseAmountMapping);
    }
}
