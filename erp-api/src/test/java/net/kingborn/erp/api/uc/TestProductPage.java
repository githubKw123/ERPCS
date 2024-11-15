package net.kingborn.erp.api.uc;

import com.alibaba.fastjson.JSONObject;
import net.kingborn.erp.api.TestUtil;
import net.kingborn.test.BaseUrl;
import net.kingborn.test.TestCommand;
import org.junit.Test;

/**
 * 商品分页列表
 */
public class TestProductPage extends TestCommand {
    @Override
    public void init() throws Exception {
        headerMap = TestUtil.getAuthHeader();

        JSONObject query = new JSONObject();
        query.put("name", "nggu");
        // query.put("categoryId", "1292435079284391938");

        postParamsMap.put("current", 1);
        postParamsMap.put("size", 2);
        postParamsMap.put("query", query);
    }

    @Test
    @Override
    public void doTest() {
        System.out.println(formatJson(doPost()));
    }

    @Override
    protected BaseUrl getBaseUrl() {
        return new BaseUrl("localhost", 9090, "/product/page");
    }
}
