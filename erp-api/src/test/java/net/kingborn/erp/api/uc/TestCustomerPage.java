package net.kingborn.erp.api.uc;

import com.alibaba.fastjson.JSONObject;
import net.kingborn.erp.api.TestUtil;
import net.kingborn.test.BaseUrl;
import net.kingborn.test.TestCommand;
import org.junit.Test;

/**
 * 客户分页
 */
public class TestCustomerPage extends TestCommand {

    @Override
    public void init() throws Exception {
        headerMap = TestUtil.getAuthHeader();

        JSONObject query = new JSONObject();
        query.put("name", "张三");
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
        return new BaseUrl("localhost", 9090, "/customer/page");
    }
}
