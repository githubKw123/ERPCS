package net.kingborn.erp.api.bc;

import net.kingborn.erp.api.TestUtil;
import net.kingborn.erp.bc.model.Purchase;
import net.kingborn.erp.fc.model.AccountRecord;
import net.kingborn.erp.wc.model.IssueProduct;
import net.kingborn.test.BaseUrl;
import net.kingborn.test.TestCommand;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 保存购货单
 */
public class TestPurchaseSave extends TestCommand {

    @Override
    public void init() throws Exception {
        headerMap = TestUtil.getAuthHeader();

        Purchase purchase = new Purchase();
        purchase.setCode("PL2021121407235305407");
        purchase.setIssueDate("2020-08-28");
        purchase.setContracts("[]");
        purchase.setSupplierId("1295709005540245506");
        purchase.setAmount(1000.0);
        purchase.setDebtAmount(0.0);
        purchase.setQuantity(11.5);
        purchase.setListerId("1289333755057299456");
        postParamsMap.put("purchase", purchase);

        List<IssueProduct> productList = new ArrayList<>();
        IssueProduct product = new IssueProduct();
        product.setProductId("1298462990858530818");
        product.setWarehouseId("1295254961130692610");
        product.setCode("1234124234");
        product.setQuantity(1.5);
        product.setPrice(110.2);
        product.setDiscountRate(20.0);
        product.setDiscountAmount(113.0);
        product.setAmount(1000.0);
        product.setRemark("很好的产品！");
        productList.add(product);
        postParamsMap.put("productList", productList);

        List<AccountRecord> accountList = new ArrayList<>();
        AccountRecord account = new AccountRecord();
        account.setAccountId("1295128579910197250");
        accountList.add(account);
        postParamsMap.put("accountList", accountList);
    }

    @Test
    @Override
    public void doTest() {
        System.out.println(formatJson(doPost()));
    }

    @Override
    protected BaseUrl getBaseUrl() {
        return new BaseUrl("localhost", 9090, "/purchase/save");
    }

}
