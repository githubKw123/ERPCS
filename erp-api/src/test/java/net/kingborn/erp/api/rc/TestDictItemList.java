package net.kingborn.erp.api.rc;

import net.kingborn.erp.api.TestUtil;
import net.kingborn.erp.constant.Define;
import net.kingborn.test.BaseUrl;
import net.kingborn.test.TestCommand;
import org.junit.Test;

/**
 * 测试获取字典项列表
 */
public class TestDictItemList extends TestCommand {

    @Override
    public void init() throws Exception {
        headerMap = TestUtil.getAuthHeader();

        postParamsMap.put("dictCode", Define.DICT_CODE_UNIT);
    }

    @Test
    @Override
    public void doTest() {
        System.out.println(formatJson(doPost()));
    }

    @Override
    protected BaseUrl getBaseUrl() {
        return new BaseUrl("localhost", 9090, "/dict/itemList");
    }
}
