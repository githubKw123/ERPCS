package net.kingborn.erp.uc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.erp.uc.model.Employee;
import net.kingborn.erp.uc.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 职员详情
 */
@Command
public class CEmployeeDetail extends BaseCommand {

    @Autowired
    private EmployeeService employeeService;

    @Param(required = true)
    private String employeeId;

    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        Employee employee = employeeService.getById(employeeId);
        Assert.notNull(employee, "ID为【" + employeeId + "】的职员不存在！");

        data.put("employee", employee);
    }
}
