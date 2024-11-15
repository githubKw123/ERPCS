package net.kingborn.erp.uc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.kingborn.erp.uc.dao.CustomerContactDao;
import net.kingborn.erp.uc.model.CustomerContact;
import net.kingborn.erp.uc.service.CustomerContactService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 客户联系人服务
 */
@Service
public class CustomerContactServiceImp extends ServiceImpl<CustomerContactDao, CustomerContact> implements CustomerContactService {
    @Override
    public void deleteByCustomer(String customerId) {
        this.remove(new QueryWrapper<CustomerContact>().eq("customerId", customerId));
    }

    @Override
    public List<CustomerContact> findListByCustomer(String customerId) {
        return this.list(new QueryWrapper<CustomerContact>().eq("customerId", customerId));
    }
}
