package net.kingborn.erp.bc.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.kingborn.erp.bc.model.Purchase;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 购货Dao
 */
@Component
public interface PurchaseDao extends BaseMapper<Purchase> {

    /**
     * 分页查询
     *
     * @param page
     * @param query
     * @return
     */
    Page<Purchase> queryPage(@Param("page") Page<Purchase> page, @Param("query") JSONObject query);
}
