package net.kingborn.erp.uc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.erp.uc.model.SettlementAccount;
import net.kingborn.erp.uc.service.SettlementAccountService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 账户详情
 */
@Command
public class CSettlementAccountDetail extends BaseCommand {

    @Autowired
    private SettlementAccountService accountService;

    @Param(required = true)
    private String accountId;

    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        SettlementAccount account = accountService.getById(accountId);
        Assert.notNull(account, "ID为【" + accountId + "】的账户不存在！");

        data.put("account", account);
    }
}
