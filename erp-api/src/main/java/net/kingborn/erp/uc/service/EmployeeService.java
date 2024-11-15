package net.kingborn.erp.uc.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import net.kingborn.erp.uc.model.Employee;

/**
 * 职员服务
 */
public interface EmployeeService extends IService<Employee> {

    /**
     * 分页查询
     *
     * @param current
     * @param size
     * @param query
     * @return
     */
    Page<Employee> pageSearch(long current, long size, JSONObject query);
}
