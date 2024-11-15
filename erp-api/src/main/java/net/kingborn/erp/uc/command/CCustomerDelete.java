package net.kingborn.erp.uc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.erp.uc.model.Customer;
import net.kingborn.erp.uc.service.CustomerContactService;
import net.kingborn.erp.uc.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 客户删除
 */
@Command
@Transactional
public class CCustomerDelete extends BaseCommand {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerContactService contactService;

    @Param(required = true)
    private String customerId;

    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        Customer customer = customerService.getById(customerId);
        Assert.notNull(customer, "ID为【" + customerId + "】的客户不存在！");

        customerService.removeById(customerId);

        contactService.deleteByCustomer(customerId);
    }
}
