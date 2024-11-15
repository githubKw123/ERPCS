package net.kingborn.erp.wc.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.kingborn.erp.wc.model.Stock;
import net.kingborn.erp.wc.model.StockRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 出入库Dao
 */
@Component
public interface StockRecordDao extends BaseMapper<StockRecord> {

    /**
     * 收发明细
     * @return
     */
    List<StockRecord> analysisList(@Param("startDate") String startDate,
                                   @Param("endDate") String endDate,
                                   @Param("productIdList") List<String> productIdList,
                                   @Param("warehouseIdList") List<String> warehouseIdList);

    /**
     * 获取不同的商品ID列表
     *
     * @param query
     * @return
     */
    List<String> distinctProductIdList(@Param("query") JSONObject query);

    /**
     * 获取商品的仓库
     *
     * @param query
     * @param productId
     * @return
     */
    List<String> distinctWarehouseIdListByProduct(@Param("query") JSONObject query, @Param("productId") String productId);

    /**
     * 获取商品在仓库中最近出入记录
     *
     * @param query
     * @param productId
     * @param warehouseId
     * @return
     */
    StockRecord findLatestRecord(@Param("query") JSONObject query, @Param("productId") String productId, @Param("warehouseId") String warehouseId);

    /**
     * 获取
     *
     * @param query
     * @return
     */
    List<StockRecord> distinctProductWarehouseIdList(@Param("query") JSONObject query);
}
