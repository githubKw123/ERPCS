package net.kingborn.erp.api.uc;

import net.kingborn.erp.api.TestUtil;
import net.kingborn.erp.uc.model.SettlementAccount;
import net.kingborn.test.BaseUrl;
import net.kingborn.test.TestCommand;
import org.junit.Test;

import java.util.Date;

/**
 * 结算账户保存
 */
public class TestSettlementAccountSave extends TestCommand {

    @Override
    public void init() throws Exception {
        headerMap = TestUtil.getAuthHeader();

        SettlementAccount account = new SettlementAccount();
        account.setCode("ICBC02");
        account.setName("工商银行2号账户");
        account.setBalanceTime(new Date());
        account.setBeginBalance(10000000);
        account.setType("1294765881575636993");

        postParamsMap.put("account", account);
    }

    @Test
    @Override
    public void doTest() {
        System.out.println(formatJson(doPost()));
    }

    @Override
    protected BaseUrl getBaseUrl() {
        return new BaseUrl("localhost", 9090, "/settlementAccount/save");
    }
}
