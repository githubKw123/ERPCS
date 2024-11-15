package net.kingborn.erp.uc.command;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.uc.model.Employee;
import net.kingborn.erp.uc.model.Warehouse;
import net.kingborn.erp.uc.service.EmployeeService;
import net.kingborn.erp.uc.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 仓库分页列表
 */
@Command
public class CWarehousePage extends BaseCommand {

    @Autowired
    private WarehouseService warehouseService;

    @Param(defaultValue = "{}")
    private JSONObject query; // 查询对象
    @Param(defaultValue = Define.CURRENT)
    private long current; // 页码
    @Param(defaultValue = Define.SIZE)
    private long size; // 每页数量

    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        Page<Warehouse> warehousePage = warehouseService.pageSearch(current, size, query);

        data.put("warehousePage", warehousePage);
    }
}
