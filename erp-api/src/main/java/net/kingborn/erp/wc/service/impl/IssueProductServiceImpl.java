package net.kingborn.erp.wc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.kingborn.erp.sc.model.PurchaseSupplierSummary;
import net.kingborn.erp.wc.dao.IssueProductDao;
import net.kingborn.erp.wc.model.IssueProduct;
import net.kingborn.erp.wc.service.IssueProductService;
import net.kingborn.erp.sc.model.SaleCustomerSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueProductServiceImpl extends ServiceImpl<IssueProductDao, IssueProduct> implements IssueProductService {

    @Autowired
    private IssueProductDao productDao;

    @Override
    public List<IssueProduct> findListByBusiness(String businessId) {
        return this.list(new QueryWrapper<IssueProduct>().eq("businessId", businessId));
    }

    @Override
    public List<IssueProduct> findListBytype(String businessType) {
        return this.list(new QueryWrapper<IssueProduct>().eq("businessType", businessType));
    }

    @Override
    public void deleteByBusiness(String businessId) {
        this.remove(new QueryWrapper<IssueProduct>().eq("businessId", businessId));
    }

    @Override
    public List<IssueProduct> analysisSaleList(JSONObject query) {
        return productDao.analysisSaleList(query);
    }

    @Override
    public List<IssueProduct> analysisSaleListByProduct(JSONObject query) {
        return productDao.analysisSaleListByProduct(query);
    }

    @Override
    public List<SaleCustomerSummary> analysisSaleListByCustomer(JSONObject query) {
        return productDao.analysisSaleListByCustomer(query);
    }

    @Override
    public List<IssueProduct> analysisPurchaseList(JSONObject query) {
        return productDao.analysisPurchaseList(query);
    }

    @Override
    public List<IssueProduct> analysisPurchaseListByProduct(JSONObject query) {
        return productDao.analysisPurchaseListByProduct(query);
    }

    @Override
    public List<PurchaseSupplierSummary> analysisPurchaseListBySupplier(JSONObject query) {
        return productDao.analysisPurchaseListBySupplier(query);
    }

}
