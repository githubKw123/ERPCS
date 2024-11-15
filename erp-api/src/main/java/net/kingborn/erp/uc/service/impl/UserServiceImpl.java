package net.kingborn.erp.uc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.util.SimpleValidator;
import net.kingborn.erp.uc.dao.UserDao;
import net.kingborn.erp.uc.model.User;
import net.kingborn.erp.uc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findByUsername(String username) {
        return userDao.selectOne(new QueryWrapper<User>().eq("username", username));
    }

    @Override
    public User findByMobile(String mobile) {
        return userDao.selectOne(new QueryWrapper<User>().eq("mobile", mobile));
    }

    @Override
    public User findByLoginName(String loginName) {
        if (SimpleValidator.validateMobile(loginName)) {
            return findByMobile(loginName);
        }

        return findByUsername(loginName);
    }

    @Override
    public Page<User> pageSearch(long current, long size, JSONObject query) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (StrKit.notBlank(query.getString("username"))) {
            wrapper.like("username", "%" + query.getString("username") + "%");
        }
        if (StrKit.notBlank(query.getString("name"))) {
            wrapper.like("name", "%" + query.getString("name") + "%");
        }
        wrapper.orderByDesc("createdTime");
        Page<User> userPage = userDao.selectPage(new Page<>(current, size), wrapper);

        return userPage;
    }
}
