package net.kingborn.erp.bc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.kingborn.erp.bc.dao.SaleDao;
import net.kingborn.erp.bc.model.Sale;
import net.kingborn.erp.bc.service.SaleService;
import net.kingborn.erp.rc.model.DictItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleServiceImpl extends ServiceImpl<SaleDao, Sale> implements SaleService {

    @Autowired
    private SaleDao saleDao;

    @Override
    public Page<Sale> pageSearch(long current, long size, JSONObject query) {
        return saleDao.queryPage(new Page<>(current, size), query);
    }

    @Override
    public Sale findByCode(String code) {
        return getOne(new QueryWrapper<Sale>().eq("code", code));
    }

    @Override
    public List<Sale> findCheckedListByCustomer(String customerId) {
        return this.list(new QueryWrapper<Sale>().eq("customerId", customerId).eq("checked", true).orderByDesc("createdTime"));
    }
}
