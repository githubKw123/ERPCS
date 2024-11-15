package net.kingborn.erp.rc.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import net.kingborn.erp.rc.model.Log;
import net.kingborn.erp.rc.model.Menu;

import java.util.List;

/**
 * 日志服务
 */
public interface LogService extends IService<Log> {

    /**
     * 分页列表
     *
     * @param current
     * @param size
     * @param query
     * @return
     */
    Page<Log> pageSearch(long current, long size, JSONObject query);

    /**
     * 记录日志
     *
     * @param type
     *  动作的类型
     * @param userId
     *  操作的用户
     * @param content
     *  动作内容
     */
    void log(int type, long userId, JSONObject content);

    /**
     * 记录用户登录
     *
     * @param username
     */
    void logUserLogin(String username);

    /**
     * 记录新增用户
     *
     * @param username
     */
    void logUserAdd(long userId, String username);

    /**
     * 记录启用用户
     *
     * @param username
     */
    void logUserActive(long userId, String username);

    /**
     * 记录停用用户
     *
     * @param username
     */
    void logUserDeactive(long userId, String username);

    /**
     * 记录修改密码
     *
     * @param userId
     * @param username
     */
    void logUserResetPassword(long userId, String username);

}
