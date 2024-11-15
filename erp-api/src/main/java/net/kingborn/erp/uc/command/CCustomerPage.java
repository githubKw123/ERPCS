package net.kingborn.erp.uc.command;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.rc.model.Category;
import net.kingborn.erp.rc.service.CategoryService;
import net.kingborn.erp.uc.model.Customer;
import net.kingborn.erp.uc.model.CustomerContact;
import net.kingborn.erp.uc.service.CustomerContactService;
import net.kingborn.erp.uc.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 客户分页列表
 */
@Command
public class CCustomerPage extends BaseCommand {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerContactService contactService;
    @Autowired
    private CategoryService categoryService;

    @Param(defaultValue = "{}")
    private JSONObject query; // 查询对象
    @Param(defaultValue = Define.CURRENT)
    private long current; // 页码
    @Param(defaultValue = Define.SIZE)
    private long size; // 每页数量

    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        Page<JSONObject> customerPage = customerService.pageSearch(current, size, query);

        for (JSONObject customer : customerPage.getRecords()) {
            Category category = categoryService.getById(customer.getString("categoryId"));
            if (category != null) {
                customer.put("categoryName", category.getName());
            }

            // 获取联系人列表
            List<CustomerContact> contactList = contactService.findListByCustomer(customer.getString("id"));
            customer.put("contactList", contactList);

            long beginBalance = customer.getLongValue("beginPrepaidAmount") - customer.getLongValue("beginReceivableAmount");
            customer.put("beginBalance", beginBalance);
        }

        data.put("customerPage", customerPage);
    }
}
