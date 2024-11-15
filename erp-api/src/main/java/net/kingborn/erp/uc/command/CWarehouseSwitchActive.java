package net.kingborn.erp.uc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.erp.uc.model.Employee;
import net.kingborn.erp.uc.model.Warehouse;
import net.kingborn.erp.uc.service.EmployeeService;
import net.kingborn.erp.uc.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 仓库启停
 */
@Command
public class CWarehouseSwitchActive extends BaseCommand {

    @Autowired
    private WarehouseService warehouseService;

    @Param(required = true)
    private String warehouseId;

    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        Warehouse warehouse = warehouseService.getById(warehouseId);
        Assert.notNull(warehouse, "ID为【" + warehouseId + "】的仓库不存在！");

        warehouse.setActive(!warehouse.isActive());
        warehouseService.saveOrUpdate(warehouse);

        data.put("warehouse", warehouse);
    }
}
