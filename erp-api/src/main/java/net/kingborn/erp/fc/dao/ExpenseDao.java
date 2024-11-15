package net.kingborn.erp.fc.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.kingborn.erp.fc.model.Expense;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 支出Dao
 */
@Component
public interface ExpenseDao extends BaseMapper<Expense> {
    /**
     * 分页查询
     *
     * @param page
     * @param query
     * @return
     */
    Page<Expense> queryPage(@Param("page") Page<Expense> page, @Param("query") JSONObject query);
}
