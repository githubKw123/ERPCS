package net.kingborn.erp.fc.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import net.kingborn.erp.fc.model.Payment;

/**
 * 付款服务
 */
public interface PaymentService extends IService<Payment> {

    /**
     * 分页列表
     *
     * @param current
     * @param size
     * @param query
     * @return
     */
    Page<Payment> pageSearch(long current, long size, JSONObject query);

    /**
     * 根据编码获取付款单
     *
     * @param code
     * @return
     */
    Payment findByCode(String code);
}
