package net.kingborn.erp.api.uc;

import com.alibaba.fastjson.JSONObject;
import net.kingborn.test.TestCommand;
import net.kingborn.test.BaseUrl;
import org.junit.Test;

/**
 * 查询用户分页列表
 */
public class TestUserPage extends TestCommand {

    @Override
    public void init() throws Exception {
        headerMap.put("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjkxOTI5NzgxNDQ0NTk5ODEwIiwic3ViIjoiY3d5ZDA4MjIiLCJpYXQiOjE1OTY5MzAzNTYsInJvbGVzIjoiW1wiYWRtaW5cIl0iLCJleHAiOjE1OTY5MzM5NTZ9.fu3AazWlo08bnvVjGq4ArGTbJWQKohtG25V08zNCfEs");

        JSONObject query = new JSONObject();
        query.put("username", "cw");

        postParamsMap.put("query", query);
        postParamsMap.put("current", 1);
        postParamsMap.put("size", 2);
    }

    @Test
    @Override
    public void doTest() {
        System.out.println(formatJson(doPost()));
    }

    @Override
    protected BaseUrl getBaseUrl() {
        return new BaseUrl("localhost", 9090, "/user/page");
    }
}
