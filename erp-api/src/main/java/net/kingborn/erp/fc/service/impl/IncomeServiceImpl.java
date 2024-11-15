package net.kingborn.erp.fc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.kingborn.erp.fc.dao.IncomeDao;
import net.kingborn.erp.fc.model.Income;
import net.kingborn.erp.fc.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncomeServiceImpl extends ServiceImpl<IncomeDao, Income> implements IncomeService {

    @Autowired
    private IncomeDao incomeDao;

    @Override
    public Page<Income> pageSearch(long current, long size, JSONObject query) {
        return incomeDao.queryPage(new Page<>(current, size), query);
    }

    @Override
    public Income findByCode(String code) {
        return getOne(new QueryWrapper<Income>().eq("code", code));
    }
}
