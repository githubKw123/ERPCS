package net.kingborn.erp.api.uc;

import net.kingborn.erp.api.TestUtil;
import net.kingborn.test.BaseUrl;
import net.kingborn.test.TestCommand;
import org.junit.Test;

/**
 * 删除客户
 */
public class TestCustomerDelete extends TestCommand {
    @Override
    public void init() throws Exception {
        headerMap = TestUtil.getAuthHeader();

        postParamsMap.put("customerId", "1295606302059298818");
    }

    @Test
    @Override
    public void doTest() {
        System.out.println(formatJson(doPost()));
    }

    @Override
    protected BaseUrl getBaseUrl() {
        return new BaseUrl("localhost", 9090, "/customer/delete");
    }
}
