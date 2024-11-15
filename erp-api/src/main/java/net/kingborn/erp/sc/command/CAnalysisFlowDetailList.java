package net.kingborn.erp.sc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.fc.model.*;
import net.kingborn.erp.fc.service.*;
import net.kingborn.erp.rc.model.Category;
import net.kingborn.erp.rc.service.CategoryService;
import net.kingborn.erp.uc.model.Customer;
import net.kingborn.erp.uc.model.Supplier;
import net.kingborn.erp.uc.service.CustomerService;
import net.kingborn.erp.uc.service.SupplierService;
import net.kingborn.erp.util.SimpleValidator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 其他收支明细表
 */
@Command
public class CAnalysisFlowDetailList extends BaseCommand {

    @Autowired
    private FlowRecordService recordService;
    @Autowired
    private IncomeService incomeService;
    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private SupplierService supplierService;

    /** 开始时间 */
    private @Param
    String startDate;
    /** 结束时间 */
    private @Param String endDate;
    /** 类别ID */
    private @Param
    String categoryId;

    @Override
    protected void init() throws Exception {
        if (StrKit.notBlank(startDate)) {
            Assert.notFalse(SimpleValidator.validateDate(startDate), "起始时间不正确！");
        }
        if (StrKit.notBlank(endDate)) {
            Assert.notFalse(SimpleValidator.validateDate(endDate), "结束时间不正确！");
        }
    }

    @Override
    protected void doCommand() throws Exception {
        List<FlowRecord> recordList = recordService.analysisList(startDate, endDate, categoryId);
        for (FlowRecord record : recordList) {
            if (record.getBusinessType().equals(Define.BUSINESS_TYPE_INCOME)) {
                Income income = incomeService.getById(record.getBusinessId());
                Assert.notNull(income, "ID为【" + record.getBusinessId() + "】的收入单不存在！");

                record.put("issueCode", income.getCode());
                record.put("businessTypeName", "其他收入");
                record.put("incomeAmount", income.getAmount());

                Customer customer = customerService.getById(income.getCustomerId());
                record.put("relatedUnit", customer.getName());

            } else if (record.getBusinessType().equals(Define.BUSINESS_TYPE_EXPENSE)) {
                Expense expense = expenseService.getById(record.getBusinessId());
                Assert.notNull(expense, "ID为【" + record.getBusinessId() + "】的支出单不存在！");

                record.put("issueCode", expense.getCode());
                record.put("businessTypeName", "其他支出");
                record.put("expenseAmount", expense.getAmount());

                Supplier supplier = supplierService.getById(expense.getSupplierId());
                record.put("relatedUnit", supplier.getName());
            }

            Category category = categoryService.getById(record.getCategoryId());
            record.put("categoryName", category.getName());
        }

        data.put("recordList", recordList);
    }
}
