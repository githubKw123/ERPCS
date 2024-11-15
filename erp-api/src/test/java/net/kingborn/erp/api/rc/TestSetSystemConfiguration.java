package net.kingborn.erp.api.rc;

import net.kingborn.erp.api.TestUtil;
import net.kingborn.erp.rc.bean.SystemConfiguration;
import net.kingborn.test.TestCommand;
import net.kingborn.test.BaseUrl;
import org.junit.Test;

/**
 * 设置系统设置
 */
public class TestSetSystemConfiguration extends TestCommand {

    @Override
    public void init() throws Exception {
        headerMap = TestUtil.getAuthHeader();

        SystemConfiguration configuration = new SystemConfiguration();
        configuration.setCompanyName("精博科技");
        configuration.setCompanyAddress("福建福州长乐");
        configuration.setCompanyPhone("18905013333");
        configuration.setCompanyFax("0591-88888888");
        configuration.setCompanyPostCode("350200");

        postParamsMap.put("configuration", configuration);
    }

    @Test
    @Override
    public void doTest() {
        System.out.println(formatJson(doPost()));
    }

    @Override
    protected BaseUrl getBaseUrl() {
        return new BaseUrl("localhost", 9090, "/setSystemConfiguration");
    }
}
