package net.kingborn.erp.wc.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import net.kingborn.erp.wc.model.Checkout;

/**
 * 出库服务
 */
public interface CheckoutService extends IService<Checkout> {

    /**
     * 分页列表
     *
     * @param current
     * @param size
     * @param query
     * @return
     */
    Page<Checkout> pageSearch(long current, long size, JSONObject query);

    /**
     * 根据编码获取出库单
     *
     * @param code
     * @return
     */
    Checkout findByCode(String code);
}
