package net.kingborn.erp.wc.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.kingborn.erp.wc.model.Store;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 入库Dao
 */
@Component
public interface StoreDao extends BaseMapper<Store> {
    /**
     * 分页查询
     *
     * @param page
     * @param query
     * @return
     */
    Page<Store> queryPage(@Param("page") Page<Store> page, @Param("query") JSONObject query);
}
