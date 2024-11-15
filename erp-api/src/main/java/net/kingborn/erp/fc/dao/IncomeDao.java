package net.kingborn.erp.fc.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.kingborn.erp.fc.model.Income;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 收入Dao
 */
@Component
public interface IncomeDao extends BaseMapper<Income> {
    /**
     * 分页查询
     *
     * @param page
     * @param query
     * @return
     */
    Page<Income> queryPage(@Param("page") Page<Income> page, @Param("query") JSONObject query);
}
