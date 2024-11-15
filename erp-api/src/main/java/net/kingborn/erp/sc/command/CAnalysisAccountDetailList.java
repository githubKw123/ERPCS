package net.kingborn.erp.sc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.bc.model.Purchase;
import net.kingborn.erp.bc.model.Sale;
import net.kingborn.erp.bc.service.PurchaseService;
import net.kingborn.erp.bc.service.SaleService;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.fc.model.*;
import net.kingborn.erp.fc.service.*;
import net.kingborn.erp.uc.model.Customer;
import net.kingborn.erp.uc.model.SettlementAccount;
import net.kingborn.erp.uc.model.Supplier;
import net.kingborn.erp.uc.service.CustomerService;
import net.kingborn.erp.uc.service.SettlementAccountService;
import net.kingborn.erp.uc.service.SupplierService;
import net.kingborn.erp.util.SimpleValidator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 现金银行报表
 */
@Command
public class CAnalysisAccountDetailList extends BaseCommand {

    @Autowired
    private AccountRecordService recordService;
    @Autowired
    private SettlementAccountService accountService;

    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private SaleService saleService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CollectionService collectionService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private IncomeService incomeService;
    @Autowired
    private ExpenseService expenseService;

    /** 开始时间 */
    private @Param
    String startDate;
    /** 结束时间 */
    private @Param String endDate;
    /** 账户ID列表 */
    private @Param List<String> accountIdList;

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
        List<AccountRecord> recordList = recordService.analysisList(startDate, endDate, accountIdList);
        for (AccountRecord record : recordList) {
            SettlementAccount account = accountService.getById(record.getAccountId());
            Assert.notNull(account, "ID为【" + record.getAccountId() + "】的结算账户不存在！");

            record.put("accountCode", account.getCode());
            record.put("accountName", account.getName());
            if (Define.ACCOUNT_RECORD_TYPE_IN == record.getType()) {
                record.put("incomeAmount", record.getAmount());
                record.put("expenseAmount", 0);
            } else {
                record.put("incomeAmount", 0);
                record.put("expenseAmount", record.getAmount());
            }

            composeIssueInfo(record);
        }

        data.put("recordList", recordList);
    }

    /**
     * 组装单据信息
     *
     * @param record
     */
    private void composeIssueInfo(AccountRecord record) {
        if (Define.BUSINESS_TYPE_PURCHASE_BUY.equals(record.getBusinessType())) {
            record.put("businessTypeName", "普通采购");
            Purchase purchase = purchaseService.getById(record.getBusinessId());
            record.put("issueCode", purchase.getCode());
            record.put("relatedUnit", getSupplierName(purchase.getSupplierId()));

        } else if (Define.BUSINESS_TYPE_PURCHASE_REFUND.equals(record.getBusinessType())) {
            record.put("businessTypeName", "采购退回");
            Purchase purchase = purchaseService.getById(record.getBusinessId());
            record.put("issueCode", purchase.getCode());
            record.put("relatedUnit", getSupplierName(purchase.getSupplierId()));

        } else if (Define.BUSINESS_TYPE_SALE_SELL.equals(record.getBusinessType())) {
            record.put("businessTypeName", "普通销售");
            Sale sale = saleService.getById(record.getBusinessId());
            record.put("issueCode", sale.getCode());
            record.put("relatedUnit", getCustomerName(sale.getCustomerId()));

        } else if (Define.BUSINESS_TYPE_SALE_RETURNED.equals(record.getBusinessType())) {
            record.put("businessTypeName", "销售退回");
            Sale sale = saleService.getById(record.getBusinessId());
            record.put("issueCode", sale.getCode());
            record.put("relatedUnit", getCustomerName(sale.getCustomerId()));

        } else if (Define.BUSINESS_TYPE_COLLECTION.equals(record.getBusinessType())) {
            Collection collection = collectionService.getById(record.getBusinessId());
            record.put("businessTypeName", "收款");
            record.put("issueCode", collection.getCode());
            record.put("relatedUnit", getCustomerName(collection.getCustomerId()));

        } else if (Define.BUSINESS_TYPE_PAYMENT.equals(record.getBusinessType())) {
            Payment payment = paymentService.getById(record.getBusinessId());
            record.put("businessTypeName", "付款");
            record.put("issueCode", payment.getCode());
            record.put("relatedUnit", getSupplierName(payment.getSupplierId()));

        } else if (Define.BUSINESS_TYPE_INCOME.equals(record.getBusinessType())) {
            Income income = incomeService.getById(record.getBusinessId());
            record.put("businessTypeName", "其他收入");
            record.put("issueCode", income.getCode());
            record.put("relatedUnit", getCustomerName(income.getCustomerId()));

        } else if (Define.BUSINESS_TYPE_EXPENSE.equals(record.getBusinessType())) {
            Expense expense = expenseService.getById(record.getBusinessId());
            record.put("businessTypeName", "其他支出");
            record.put("issueCode", expense.getCode());
            record.put("relatedUnit", getSupplierName(expense.getSupplierId()));

        }
    }

    /**
     * 获取供应商名称
     *
     * @param supplierId
     * @return
     */
    private String getSupplierName(String supplierId) {
        if (StrKit.isBlank(supplierId)) {
            return "";
        }
        Supplier supplier = supplierService.getById(supplierId);

        return supplier.getName();
    }

    /**
     * 获取客户名称
     *
     * @param customerId
     * @return
     */
    private String getCustomerName(String customerId) {
        if (StrKit.isBlank(customerId)) {
            return "";
        }
        Customer customer = customerService.getById(customerId);

        return customer.getName();
    }
}
