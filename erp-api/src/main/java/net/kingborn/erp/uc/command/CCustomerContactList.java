package net.kingborn.erp.uc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.uc.model.CustomerContact;
import net.kingborn.erp.uc.service.CustomerContactService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 获取客户联系人列表
 */
@Command
public class CCustomerContactList extends BaseCommand {

    @Autowired
    private CustomerContactService contactService;

    @Param(required = true)
    private String customerId; // 客户ID

    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        List<CustomerContact> contactList = contactService.findListByCustomer(customerId);
        data.put("contactList", contactList);
    }
}
