package net.kingborn.erp.uc.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.kingborn.erp.uc.model.Customer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 客户Dao
 */
@Component
public interface CustomerDao extends BaseMapper<Customer> {

    Page<JSONObject> queryPage(@Param("page") Page<JSONObject> page, @Param("query") JSONObject query);

}
