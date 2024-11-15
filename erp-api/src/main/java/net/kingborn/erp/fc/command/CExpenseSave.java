package net.kingborn.erp.fc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.core.exception.BizException;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.fc.model.Expense;
import net.kingborn.erp.fc.model.AccountRecord;
import net.kingborn.erp.fc.model.FlowRecord;
import net.kingborn.erp.fc.service.ExpenseService;
import net.kingborn.erp.fc.service.AccountRecordService;
import net.kingborn.erp.fc.service.FlowRecordService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 支出单保存
 */
@Command
public class CExpenseSave extends BaseCommand {

    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private AccountRecordService accountService;
    @Autowired
    private FlowRecordService recordService;

    @Param(required = true)
    private Expense expense;
    @Param(defaultValue = "[]")
    private List<FlowRecord> recordList;
    @Param(defaultValue = "[]")
    private List<AccountRecord> accountList;

    private Expense persistedExpense;
    
    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        // 计算
        if (StrKit.isBlank(expense.getId())) {
            persistedExpense = new Expense();

            // 校验编码是否合法
            validateCode(expense.getCode());
            persistedExpense.setCode(expense.getCode());

        } else {
            persistedExpense = expenseService.getById(expense.getId());
            Assert.notNull(persistedExpense, "ID为【" + expense.getId() + "】的支出订单不存在！");

            // 删除原来的账户
            accountService.deleteByBusiness(expense.getId());

            // 删除关联的单据
            recordService.deleteByBusiness(expense.getId());
        }

        persistedExpense.setSupplierId(expense.getSupplierId());
        persistedExpense.setIssueDate(expense.getIssueDate());
        persistedExpense.setPaidAmount(expense.getPaidAmount());
        persistedExpense.setListerId(expense.getListerId());
        persistedExpense.setRemark(expense.getRemark());
        expenseService.saveOrUpdate(persistedExpense);

        // 新增账户
        for (AccountRecord record : accountList) {
            record.setAmount(persistedExpense.getPaidAmount());
        }
        accountService.addRecordList(accountList, Define.ACCOUNT_RECORD_TYPE_OUT, persistedExpense.getIssueDate(), Define.BUSINESS_TYPE_EXPENSE, persistedExpense.getId());

        // 新增单据
        addRecordList();

        data.put("expense", persistedExpense);
    }

    /**
     * 校验单据编号是否合法
     *
     * @param code
     */
    private void validateCode(String code) {
        Expense Expense = expenseService.findByCode(code);
        if (Expense != null) {
            throw new BizException("单据编号为【" + code + "】的支出单已经存在！");
        }
    }

    /**
     * 新增记录列表
     */
    private void addRecordList() {
        if (recordList == null || recordList.size() == 0) return;

        List<FlowRecord> persistedRecordList = new ArrayList<>();
        FlowRecord persistedRecord;
        for (FlowRecord record : recordList) {
            persistedRecord = new FlowRecord();
            persistedRecord.setIssueDate(persistedExpense.getIssueDate());
            persistedRecord.setBusinessType(Define.BUSINESS_TYPE_EXPENSE);
            persistedRecord.setBusinessId(persistedExpense.getId());
            persistedRecord.setCategoryId(record.getCategoryId());
            persistedRecord.setAmount(record.getAmount());
            persistedRecord.setRemark(record.getRemark());

            persistedRecordList.add(persistedRecord);
        }

        recordService.saveBatch(persistedRecordList);
    }
}
