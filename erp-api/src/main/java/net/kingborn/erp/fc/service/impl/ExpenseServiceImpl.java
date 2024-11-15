package net.kingborn.erp.fc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.kingborn.erp.fc.dao.ExpenseDao;
import net.kingborn.erp.fc.model.Expense;
import net.kingborn.erp.fc.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseServiceImpl extends ServiceImpl<ExpenseDao, Expense> implements ExpenseService {

    @Autowired
    private ExpenseDao expenseDao;

    @Override
    public Page<Expense> pageSearch(long current, long size, JSONObject query) {
        return expenseDao.queryPage(new Page<>(current, size), query);
    }

    @Override
    public Expense findByCode(String code) {
        return getOne(new QueryWrapper<Expense>().eq("code", code));
    }
}
