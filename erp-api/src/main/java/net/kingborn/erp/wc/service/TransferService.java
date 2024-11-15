package net.kingborn.erp.wc.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import net.kingborn.erp.wc.model.Transfer;

/**
 * 调拨服务
 */
public interface TransferService extends IService<Transfer> {

    /**
     * 分页列表
     *
     * @param current
     * @param size
     * @param query
     * @return
     */
    Page<Transfer> pageSearch(long current, long size, JSONObject query);

    /**
     * 根据编码获取调拨单
     *
     * @param code
     * @return
     */
    Transfer findByCode(String code);
}
