package net.kingborn.erp.api.bc;

import com.alibaba.fastjson.JSONObject;
import net.kingborn.erp.api.TestUtil;
import net.kingborn.test.BaseUrl;
import net.kingborn.test.TestCommand;
import org.junit.Test;

/**
 * 客户订单分页
 */
public class TestOrderPage extends TestCommand {

    @Override
    public void init() throws Exception {
        headerMap = TestUtil.getAuthHeader();

        JSONObject query = new JSONObject();
        query.put("name", "lix");
        query.put("startDate", "2020-08-27");
        query.put("endDate", "2022-08-27");
        postParamsMap.put("query", query);

        postParamsMap.put("current", 1);
        postParamsMap.put("size", 10);
    }

    @Test
    @Override
    public void doTest() {
        System.out.println(formatJson(doPost()));
    }

    @Override
    protected BaseUrl getBaseUrl() {
        return new BaseUrl("localhost", 9090, "/order/page");
    }
}
