package net.kingborn.erp.uc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.erp.uc.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 职员删除
 */
@Command
public class CEmployeeDelete extends BaseCommand {

    @Autowired
    private EmployeeService employeeService;

    @Param(required = true)
    private String employeeId;

    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        employeeService.removeById(employeeId);
    }
}
