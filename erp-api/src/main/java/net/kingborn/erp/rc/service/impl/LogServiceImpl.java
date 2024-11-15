package net.kingborn.erp.rc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.kingborn.core.exception.Assert;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.rc.dao.LogDao;
import net.kingborn.erp.rc.model.Log;
import net.kingborn.erp.rc.service.LogService;
import net.kingborn.erp.uc.model.User;
import net.kingborn.erp.uc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LogServiceImpl extends ServiceImpl<LogDao, Log> implements LogService {

    @Autowired
    private LogDao logDao;
    @Autowired
    private UserService userService;

    @Override
    public Page<Log> pageSearch(long current, long size, JSONObject query) {
        QueryWrapper<Log> wrapper = new QueryWrapper<>();
        if (StrKit.notBlank(query.getString("userId"))) {
            wrapper.eq("userId", query.getString("userId"));
        }
        if (query.getDate("startTime") != null || query.getDate("endTime") != null) {
            wrapper.between("createdTime", query.getDate("startTime"), query.getDate("endTime"));
        }
        wrapper.orderByDesc("createdTime");

        Page<Log> logPage = logDao.selectPage(new Page<>(current, size), wrapper);

        for (Log log : logPage.getRecords()) {
            log.put("description", composeDescription(log));
            log.remove("content");
        }

        return logPage;
    }

    /**
     * 组装描述
     *
     * @param log
     * @return
     */
    private String composeDescription(Log log) {
        String description;
        switch (log.getType()) {
            case Define.LOG_TYPE_LOGIN:
                description = "登录成功 用户名：" + log.getContentObject().getString("username");
                break;
            case Define.LOG_TYPE_USER_ADD:
                description = "新增用户 用户名：" + log.getContentObject().getString("username");
                break;
            case Define.LOG_TYPE_USER_ACTIVATE:
                description = "用户启用 用户名：" + log.getContentObject().getString("username");
                break;
            case Define.LOG_TYPE_USER_DEACTIVATE:
                description = "用户停用 用户名：" + log.getContentObject().getString("username");
                break;
            case Define.LOG_TYPE_USER_RESET_PASSWORD:
                description = "强制修改密码成功 用户名：" + log.getContentObject().getString("username");
                break;

            default:
                description = "";
        }

        return description;
    }

    @Override
    public void log(int type, long userId, JSONObject content) {
        Log log = new Log();
        log.setType(type);
        log.setContentObject(content);

        if (userId != 0) {
            User user = userService.getById(userId);
            Assert.notNull(user, "ID为【" + userId + "】的用户不存在！");

            log.setUserId(user.getId());
            log.setUsername(user.getUsername());
            log.setName(user.getName());
        }

        save(log);
    }

    @Override
    public void logUserLogin(String username) {
        JSONObject content = new JSONObject();
        content.put("username", username);

        // 这里的userId=0是故意的，因为产生登录事件时，用户是处于未登录状态的
        log(Define.LOG_TYPE_LOGIN, 0, content);
    }

    @Override
    public void logUserAdd(long userId, String username) {
        JSONObject content = new JSONObject();
        content.put("username", username);

        log(Define.LOG_TYPE_USER_ADD, userId, content);
    }

    @Override
    public void logUserActive(long userId, String username) {
        JSONObject content = new JSONObject();
        content.put("username", username);

        log(Define.LOG_TYPE_USER_ACTIVATE, userId, content);
    }

    @Override
    public void logUserDeactive(long userId, String username) {
        JSONObject content = new JSONObject();
        content.put("username", username);

        log(Define.LOG_TYPE_USER_DEACTIVATE, userId, content);
    }

    @Override
    public void logUserResetPassword(long userId, String username) {
        JSONObject content = new JSONObject();
        content.put("username", username);

        log(Define.LOG_TYPE_USER_RESET_PASSWORD, userId, content);
    }
}
