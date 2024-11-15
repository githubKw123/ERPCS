package net.kingborn.erp.fc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.erp.fc.model.Income;
import net.kingborn.erp.fc.service.FlowRecordService;
import net.kingborn.erp.fc.service.IncomeService;
import net.kingborn.erp.fc.service.AccountRecordService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 收入单删除
 */
@Command
public class CIncomeDelete extends BaseCommand {

    @Autowired
    private IncomeService incomeService;
    @Autowired
    private FlowRecordService issueService;
    @Autowired
    private AccountRecordService accountService;

    @Param(required = true)
    private String incomeId;
    
    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        Income income = incomeService.getById(incomeId);
        Assert.notNull(income, "ID为【" + incomeId + "】的收入订单不存在！");

        incomeService.removeById(incomeId);

        issueService.deleteByBusiness(incomeId);
        accountService.deleteByBusiness(incomeId);
    }
}
