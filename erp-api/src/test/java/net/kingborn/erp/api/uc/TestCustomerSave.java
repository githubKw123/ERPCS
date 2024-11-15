package net.kingborn.erp.api.uc;

import com.alibaba.fastjson.JSONArray;
import net.kingborn.erp.api.TestUtil;
import net.kingborn.erp.uc.model.Customer;
import net.kingborn.erp.uc.model.CustomerContact;
import net.kingborn.test.BaseUrl;
import net.kingborn.test.TestCommand;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 保存客户
 */
public class TestCustomerSave extends TestCommand {

    @Override
    public void init() throws Exception {
        headerMap = TestUtil.getAuthHeader();

        Customer customer = new Customer();
        customer.setCode("lisi");
        customer.setName("李四");
        customer.setCategoryId("1293513956037320706");
        customer.setLevel("1294753269708566530");
        customer.setBalanceTime(new Date());
        customer.setBeginReceivableAmount(0);
        customer.setBeginPrepaidAmount(10000);
        postParamsMap.put("customer", customer);

        List<CustomerContact> contactList = new ArrayList<>();
        CustomerContact contact = new CustomerContact();
        contact.setName("张三");
        contact.setMobile("1888888888");
        contact.setPosition("总经理");
        contact.setPrimary(true);
        contactList.add(contact);

        contact = new CustomerContact();
        contact.setName("李四");
        contact.setMobile("16666666666");
        contact.setPosition("董事长");
        contact.setPrimary(false);
        contactList.add(contact);
        postParamsMap.put("contactList", contactList);
    }

    @Test
    @Override
    public void doTest() {
        System.out.println(formatJson(doPost()));
    }

    @Override
    protected BaseUrl getBaseUrl() {
        return new BaseUrl("localhost", 9090, "/customer/save");
    }

}
