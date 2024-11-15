package net.kingborn.erp.api.rc;

import com.alibaba.fastjson.JSONObject;
import net.kingborn.test.TestCommand;
import net.kingborn.test.BaseUrl;
import org.junit.Test;

/**
 * 测试获取日志列表
 */
public class TestLogPage extends TestCommand {

    @Override
    public void init() throws Exception {
        headerMap.put("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjkxOTI5NzgxNDQ0NTk5ODEwIiwic3ViIjoiY3d5ZDA4MjIiLCJpYXQiOjE1OTY5MzQzNjYsInJvbGVzIjoiW1wiYWRtaW5cIl0iLCJleHAiOjE5NTY5MzQzNjZ9.sCydA4m-BTiWG6iv0V2r5yA7VVZQ9qYn1wI5RNC4woU");

        // postParamsMap.put("userId", 1289333755057299456L);

        JSONObject query = new JSONObject();
        query.put("userId", 1291929781444599810L);
        query.put("startTime", "2020-08-09");
        query.put("endTime", "2020-08-09");

        postParamsMap.put("query", query);
    }

    @Test
    @Override
    public void doTest() {
        System.out.println(formatJson(doPost()));
    }

    @Override
    protected BaseUrl getBaseUrl() {
        return new BaseUrl("localhost", 9090, "/log/page");
    }
}
