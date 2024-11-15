package net.kingborn.erp.api.uc;

import net.kingborn.test.TestCommand;
import net.kingborn.test.BaseUrl;
import org.junit.Test;

/**
 * 测试登录
 */
public class TestUserLogin extends TestCommand {

    @Override
    public void init() throws Exception {
        postParamsMap.put("loginName", "cwyd0822");
        postParamsMap.put("password", "123456");
    }

    @Test
    @Override
    public void doTest() {
        System.out.println(formatJson(doPost()));
    }

    @Override
    protected BaseUrl getBaseUrl() {
        return new BaseUrl("localhost", 9090, "/user/login");
    }
}
