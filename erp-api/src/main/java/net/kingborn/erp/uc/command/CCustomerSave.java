package net.kingborn.erp.uc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.core.exception.BizException;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.rc.model.Category;
import net.kingborn.erp.rc.service.CategoryService;
import net.kingborn.erp.rc.service.DictItemService;
import net.kingborn.erp.uc.model.Customer;
import net.kingborn.erp.uc.model.CustomerContact;
import net.kingborn.erp.uc.service.CustomerContactService;
import net.kingborn.erp.uc.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 保存客户
 */
@Command
public class CCustomerSave extends BaseCommand {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerContactService contactService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DictItemService dictItemService;

    @Param(required = true)
    private Customer customer;
    @Param(defaultValue = "[]")
    private List<CustomerContact> contactList;

    @Override
    protected void init() throws Exception {
        Assert.notBlank(customer.getCode(), "客户编号不能为空！");
        Assert.notBlank(customer.getName(), "客户名称不能为空！");
        Assert.notBlank(customer.getCategoryId(), "客户类别不能为空！");
    }

    @Transactional
    @Override
    protected void doCommand() throws Exception {
        Category category = categoryService.getById(customer.getCategoryId());
        if (category == null || category.getType() != Define.CATEGORY_TYPE_CUSTOMER) {
            throw new BizException("客户类别不正确！");
        }

        checkDuplicatedCode();


//        if (!dictItemService.validate(customer.getLevel(), Define.DICT_CODE_CUSTOMER_LEVEL)) {
//            throw new BizException("客户等级不正确！");
//        }

        Customer persistedCustomer;
        if (StrKit.isBlank(customer.getId())) { // 新增
            persistedCustomer = new Customer();
        } else {
            persistedCustomer = customerService.getById(customer.getId());
            Assert.notNull(persistedCustomer, "ID为【" + customer.getId() + "】的客户不存在！");

            // 删除原来的联系人
            contactService.deleteByCustomer(customer.getId());
        }

        persistedCustomer.setCode(customer.getCode());
        persistedCustomer.setName(customer.getName());
        persistedCustomer.setCategoryId(customer.getCategoryId());
        persistedCustomer.setLevel(customer.getLevel());
        persistedCustomer.setBalanceTime(customer.getBalanceTime());
        persistedCustomer.setBeginPrepaidAmount(customer.getBeginPrepaidAmount());
        persistedCustomer.setBeginReceivableAmount(customer.getBeginReceivableAmount());
        persistedCustomer.setActive(true);
        persistedCustomer.setRemark(customer.getRemark());
        customerService.saveOrUpdate(persistedCustomer);

        // 校验首要联系人
        boolean hasPrimary = false;
        for (CustomerContact contact : contactList) {
            // 设置客户ID
            contact.setCustomerId(persistedCustomer.getId());

            // 处理首要联系人
            if (!hasPrimary && contact.isPrimary()) {
                hasPrimary = true;
                continue;
            }
            if (hasPrimary) {
                contact.setPrimary(false);
            }
        }
        if (!hasPrimary && contactList.size() > 0) { // 没有设置首要联系人，将第一个作为首要联系人
            contactList.get(0).setPrimary(true);
        }

        // 批量保存客户联系人
        contactService.saveBatch(contactList);

        data.put("customer", persistedCustomer);
    }

    /**
     * 检测编码是否重复
     */
    private void checkDuplicatedCode() {
        Customer customerByCode = customerService.findByCode(customer.getCode());
        if (customerByCode == null) return;

        if (StrKit.notBlank(customer.getId()) && customer.getId().equals(customerByCode.getId())) return;

        throw new BizException("编码为【" + customer.getCode() + "】的客户已经存在！");
    }
}
