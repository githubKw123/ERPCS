package net.kingborn.erp.uc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.kingborn.erp.uc.model.CustomerContact;

import java.util.List;

/**
 * 客户联系人服务
 */
public interface CustomerContactService extends IService<CustomerContact> {

    /**
     * 根据客户删除
     *
     * @param customerId
     */
    void deleteByCustomer(String customerId);

    /**
     * 根据客户获取联系人列表
     *
     * @param customerId
     * @return
     */
    List<CustomerContact> findListByCustomer(String customerId);
}
