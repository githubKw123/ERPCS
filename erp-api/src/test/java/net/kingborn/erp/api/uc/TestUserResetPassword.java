package net.kingborn.erp.api.uc;

import net.kingborn.test.TestCommand;
import net.kingborn.test.BaseUrl;
import org.junit.Test;

/**
 * 测试重置密码
 */
public class TestUserResetPassword extends TestCommand {

    @Override
    public void init() throws Exception {
        headerMap.put("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjkxOTI5NzgxNDQ0NTk5ODEwIiwic3ViIjoiY3d5ZDA4MjIiLCJpYXQiOjE1OTY5MzQzNjYsInJvbGVzIjoiW1wiYWRtaW5cIl0iLCJleHAiOjE5NTY5MzQzNjZ9.sCydA4m-BTiWG6iv0V2r5yA7VVZQ9qYn1wI5RNC4woU");

        postParamsMap.put("userId", 1289333755057299456L);
        postParamsMap.put("password", "123456");
    }

    @Test
    @Override
    public void doTest() {
        System.out.println(formatJson(doPost()));
    }

    @Override
    protected BaseUrl getBaseUrl() {
        return new BaseUrl("localhost", 9090, "/user/resetPassword");
    }
}
