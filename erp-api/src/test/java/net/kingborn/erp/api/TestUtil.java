package net.kingborn.erp.api;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试的工具
 */
public class TestUtil {

    /**
     * 获取带有授权标识的头部
     *
     * @return
     */
    public static Map<String, String> getAuthHeader() {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjkxOTI5NzgxNDQ0NTk5ODEwIiwic3ViIjoiY3d5ZDA4MjIiLCJpYXQiOjE1OTY5MzQzNjYsInJvbGVzIjoiW1wiYWRtaW5cIl0iLCJleHAiOjE5NTY5MzQzNjZ9.sCydA4m-BTiWG6iv0V2r5yA7VVZQ9qYn1wI5RNC4woU");

        return headerMap;
    }

}
