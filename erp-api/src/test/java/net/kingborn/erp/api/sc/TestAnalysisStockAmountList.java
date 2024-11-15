package net.kingborn.erp.api.sc;

import net.kingborn.erp.api.TestUtil;
import net.kingborn.test.BaseUrl;
import net.kingborn.test.TestCommand;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品库存余额表
 */
public class TestAnalysisStockAmountList extends TestCommand {

    @Override
    public void init() throws Exception {
        headerMap = TestUtil.getAuthHeader();

//        postParamsMap.put("startDate", "2020-08-27");
//        postParamsMap.put("endDate", "2022-08-27");

//        List<String> warehouseIdList = new ArrayList<>();
//        warehouseIdList.add("1295255465562857473");
//        postParamsMap.put("warehouseIdList", warehouseIdList);
    }

    @Test
    @Override
    public void doTest() {
        System.out.println(formatJson(doPost()));
    }

    @Override
    protected BaseUrl getBaseUrl() {
        return new BaseUrl("localhost", 9090, "/analysis/stock/amountList");
    }
}
