package net.kingborn.erp.uc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.erp.uc.service.EmployeeService;
import net.kingborn.erp.uc.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 仓库删除
 */
@Command
public class CWarehouseDelete extends BaseCommand {

    @Autowired
    private WarehouseService warehouseService;

    @Param(required = true)
    private String warehouseId;

    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        warehouseService.removeById(warehouseId);
    }
}
