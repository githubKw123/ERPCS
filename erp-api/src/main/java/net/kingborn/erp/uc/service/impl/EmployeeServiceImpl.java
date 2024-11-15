package net.kingborn.erp.uc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.kingborn.erp.uc.dao.EmployeeDao;
import net.kingborn.erp.uc.model.Employee;
import net.kingborn.erp.uc.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * 职员服务实现
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeDao, Employee> implements EmployeeService {
    @Override
    public Page<Employee> pageSearch(long current, long size, JSONObject query) {
        QueryWrapper<Employee> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("createdTime");

        return this.page(new Page<>(current, size), wrapper);
    }
}
