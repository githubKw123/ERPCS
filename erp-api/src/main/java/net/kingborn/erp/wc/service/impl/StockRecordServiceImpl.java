package net.kingborn.erp.wc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.kingborn.erp.wc.dao.StockRecordDao;
import net.kingborn.erp.wc.model.StockRecord;
import net.kingborn.erp.wc.service.StockRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockRecordServiceImpl extends ServiceImpl<StockRecordDao, StockRecord> implements StockRecordService {

    @Autowired
    private StockRecordDao recordDao;

    @Override
    public List<StockRecord> analysisList(String startDate,
                                          String endDate,
                                          List<String> productIdList,
                                          List<String> warehouseIdList) {
        return recordDao.analysisList(startDate, endDate, productIdList, warehouseIdList);
    }

    @Override
    public List<String> distinctProductIdList(JSONObject query) {
        return recordDao.distinctProductIdList(query);
    }

    @Override
    public List<String> distinctWarehouseIdListByProduct(JSONObject query, String productId) {
        return recordDao.distinctWarehouseIdListByProduct(query, productId);
    }

    @Override
    public StockRecord findLatestRecord(JSONObject query, String productId, String warehouseId) {
        return recordDao.findLatestRecord(query, productId, warehouseId);
    }

    @Override
    public List<StockRecord> distinctProductWarehouseIdList(JSONObject query) {
        return recordDao.distinctProductWarehouseIdList(query);
    }
}
