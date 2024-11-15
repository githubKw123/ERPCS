package net.kingborn.erp.api.sc;

import net.kingborn.erp.api.TestUtil;
import net.kingborn.test.BaseUrl;
import net.kingborn.test.TestCommand;
import org.junit.Test;

/**
 * 供应商对账单
 */
public class TestAnalysisPayableSupplierList extends TestCommand {

    @Override
    public void init() throws Exception {
        headerMap = TestUtil.getAuthHeader();

//        postParamsMap.put("startDate", "2020-08-27");
//        postParamsMap.put("endDate", "2022-08-27");

        String supplierId = "1297813081146834946";
        postParamsMap.put("supplierId", supplierId);

    }

    @Test
    @Override
    public void doTest() {
        System.out.println(formatJson(doPost()));
    }

    @Override
    protected BaseUrl getBaseUrl() {
        return new BaseUrl("localhost", 9090, "/analysis/payable/supplierList");
    }
}
