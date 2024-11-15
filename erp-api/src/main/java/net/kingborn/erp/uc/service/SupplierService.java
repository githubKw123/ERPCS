package net.kingborn.erp.uc.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import net.kingborn.erp.uc.model.Supplier;

/**
 * 供应商服务
 */
public interface SupplierService extends IService<Supplier> {

    /**
     * 根据编码获取对象
     *
     * @param code
     * @return
     */
    Supplier findByCode(String code);

    /**
     * 根据Id获取名称
     *
     * @param id
     * @return
     */
    String getNameById(String id);

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
