package net.kingborn.erp.uc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.kingborn.erp.uc.dao.SupplierDao;
import net.kingborn.erp.uc.model.Supplier;
import net.kingborn.erp.uc.model.Warehouse;
import net.kingborn.erp.uc.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 供应商服务实现
 */
@Service
public class SupplierServiceImpl extends ServiceImpl<SupplierDao, Supplier> implements SupplierService {

    @Autowired
    private SupplierDao supplierDao;

    @Override
    public Supplier findByCode(String code) {
        return this.getOne(new QueryWrapper<Supplier>().eq("code", code));
    }

    @Override
    public String getNameById(String id) {
        return getOne(new QueryWrapper<Supplier>().eq("id", id)).getName();
    }

    @Override
    public Page<JSONObject> pageSearch(long current, long size, JSONObject query) {
        return supplierDao.queryPage(new Page<JSONObject>(current, size), query);
    }
}
