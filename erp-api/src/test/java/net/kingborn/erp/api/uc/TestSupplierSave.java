package net.kingborn.erp.api.uc;

import net.kingborn.erp.api.TestUtil;
import net.kingborn.erp.uc.model.Customer;
import net.kingborn.erp.uc.model.CustomerContact;
import net.kingborn.erp.uc.model.Supplier;
import net.kingborn.erp.uc.model.SupplierContact;
import net.kingborn.test.BaseUrl;
import net.kingborn.test.TestCommand;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 保存客户
 */
public class TestSupplierSave extends TestCommand {

    @Override
    public void init() throws Exception {
        headerMap = TestUtil.getAuthHeader();

        Supplier supplier = new Supplier();
        supplier.setCode("wangwu");
        supplier.setName("王五");
        supplier.setCategoryId("1293527068016902146");
        supplier.setVatRate(17);
        supplier.setBalanceTime(new Date());
        supplier.setBeginReceivableAmount(0);
        supplier.setBeginPrepaidAmount(10000);
        supplier.setRemark("很好的供应商");
        postParamsMap.put("supplier", supplier);

        List<SupplierContact> contactList = new ArrayList<>();
        SupplierContact contact = new SupplierContact();
        contact.setName("张三");
        contact.setMobile("1888888888");
        contact.setPrimary(true);
        contactList.add(contact);

        contact = new SupplierContact();
        contact.setName("李四");
        contact.setMobile("16666666666");
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
        return new BaseUrl("localhost", 9090, "/supplier/save");
    }

}
