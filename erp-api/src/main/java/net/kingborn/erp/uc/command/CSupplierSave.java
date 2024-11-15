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
import net.kingborn.erp.uc.model.Supplier;
import net.kingborn.erp.uc.model.SupplierContact;
import net.kingborn.erp.uc.service.CustomerContactService;
import net.kingborn.erp.uc.service.CustomerService;
import net.kingborn.erp.uc.service.SupplierContactService;
import net.kingborn.erp.uc.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 保存客户
 */
@Command
public class CSupplierSave extends BaseCommand {

    @Autowired
    private SupplierService supplierService;
    @Autowired
    private SupplierContactService contactService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DictItemService dictItemService;

    @Param(required = true)
    private Supplier supplier;
    @Param(defaultValue = "[]")
    private List<SupplierContact> contactList;

    @Override
    protected void init() throws Exception {
        Assert.notBlank(supplier.getCode(), "供应商编号不能为空！");
        Assert.notBlank(supplier.getName(), "供应商名称不能为空！");
        Assert.notBlank(supplier.getCategoryId(), "供应商类别不能为空！");
    }

    @Transactional
    @Override
    protected void doCommand() throws Exception {
        Category category = categoryService.getById(supplier.getCategoryId());
        if (category == null || category.getType() != Define.CATEGORY_TYPE_SUPPLIER) {
            throw new BizException("供应商类别不正确！");
        }

        checkDuplicatedCode();

        Supplier persistedSupplier;
        if (StrKit.isBlank(supplier.getId())) { // 新增
            persistedSupplier = new Supplier();
        } else {
            persistedSupplier = supplierService.getById(supplier.getId());
            Assert.notNull(persistedSupplier, "ID为【" + supplier.getId() + "】的供应商不存在！");

            // 删除原来的联系人
            contactService.deleteBySupplier(supplier.getId());
        }

        persistedSupplier.setCode(supplier.getCode());
        persistedSupplier.setName(supplier.getName());
        persistedSupplier.setCategoryId(supplier.getCategoryId());
        persistedSupplier.setBalanceTime(supplier.getBalanceTime());
        persistedSupplier.setBeginPrepaidAmount(supplier.getBeginPrepaidAmount());
        persistedSupplier.setBeginReceivableAmount(supplier.getBeginReceivableAmount());
        persistedSupplier.setVatRate(supplier.getVatRate());
        persistedSupplier.setRemark(supplier.getRemark());
        persistedSupplier.setActive(true);
        supplierService.saveOrUpdate(persistedSupplier);

        // 校验首要联系人
        boolean hasPrimary = false;
        for (SupplierContact contact : contactList) {
            // 设置客户ID
            contact.setSupplierId(persistedSupplier.getId());

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

        data.put("supplier", persistedSupplier);
    }

    private void checkDuplicatedCode() {
        Supplier supplierByCode = supplierService.findByCode(supplier.getCode());
        if (supplierByCode == null) return;

        if (StrKit.notBlank(supplier.getId()) && supplier.getId().equals(supplierByCode.getId())) return;

        throw new BizException("编码为【" + supplier.getCode() + "】的供应商已经存在！");
    }

}
