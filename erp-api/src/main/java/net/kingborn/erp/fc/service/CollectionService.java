package net.kingborn.erp.fc.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import net.kingborn.erp.fc.model.Collection;

/**
 * 收款服务
 */
public interface CollectionService extends IService<Collection> {

    /**
     * 分页列表
     *
     * @param current
     * @param size
     * @param query
     * @return
     */
    Page<Collection> pageSearch(long current, long size, JSONObject query);

    /**
     * 根据编码获取入库单
     *
     * @param code
     * @return
     */
    Collection findByCode(String code);
}
