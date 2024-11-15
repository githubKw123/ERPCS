package net.kingborn.erp.wc.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import net.kingborn.erp.wc.model.StockRecord;

import java.util.List;

/**
 * 出入库服务
 */
public interface StockRecordService extends IService<StockRecord> {

    /**
     * 收发明细表
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param productIdList 商品ID列表
     * @param warehouseIdList 仓库ID列表
     * @return
     */
    List<StockRecord> analysisList(String startDate,
                                   String endDate,
                                   List<String> productIdList,
                                   List<String> warehouseIdList);

    /**
     * 获取不同的商品ID列表
     *
     * @param query
     * @return
     */
    List<String> distinctProductIdList(JSONObject query);

    /**
     * 获取某一商品的出入库仓列表
     *
     * @param query
     * @param productId
     * @return
     */
    List<String> distinctWarehouseIdListByProduct(JSONObject query, String productId);

    /**
     * 获取最近出入库记录
     *
     * @param query
     * @param productId
     * @param warehouseId
     * @return
     */
    StockRecord findLatestRecord(JSONObject query, String productId, String warehouseId);

    /**
     * 获取商品仓库ID列表
     *
     * @param query
     * @return
     */
    List<StockRecord> distinctProductWarehouseIdList(JSONObject query);
}
