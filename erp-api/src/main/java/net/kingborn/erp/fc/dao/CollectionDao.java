package net.kingborn.erp.fc.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.kingborn.erp.fc.model.Collection;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 收款Dao
 */
@Component
public interface CollectionDao extends BaseMapper<Collection> {
    /**
     * 分页查询
     *
     * @param page
     * @param query
     * @return
     */
    Page<Collection> queryPage(@Param("page") Page<Collection> page, @Param("query") JSONObject query);
}
