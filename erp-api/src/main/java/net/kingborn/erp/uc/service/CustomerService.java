package net.kingborn.erp.uc.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import net.kingborn.erp.uc.model.Customer;

/**
 * 客户服务
 */
public interface CustomerService extends IService<Customer> {

    /**
     * 根据编码获取对象
     *
     * @param code
     * @return
     */
    Customer findByCode(String code);

    /**
     * 分页列表
     *
     * @param current
     * @param size
     * @param query
     * @return
     */
    Page<JSONObject> pageSearch(long current, long size, JSONObject query);
}
