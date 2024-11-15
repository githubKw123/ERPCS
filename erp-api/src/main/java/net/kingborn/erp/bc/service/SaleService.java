package net.kingborn.erp.bc.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import net.kingborn.erp.bc.model.Sale;

import java.util.List;

/**
 * 销售服务
 */
public interface SaleService extends IService<Sale> {

    /**
     * 分页列表
     *
     * @param current
     * @param size
     * @param query
     * @return
     */
    Page<Sale> pageSearch(long current, long size, JSONObject query);

    /**
     * 根据编码获取销售单
     *
     * @param code
     * @return
     */
    Sale findByCode(String code);

    /**
     * 获取客户已审核的销售单列表
     *
     * @param customerId
     * @return
     */
    List<Sale> findCheckedListByCustomer(String customerId);
}
