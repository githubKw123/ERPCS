package net.kingborn.erp.uc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.erp.uc.service.SettlementAccountService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 账户删除
 */
@Command
public class CSettlementAccountDelete extends BaseCommand {

    @Autowired
    private SettlementAccountService accountService;

    @Param(required = true)
    private String accountId;

    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        accountService.removeById(accountId);
    }
}
