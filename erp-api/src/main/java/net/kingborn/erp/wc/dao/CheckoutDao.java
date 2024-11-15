package net.kingborn.erp.wc.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.kingborn.erp.wc.model.Checkout;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 出库Dao
 */
@Component
public interface CheckoutDao extends BaseMapper<Checkout> {
    /**
     * 分页查询
     *
     * @param page
     * @param query
     * @return
     */
    Page<Checkout> queryPage(@Param("page") Page<Checkout> page, @Param("query") JSONObject query);
}
