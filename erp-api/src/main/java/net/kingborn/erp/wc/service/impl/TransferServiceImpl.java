package net.kingborn.erp.wc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.kingborn.erp.wc.dao.TransferDao;
import net.kingborn.erp.wc.model.Transfer;
import net.kingborn.erp.wc.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferServiceImpl extends ServiceImpl<TransferDao, Transfer> implements TransferService {

    @Autowired
    private TransferDao transferDao;

    @Override
    public Page<Transfer> pageSearch(long current, long size, JSONObject query) {
        return transferDao.queryPage(new Page<>(current, size), query);
    }

    @Override
    public Transfer findByCode(String code) {
        return getOne(new QueryWrapper<Transfer>().eq("code", code));
    }
}
