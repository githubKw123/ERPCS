package net.kingborn.erp.uc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.kingborn.erp.uc.dao.EmployeeDao;
import net.kingborn.erp.uc.dao.WarehouseDao;
import net.kingborn.erp.uc.model.Employee;
import net.kingborn.erp.uc.model.Warehouse;
import net.kingborn.erp.uc.service.EmployeeService;
import net.kingborn.erp.uc.service.WarehouseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 仓库服务实现
 */
@Service
public class WarehouseServiceImpl extends ServiceImpl<WarehouseDao, Warehouse> implements WarehouseService {
    @Override
    public Page<Warehouse> pageSearch(long current, long size, JSONObject query) {
        QueryWrapper<Warehouse> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("createdTime");

        return this.page(new Page<>(current, size), wrapper);
    }

    @Override
    public String getNameById(String id) {
        return getOne(new QueryWrapper<Warehouse>().eq("id", id)).getName();
    }

    @Override
    public List<Warehouse> findByIdList(List<String> idList) {
        if (idList == null || idList.size() == 0) {
            return list(new QueryWrapper<Warehouse>().orderByAsc("createdTime"));
        }
        return list(new QueryWrapper<Warehouse>().in("id", idList).orderByAsc("createdTime"));
    }
}
