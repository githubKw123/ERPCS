package net.kingborn.erp.fc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.erp.fc.model.Expense;
import net.kingborn.erp.fc.model.FlowRecord;
import net.kingborn.erp.fc.model.AccountRecord;
import net.kingborn.erp.fc.service.FlowRecordService;
import net.kingborn.erp.fc.service.ExpenseService;
import net.kingborn.erp.fc.service.AccountRecordService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 支出单详情
 */
@Command
public class CExpenseDetail extends BaseCommand {

    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private AccountRecordService accountService;
    @Autowired
    private FlowRecordService recordService;

    @Param(required = true)
    private String expenseId; // 支出订单ID
    
    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        Expense expense = expenseService.getById(expenseId);
        Assert.notNull(expense, "ID为【" + expenseId + "】的支出订单不存在！");

        List<AccountRecord> accountList = accountService.findListByBusiness(expense.getId());
        expense.put("accountList", accountList);

        List<FlowRecord> recordList = recordService.findListByBusiness(expense.getId());
        expense.put("recordList", recordList);

        data.put("expense", expense);
    }
}
