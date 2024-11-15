package net.kingborn.erp.bc.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import net.kingborn.erp.bc.model.Order;

/**
 * 客户订单服务
 */
public interface OrderService extends IService<Order> {

    /**
     * 分页列表
     *
     * @param current
     * @param size
     * @param query
     * @return
     */
    Page<Order> pageSearch(long current, long size, JSONObject query);

}
