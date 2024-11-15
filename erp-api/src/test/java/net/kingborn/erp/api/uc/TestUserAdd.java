package net.kingborn.erp.api.uc;

import net.kingborn.erp.uc.model.User;
import net.kingborn.test.TestCommand;
import net.kingborn.test.BaseUrl;
import org.junit.Test;

/**
 * 新增用户
 */
public class TestUserAdd extends TestCommand {
    @Override
    public void init() throws Exception {
        headerMap.put("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjkxOTI5NzgxNDQ0NTk5ODEwIiwic3ViIjoiY3d5ZDA4MjIiLCJpYXQiOjE1OTY5MzQzNjYsInJvbGVzIjoiW1wiYWRtaW5cIl0iLCJleHAiOjE5NTY5MzQzNjZ9.sCydA4m-BTiWG6iv0V2r5yA7VVZQ9qYn1wI5RNC4woU");

        User user = new User();
        user.setUsername("chenwei11");
        user.setPassword("123456");
        user.setName("陈伟");
        user.setMobile("18905015556");

        postParamsMap.put("user", user);
    }

    @Test
    @Override
    public void doTest() {
        System.out.println(formatJson(doPost()));
    }

    @Override
    protected BaseUrl getBaseUrl() {
        return new BaseUrl("localhost", 9090, "/user/add");
    }
}
