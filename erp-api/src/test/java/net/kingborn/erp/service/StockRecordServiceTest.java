package net.kingborn.erp.service;

import com.alibaba.fastjson.JSONObject;
import net.kingborn.erp.wc.model.StockRecord;
import net.kingborn.erp.wc.service.StockRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * 测试
 */
@SpringBootTest
public class StockRecordServiceTest {

    @Autowired
    private StockRecordService recordService;

    @Test
    public void findDistinctProductIdList() {
        JSONObject query = new JSONObject();
        query.put("endDate", "2001-12-01");
        List<String> recordList = recordService.distinctProductIdList(query);

        System.out.println(JSONObject.toJSONString(recordList));
    }

    @Test
    public void findDistinctWarehouseIdListByProduct() {
        JSONObject query = new JSONObject();
        // query.put("endDate", "2001-12-01");
        List<String> recordList = recordService.distinctWarehouseIdListByProduct(query, "1298462990858530818");

        System.out.println(JSONObject.toJSONString(recordList));
    }

    @Test
    public void findLatestRecord() {
        JSONObject query = new JSONObject();

        StockRecord latestRecord = recordService.findLatestRecord(query, "1298462990858530818", "1295255465562857473");
        System.out.println(JSONObject.toJSONString(latestRecord));
    }

    @Test
    public void distinctProductWarehouseIdList() {
        JSONObject query = new JSONObject();

        List<StockRecord> recordList = recordService.distinctProductWarehouseIdList(query);
        System.out.println(JSONObject.toJSONString(recordList));
    }

}
