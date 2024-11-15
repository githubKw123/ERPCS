package net.kingborn.erp.api.rc;

import net.kingborn.erp.api.TestUtil;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.rc.model.Category;
import net.kingborn.test.TestCommand;
import net.kingborn.test.BaseUrl;
import org.junit.Test;

/**
 * 测试保存分类
 */
public class TestCategorySave extends TestCommand {

    @Override
    public void init() throws Exception {
        headerMap = TestUtil.getAuthHeader();

        Category category = new Category();
        category.setId("1292437096899928065");
        category.setType(Define.CATEGORY_TYPE_PRODUCT);
        category.setParentId("1292435079284391938");
        category.setName("美国青苹果");

        postParamsMap.put("category", category);
    }

    @Test
    @Override
    public void doTest() {
        System.out.println(formatJson(doPost()));
    }

    @Override
    protected BaseUrl getBaseUrl() {
        return new BaseUrl("localhost", 9090, "/category/save");
    }
}
