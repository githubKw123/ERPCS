package net.kingborn.erp.wc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.kingborn.erp.wc.dao.CheckoutDao;
import net.kingborn.erp.wc.model.Checkout;
import net.kingborn.erp.wc.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckoutServiceImpl extends ServiceImpl<CheckoutDao, Checkout> implements CheckoutService {

    @Autowired
    private CheckoutDao checkoutDao;

    @Override
    public Page<Checkout> pageSearch(long current, long size, JSONObject query) {
        return checkoutDao.queryPage(new Page<>(current, size), query);
    }

    @Override
    public Checkout findByCode(String code) {
        return getOne(new QueryWrapper<Checkout>().eq("code", code));
    }
}
