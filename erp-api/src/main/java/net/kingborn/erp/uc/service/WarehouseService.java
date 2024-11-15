package net.kingborn.erp.uc.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import net.kingborn.erp.uc.model.Employee;
import net.kingborn.erp.uc.model.Warehouse;

import java.util.List;

/**
 * 仓库服务
 */
public interface WarehouseService extends IService<Warehouse> {

    /**
     * 分页查询
     *
     * @param current
     * @param size
     * @param query
     * @return
     */
    Page<Warehouse> pageSearch(long current, long size, JSONObject query);

    /**
     * 根据Id获取名称
     *
     * @param id
     * @return
     */
    String getNameById(String id);

    /**
     * 根据ID列表获取
     *
     * @param idList
     * @return
     */
    List<Warehouse> findByIdList(List<String> idList);
}
