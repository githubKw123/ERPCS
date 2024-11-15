package net.kingborn.erp.api.rc;

import net.kingborn.test.TestCommand;
import net.kingborn.test.BaseUrl;
import org.junit.Test;

/**
 * 菜单列表
 */
public class TestMenuList extends TestCommand {

    @Override
    public void init() throws Exception {
        headerMap.put("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjkxOTI5NzgxNDQ0NTk5ODEwIiwic3ViIjoiY3d5ZDA4MjIiLCJpYXQiOjE1OTY4NzUxMTQsInJvbGVzIjoiW1wiYWRtaW5cIl0iLCJleHAiOjE1OTY4Nzg3MTR9.0IyptVvrQ_I1VdqJ0FYwewSULY7soaPpH6vCiYY1lZU");

        postParamsMap.put("hello", "world");
    }

    @Test
    @Override
    public void doTest() {
        System.out.println(formatJson(doPost()));
    }

    @Override
    protected BaseUrl getBaseUrl() {
        return new BaseUrl("localhost", 9090, "/menu/list");
    }
}
