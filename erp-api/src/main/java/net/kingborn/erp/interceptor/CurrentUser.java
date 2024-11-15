package net.kingborn.erp.interceptor;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

/**
 * 当前用户
 */
@Data
public class CurrentUser {

    private long id;

    private String username;

    private JSONArray roles;



}
