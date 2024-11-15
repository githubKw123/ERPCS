package net.kingborn.erp.uc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.erp.uc.model.Customer;
import net.kingborn.erp.uc.model.CustomerContact;
import net.kingborn.erp.uc.model.Supplier;
import net.kingborn.erp.uc.model.SupplierContact;
import net.kingborn.erp.uc.service.CustomerContactService;
import net.kingborn.erp.uc.service.CustomerService;
import net.kingborn.erp.uc.service.SupplierContactService;
import net.kingborn.erp.uc.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 供应商详情
 */
@Command
public class CSupplierDetail extends BaseCommand {

    @Autowired
    private SupplierService supplierService;
    @Autowired
    private SupplierContactService contactService;

    @Param(required = true)
    private String supplierId;

    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        Supplier supplier = supplierService.getById(supplierId);
        Assert.notNull(supplier, "ID为【" + supplierId + "】的供应商不存在！");

        List<SupplierContact> contactList = contactService.findListBySupplier(supplierId);
        supplier.put("contactList", contactList);

        data.put("supplier", supplier);
    }
}
