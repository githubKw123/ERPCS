package net.kingborn.erp.uc.command;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.rc.model.Category;
import net.kingborn.erp.rc.service.CategoryService;
import net.kingborn.erp.uc.model.CustomerContact;
import net.kingborn.erp.uc.service.CustomerContactService;
import net.kingborn.erp.uc.service.CustomerService;
import net.kingborn.erp.uc.service.SupplierContactService;
import net.kingborn.erp.uc.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 供应商分页列表
 */
@Command
public class CSupplierPage extends BaseCommand {

    @Autowired
    private SupplierService supplierService;
    @Autowired
    private SupplierContactService contactService;
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
        Page<JSONObject> supplierPage = supplierService.pageSearch(current, size, query);

        for (JSONObject supplier : supplierPage.getRecords()) {
            Category category = categoryService.getById(supplier.getString("categoryId"));
            if (category != null) {
                supplier.put("categoryName", category.getName());
            }

            long beginBalance = supplier.getLongValue("beginPrepaidAmount") - supplier.getLongValue("beginReceivableAmount");
            supplier.put("beginBalance", beginBalance);
        }

        data.put("supplierPage", supplierPage);
    }
}
