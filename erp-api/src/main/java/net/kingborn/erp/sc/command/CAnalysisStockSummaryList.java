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
import net.kingborn.erp.uc.model.Product;
import net.kingborn.erp.uc.model.Warehouse;
import net.kingborn.erp.uc.service.ProductService;
import net.kingborn.erp.uc.service.WarehouseService;
import net.kingborn.erp.util.SimpleValidator;
import net.kingborn.erp.wc.model.StockRecord;
import net.kingborn.erp.wc.service.StockRecordService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * 收发汇总表
 */
@Command
public class CAnalysisStockSummaryList extends BaseCommand {

    @Autowired
    private StockRecordService recordService;
    @Autowired
    private ProductService productService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private DictItemService dictItemService;

    /** 开始时间 */
    private @Param
    String startDate;
    /** 结束时间 */
    private @Param String endDate;
    /** 商品ID列表 */
    private @Param
    List<String> productIdList;
    /** 仓库ID列表 */
    private @Param List<String> warehouseIdList;

    private JSONObject query;
    private List<JSONObject> stockList = new ArrayList<>();
    private JSONObject stock;

    @Override
    protected void init() throws Exception {
        if (StrKit.notBlank(startDate)) {
            Assert.notFalse(SimpleValidator.validateDate(startDate), "起始时间不正确！");
        }
        if (StrKit.notBlank(endDate)) {
            Assert.notFalse(SimpleValidator.validateDate(endDate), "结束时间不正确！");
        }

        query = new JSONObject();
        query.put("startDate", startDate);
        query.put("endDate", endDate);
        query.put("productIdList", productIdList);
        query.put("warehouseIdList", warehouseIdList);
    }

    @Override
    protected void doCommand() throws Exception {
        List<StockRecord> recordList = recordService.distinctProductWarehouseIdList(query);

        for (StockRecord record : recordList) {
            stock = new JSONObject();
            stock.put("productId", record.getProductId());
            stock.put("warehouseId", record.getWarehouseId());

            Product product = productService.getById(record.getProductId());
            Assert.notNull(product, "ID为【" + record.getProductId() + "】的商品不存在！");

            stock.put("productName", product.getName());
            stock.put("spec", product.getSpec());
            stock.put("productCode", product.getCode());

            DictItem unit = dictItemService.getById(product.getUnitId());
            stock.put("unitName", unit.getName());

            Warehouse warehouse = warehouseService.getById(record.getWarehouseId());
            Assert.notNull(warehouse, "ID为【" + record.getWarehouseId() + "】的仓库不存在！");
            stock.put("warehouseName", warehouse.getName());

            composeStockInfo(record);

            stockList.add(stock);
        }

        data.put("stockList", stockList);
    }

    /**
     * 组装库存信息
     *
     * @param record
     */
    private void composeStockInfo(StockRecord record) {
        List<String> queryProductIdList = new ArrayList<>();
        queryProductIdList.add(record.getProductId());
        List<String> queryWarehouseIdList = new ArrayList<>();
        queryWarehouseIdList.add(record.getWarehouseId());
        List<StockRecord> recordList = recordService.analysisList(startDate, endDate, queryProductIdList, queryWarehouseIdList);

        StockRecord startRecord = recordList.get(0);
        stock.put("startAmount", startRecord.getCurrentQuantity() * startRecord.getPrice());
        stock.put("startQuantity", startRecord.getCurrentQuantity());

        for (StockRecord stockRecord : recordList) {
            if (Define.BUSINESS_TYPE_TRANSFER_IN.equals(stockRecord.getBusinessType())) {
                putSumInfo(stockRecord, "transferInQuantity");
            } else if (Define.BUSINESS_TYPE_TRANSFER_OUT.equals(stockRecord.getBusinessType())) {
                putSumInfo(stockRecord, "transferOutQuantity");
            } else if (Define.BUSINESS_TYPE_STORE_PROFIT.equals(stockRecord.getBusinessType())) {
                putSumInfo(stockRecord, "storeProfitQuantity");
            } else if (Define.BUSINESS_TYPE_STORE_OTHER.equals(stockRecord.getBusinessType())) {
                putSumInfo(stockRecord, "storeOtherQuantity");
            } else if (Define.BUSINESS_TYPE_CHECKOUT_LOSS.equals(stockRecord.getBusinessType())) {
                putSumInfo(stockRecord, "checkoutLossQuantity");
            } else if (Define.BUSINESS_TYPE_CHECKOUT_OTHER.equals(stockRecord.getBusinessType())) {
                putSumInfo(stockRecord, "checkoutOtherQuantity");
            } else {
                putSumInfo(stockRecord, stockRecord.getBusinessType() + "Quantity");
            }
        }

        // TODO 成本调整
        // 入库合计
        double storeTotalQuantity = stock.getDoubleValue("transferInQuantity") + stock.getDoubleValue("buyQuantity")
                + stock.getDoubleValue("returnedQuantity") + stock.getDoubleValue("storeProfitQuantity") + stock.getDoubleValue("storeOtherQuantity");
        stock.put("storeTotalQuantity", storeTotalQuantity);

        // 出库合计
        double checkoutTotalQuantity = stock.getDoubleValue("transferOutQuantity") + stock.getDoubleValue("refundQuantity")
                + stock.getDoubleValue("sellQuantity") + stock.getDoubleValue("checkoutLossQuantity") + stock.getDoubleValue("checkoutOtherQuantity");
        stock.put("checkoutTotalQuantity", checkoutTotalQuantity);

        StockRecord endRecord = recordList.get(recordList.size() - 1);
        stock.put("endAmount", endRecord.getCurrentQuantity() * endRecord.getPrice());
        stock.put("endQuantity", endRecord.getCurrentQuantity());
    }

    private void putSumInfo(StockRecord stockRecord, String quantityKey) {
        double currentQuantity = stock.getDouble(quantityKey) == null ? 0.0 : stock.getDoubleValue(quantityKey);
        stock.put(quantityKey, currentQuantity + stockRecord.getCurrentQuantity());
    }
}
