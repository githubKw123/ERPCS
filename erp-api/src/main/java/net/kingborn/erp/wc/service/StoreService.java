package net.kingborn.erp.wc.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import net.kingborn.erp.wc.model.Store;

/**
 * 入库服务
 */
public interface StoreService extends IService<Store> {

    /**
     * 分页列表
     *
     * @param current
     * @param size
     * @param query
     * @return
     */
    Page<Store> pageSearch(long current, long size, JSONObject query);

    /**
     * 根据编码获取入库单
     *
     * @param code
     * @return
     */
    Store findByCode(String code);
}
