package net.kingborn.erp.fc.command;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.fc.model.Expense;
import net.kingborn.erp.fc.service.ExpenseService;
import net.kingborn.erp.uc.model.Customer;
import net.kingborn.erp.uc.model.Supplier;
import net.kingborn.erp.uc.model.User;
import net.kingborn.erp.uc.service.CustomerService;
import net.kingborn.erp.uc.service.SupplierService;
import net.kingborn.erp.uc.service.UserService;
import net.kingborn.erp.util.SimpleValidator;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 支出单分页列表
 */
@Command
public class CExpensePage extends BaseCommand {

    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private UserService userService;
    @Autowired
    private SupplierService supplierService;

    @Param(defaultValue = "{}")
    private JSONObject query; // 查询对象
    @Param(defaultValue = Define.CURRENT)
    private long current;
    @Param(defaultValue = Define.SIZE)
    private long size;

    @Override
    protected void init() throws Exception {
        String startDate = query.getString("startDate");
        if (StrKit.notBlank(startDate)) {
            Assert.notFalse(SimpleValidator.validateDate(startDate), "起始时间不正确！");
        }

        String endDate = query.getString("endDate");
        if (StrKit.notBlank(endDate)) {
            Assert.notFalse(SimpleValidator.validateDate(endDate), "结束时间不正确！");
        }
    }

    @Override
    protected void doCommand() throws Exception {
        Page<Expense> expensePage = expenseService.pageSearch(current, size, query);

        Supplier supplier;
        User lister;
        for (Expense expense : expensePage.getRecords()) {
            supplier = supplierService.getById(expense.getSupplierId());
            expense.put("supplierName", supplier.getName());

            if (StrKit.notBlank(expense.getListerId())) {
                lister = userService.getById(expense.getListerId());
                expense.put("listerName", lister.getName());
            }
        }

        data.put("expensePage", expensePage);
    }
}
