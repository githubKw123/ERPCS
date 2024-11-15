package net.kingborn.erp.uc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.uc.dao.CustomerContactDao;
import net.kingborn.erp.uc.dao.CustomerDao;
import net.kingborn.erp.uc.model.Customer;
import net.kingborn.erp.uc.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 客户服务实现
 */
@Service
@Transactional
public class CustomerServiceImpl extends ServiceImpl<CustomerDao, Customer> implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Override
    public Customer findByCode(String code) {
        return this.getOne(new QueryWrapper<Customer>().eq("code", code));
    }

    @Override
    public Page<JSONObject> pageSearch(long current, long size, JSONObject query) {
        return customerDao.queryPage(new Page<JSONObject>(current, size), query);
    }

}
