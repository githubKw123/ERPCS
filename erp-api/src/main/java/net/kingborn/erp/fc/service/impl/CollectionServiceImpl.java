package net.kingborn.erp.fc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.kingborn.erp.fc.dao.CollectionDao;
import net.kingborn.erp.fc.model.Collection;
import net.kingborn.erp.fc.service.CollectionService;
import net.kingborn.erp.wc.dao.StoreDao;
import net.kingborn.erp.wc.model.Store;
import net.kingborn.erp.wc.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollectionServiceImpl extends ServiceImpl<CollectionDao, Collection> implements CollectionService {

    @Autowired
    private CollectionDao collectionDao;

    @Override
    public Page<Collection> pageSearch(long current, long size, JSONObject query) {
        return collectionDao.queryPage(new Page<>(current, size), query);
    }

    @Override
    public Collection findByCode(String code) {
        return getOne(new QueryWrapper<Collection>().eq("code", code));
    }
}
