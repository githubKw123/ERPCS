package net.kingborn.erp.api.bc;

import net.kingborn.erp.api.TestUtil;
import net.kingborn.erp.bc.model.Sale;
import net.kingborn.erp.wc.model.IssueProduct;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.fc.model.AccountRecord;
import net.kingborn.test.BaseUrl;
import net.kingborn.test.TestCommand;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 保存销货单
 */
public class TestSaleSave extends TestCommand {

    @Override
    public void init() throws Exception {
        headerMap = TestUtil.getAuthHeader();

        Sale sale = new Sale();
        sale.setType(Define.BUSINESS_TYPE_SALE_SELL);
        sale.setCode("134123413241234");
        sale.setIssueDate("2020-08-28");
        sale.setSellerId("123413414");
        sale.setCustomerId("1295709005540245506");
        sale.setAddress("测试地址");
        sale.setPhone("13412343124");
        sale.setDiscountAmount(100.0);
        sale.setPreferentialAmount(800.0);
        sale.setAmount(800.0);
        sale.setCustomerFee(100.0);
        sale.setDebtAmount(0.0);
        sale.setQuantity(11.5);
        sale.setPreferentialRate(20.0);
        sale.setListerId("1289333755057299456");
        postParamsMap.put("sell", sale);

        List<IssueProduct> productList = new ArrayList<>();
        IssueProduct product = new IssueProduct();
        product.setProductId("1298462990858530818");
        product.setWarehouseId("1295254961130692610");
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
        account.setAmount(1000.0);
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
        return new BaseUrl("localhost", 9090, "/sale/save");
    }

}
