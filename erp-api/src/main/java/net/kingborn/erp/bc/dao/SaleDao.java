package net.kingborn.erp.bc.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.kingborn.erp.bc.model.Sale;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 销售单Dao
 */
@Component
public interface SaleDao extends BaseMapper<Sale> {

    /**
     * 分页查询
     *
     * @param page
     * @param query
     * @return
     */
    Page<Sale> queryPage(@Param("page") Page<Sale> page, @Param("query") JSONObject query);

}
