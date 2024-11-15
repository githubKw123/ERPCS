package net.kingborn.erp.uc.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.kingborn.erp.uc.model.Customer;
import net.kingborn.erp.uc.model.Supplier;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 供应商Dao
 */
@Component
public interface SupplierDao extends BaseMapper<Supplier> {

    Page<JSONObject> queryPage(@Param("page") Page<JSONObject> page, @Param("query") JSONObject query);

}
