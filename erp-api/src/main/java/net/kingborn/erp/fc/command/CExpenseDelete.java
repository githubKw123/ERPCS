package net.kingborn.erp.fc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.erp.fc.model.Expense;
import net.kingborn.erp.fc.service.FlowRecordService;
import net.kingborn.erp.fc.service.ExpenseService;
import net.kingborn.erp.fc.service.AccountRecordService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 支出单删除
 */
@Command
public class CExpenseDelete extends BaseCommand {

    @Autowired
    private ExpenseService ExpenseService;
    @Autowired
    private FlowRecordService recordService;
    @Autowired
    private AccountRecordService accountService;

    @Param(required = true)
    private String expenseId;
    
    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        Expense expense = ExpenseService.getById(expenseId);
        Assert.notNull(expense, "ID为【" + expenseId + "】的支出订单不存在！");

        ExpenseService.removeById(expenseId);

        recordService.deleteByBusiness(expenseId);
        accountService.deleteByBusiness(expenseId);
    }
}
