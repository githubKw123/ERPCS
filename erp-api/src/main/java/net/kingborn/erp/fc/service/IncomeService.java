package net.kingborn.erp.fc.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import net.kingborn.erp.fc.model.Income;

/**
 * 收入服务
 */
public interface IncomeService extends IService<Income> {

    /**
     * 分页列表
     *
     * @param current
     * @param size
     * @param query
     * @return
     */
    Page<Income> pageSearch(long current, long size, JSONObject query);

    /**
     * 根据编码获取入库单
     *
     * @param code
     * @return
     */
    Income findByCode(String code);
}
