package net.kingborn.erp.api.rc;

import com.alibaba.fastjson.JSONObject;
import net.kingborn.erp.api.TestUtil;
import net.kingborn.erp.constant.Define;
import net.kingborn.test.BaseUrl;
import net.kingborn.test.TestCommand;
import org.junit.Test;

/**
 * 获取分类列表
 */
public class TestCategoryList extends TestCommand {

    @Override
    public void init() throws Exception {
        headerMap = TestUtil.getAuthHeader();

        JSONObject query = new JSONObject();
        query.put("name", "苹果");

        postParamsMap.put("query", query);
        postParamsMap.put("type", Define.CATEGORY_TYPE_PRODUCT);
    }

    @Test
    @Override
    public void doTest() {
        System.out.println(formatJson(doPost()));
    }

    @Override
    protected BaseUrl getBaseUrl() {
        return new BaseUrl("localhost", 9090, "/category/list");
    }
}
