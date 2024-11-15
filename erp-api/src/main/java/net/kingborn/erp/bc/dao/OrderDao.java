package net.kingborn.erp.bc.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.kingborn.erp.bc.model.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 客户订单Dao
 */
@Component
public interface OrderDao extends BaseMapper<Order> {

    /**
     * 分页查询
     *
     * @param page
     * @param query
     * @return
     */
    Page<Order> queryPage(@Param("page") Page<Order> page, @Param("query") JSONObject query);

}
