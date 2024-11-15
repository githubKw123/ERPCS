package net.kingborn.erp.bc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.kingborn.erp.bc.dao.PurchaseDao;
import net.kingborn.erp.bc.model.Purchase;
import net.kingborn.erp.bc.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, Purchase> implements PurchaseService {

    @Autowired
    private PurchaseDao purchaseDao;

    @Override
    public Page<Purchase> pageSearch(long current, long size, JSONObject query) {
        return purchaseDao.queryPage(new Page<>(current, size), query);
    }

    @Override
    public Purchase findByCode(String code) {
        return getOne(new QueryWrapper<Purchase>().eq("code", code));
    }

    @Override
    public List<Purchase> findCheckedListBySupplier(String supplierId) {
        return this.list(new QueryWrapper<Purchase>().eq("supplierId", supplierId).eq("checked", true).orderByDesc("createdTime"));
    }
}
