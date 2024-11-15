package net.kingborn.erp.uc.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import net.kingborn.erp.uc.model.User;

/**
 * 用户服务
 */
public interface UserService extends IService<User> {
    
    User findByUsername(String username);

    User findByMobile(String mobile);

    User findByLoginName(String loginName);

    /**
     * 用户分页列表
     *
     * @param current
     *  当前页码
     * @param size
     *  每页数量
     * @param query
     *  查询对象
     * @return
     */
    Page<User> pageSearch(long current, long size, JSONObject query);
}
