package net.kingborn.erp.fc.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.kingborn.erp.fc.model.Payment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 付款Dao
 */
@Component
public interface PaymentDao extends BaseMapper<Payment> {
    /**
     * 分页查询
     *
     * @param page
     * @param query
     * @return
     */
    Page<Payment> queryPage(@Param("page") Page<Payment> page, @Param("query") JSONObject query);
}
