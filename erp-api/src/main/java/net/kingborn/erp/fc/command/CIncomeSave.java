package net.kingborn.erp.fc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.core.exception.BizException;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.fc.model.Income;
import net.kingborn.erp.fc.model.AccountRecord;
import net.kingborn.erp.fc.model.FlowRecord;
import net.kingborn.erp.fc.service.AccountRecordService;
import net.kingborn.erp.fc.service.FlowRecordService;
import net.kingborn.erp.fc.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 收入单保存
 */
@Command
public class CIncomeSave extends BaseCommand {

    @Autowired
    private IncomeService incomeService;
    @Autowired
    private AccountRecordService accountService;
    @Autowired
    private FlowRecordService recordService;

    @Param(required = true)
    private Income income;
    @Param(defaultValue = "[]")
    private List<FlowRecord> recordList;
    @Param(defaultValue = "[]")
    private List<AccountRecord> accountList;

    private Income persistedIncome;
    
    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        // 计算
        if (StrKit.isBlank(income.getId())) {
            persistedIncome = new Income();

            // 校验编码是否合法
            validateCode(income.getCode());
            persistedIncome.setCode(income.getCode());

        } else {
            persistedIncome = incomeService.getById(income.getId());
            Assert.notNull(persistedIncome, "ID为【" + income.getId() + "】的收入订单不存在！");

            // 删除原来的账户
            accountService.deleteByBusiness(income.getId());

            // 删除关联的单据
            recordService.deleteByBusiness(income.getId());
        }

        persistedIncome.setCustomerId(income.getCustomerId());
        persistedIncome.setIssueDate(income.getIssueDate());
        persistedIncome.setCollectAmount(income.getCollectAmount());
        persistedIncome.setListerId(income.getListerId());
        persistedIncome.setRemark(income.getRemark());
        incomeService.saveOrUpdate(persistedIncome);

        // 新增账户
        for (AccountRecord record : accountList) {
            record.setAmount(persistedIncome.getCollectAmount());
        }
        accountService.addRecordList(accountList, Define.ACCOUNT_RECORD_TYPE_IN, persistedIncome.getIssueDate(), Define.BUSINESS_TYPE_INCOME, persistedIncome.getId());

        // 新增单据
        addRecordList();

        data.put("income", persistedIncome);
    }

    /**
     * 校验单据编号是否合法
     *
     * @param code
     */
    private void validateCode(String code) {
        Income Income = incomeService.findByCode(code);
        if (Income != null) {
            throw new BizException("单据编号为【" + code + "】的收入单已经存在！");
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
            persistedRecord.setIssueDate(persistedIncome.getIssueDate());
            persistedRecord.setBusinessType(Define.BUSINESS_TYPE_INCOME);
            persistedRecord.setBusinessId(persistedIncome.getId());
            persistedRecord.setCategoryId(record.getCategoryId());
            persistedRecord.setAmount(record.getAmount());
            persistedRecord.setRemark(record.getRemark());

            persistedRecordList.add(persistedRecord);
        }

        recordService.saveBatch(persistedRecordList);
    }
}
