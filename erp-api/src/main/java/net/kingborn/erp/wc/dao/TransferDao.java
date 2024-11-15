package net.kingborn.erp.wc.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.kingborn.erp.wc.model.Transfer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 调拨Dao
 */
@Component
public interface TransferDao extends BaseMapper<Transfer> {
    /**
     * 分页查询
     *
     * @param page
     * @param query
     * @return
     */
    Page<Transfer> queryPage(@Param("page") Page<Transfer> page, @Param("query") JSONObject query);
}
