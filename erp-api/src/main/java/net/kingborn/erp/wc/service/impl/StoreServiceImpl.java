package net.kingborn.erp.wc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.kingborn.erp.wc.dao.StoreDao;
import net.kingborn.erp.wc.model.Store;
import net.kingborn.erp.wc.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreServiceImpl extends ServiceImpl<StoreDao, Store> implements StoreService {

    @Autowired
    private StoreDao storeDao;

    @Override
    public Page<Store> pageSearch(long current, long size, JSONObject query) {
        return storeDao.queryPage(new Page<>(current, size), query);
    }

    @Override
    public Store findByCode(String code) {
        return getOne(new QueryWrapper<Store>().eq("code", code));
    }
}
