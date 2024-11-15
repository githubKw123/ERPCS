package net.kingborn.erp.uc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.core.exception.BizException;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.rc.service.DictItemService;
import net.kingborn.erp.uc.model.SettlementAccount;
import net.kingborn.erp.uc.service.SettlementAccountService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 账户保存
 */
@Command
public class CSettlementAccountSave extends BaseCommand {

    @Autowired
    private SettlementAccountService accountService;
    @Autowired
    private DictItemService itemService;

    @Param(required = true)
    private SettlementAccount account;

    @Override
    protected void init() throws Exception {
        Assert.notBlank(account.getCode(), "账户编号不能为空！");
        Assert.notBlank(account.getName(), "账户名称不能为空！");
        Assert.notNull(account.getBalanceTime(), "余额日期不能为空！");
        Assert.notBlank(account.getType(), "账户类别不能为空！");
    }

    @Override
    protected void doCommand() throws Exception {
        if (!itemService.validate(account.getType(), Define.DICT_CODE_ACCOUNT_TYPE)) {
            throw new BizException("账户类型不正确！");
        }

        SettlementAccount persistedAccount;
        if (StrKit.notBlank(account.getId())) {
            persistedAccount = accountService.getById(account.getId());
            Assert.notNull(persistedAccount, "ID为【" + account.getId() + "】的账户不存在！");

        } else {
            persistedAccount = new SettlementAccount();
        }
        persistedAccount.setCode(account.getCode());
        persistedAccount.setName(account.getName());
        persistedAccount.setBalanceTime(account.getBalanceTime());
        persistedAccount.setBeginBalance(account.getBeginBalance());
        persistedAccount.setCurrentBalance(getCurrentBalance());
        persistedAccount.setType(account.getType());

        accountService.saveOrUpdate(persistedAccount);

        data.put("account", persistedAccount);
    }

    /**
     * 计算当前余额
     *
     * @return
     */
    private double getCurrentBalance() {
        return account.getBeginBalance();
    }
}
