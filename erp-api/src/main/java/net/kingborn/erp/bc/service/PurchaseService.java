package net.kingborn.erp.bc.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import net.kingborn.erp.bc.model.Purchase;

import java.util.List;

/**
 * 购货服务
 */
public interface PurchaseService extends IService<Purchase> {

    /**
     * 分页列表
     *
     * @param current
     * @param size
     * @param query
     * @return
     */
    Page<Purchase> pageSearch(long current, long size, JSONObject query);

    /**
     * 根据编码获取购货单
     *
     * @param code
     * @return
     */
    Purchase findByCode(String code);

    /**
     * 获取供应商已审核的采购单列表
     *
     * @param supplierId
     * @return
     */
    List<Purchase> findCheckedListBySupplier(String supplierId);
}
