package net.kingborn.erp.uc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.erp.rc.model.DictItem;
import net.kingborn.erp.rc.service.CategoryService;
import net.kingborn.erp.rc.service.DictItemService;
import net.kingborn.erp.uc.model.SettlementAccount;
import net.kingborn.erp.uc.service.SettlementAccountService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 账户列表
 */
@Command
public class CSettlementAccountList extends BaseCommand {

    @Autowired
    private SettlementAccountService accountService;
    @Autowired
    private DictItemService itemService;

    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        List<SettlementAccount> accountList = accountService.list();

        for (SettlementAccount account : accountList) {
            DictItem item = itemService.getById(account.getType());
            account.put("typeName", item.getName());
        }

        data.put("accountList", accountList);
    }
}
