package net.kingborn.erp.fc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.kingborn.core.exception.Assert;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.fc.dao.AccountRecordDao;
import net.kingborn.erp.fc.model.AccountRecord;
import net.kingborn.erp.fc.service.AccountRecordService;
import net.kingborn.erp.uc.model.SettlementAccount;
import net.kingborn.erp.uc.service.SettlementAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountRecordServiceImpl extends ServiceImpl<AccountRecordDao, AccountRecord> implements AccountRecordService {

    @Autowired
    private AccountRecordDao recordDao;

    @Autowired
    private SettlementAccountService accountService;

    @Override
    public List<AccountRecord> findListByBusiness(String businessId) {
        return this.list(new QueryWrapper<AccountRecord>().eq("businessId", businessId));
    }

    @Override
    public void deleteByBusiness(String businessId) {
        this.remove(new QueryWrapper<AccountRecord>().eq("businessId", businessId));
    }

    @Override
    public void addRecordList(List<AccountRecord> recordList, String type, String issueDate, String businessType, String businessId) {
        if (recordList == null || recordList.size() == 0) return;

        List<AccountRecord> persistedPurchaseAccountList = new ArrayList<>();
        AccountRecord persistedRecord;
        for (AccountRecord record : recordList) {
            Assert.notBlank(record.getAccountId(), "结算账户的ID不能为空！");
            SettlementAccount account = accountService.getById(record.getAccountId());
            Assert.notNull(account, "ID为【" + record.getAccountId() + "】的结算账户不存在！");

            persistedRecord = new AccountRecord();
            persistedRecord.setType(type);
            persistedRecord.setIssueDate(issueDate);
            persistedRecord.setBusinessType(businessType);

            persistedRecord.setBusinessId(businessId);
            persistedRecord.setAccountId(record.getAccountId());

            // TODO 校验金额
            persistedRecord.setAmount(record.getAmount());

            persistedRecord.setSettlementCode(record.getSettlementCode());
            persistedRecord.setSettlementType(record.getSettlementType());
            persistedRecord.setRemark(record.getRemark());

            if (Define.ACCOUNT_RECORD_TYPE_IN == type) {
                account.setCurrentBalance(account.getCurrentBalance() + record.getAmount());
            } else {
                account.setCurrentBalance(account.getCurrentBalance() - record.getAmount());
            }

            accountService.updateById(account);

            persistedRecord.setCurrentAmount(account.getCurrentBalance());

            persistedPurchaseAccountList.add(persistedRecord);
        }

        saveBatch(persistedPurchaseAccountList);
    }

    @Override
    public List<AccountRecord> analysisList(String startDate, String endDate, List<String> accountIdList) {
        QueryWrapper<AccountRecord> wrapper = new QueryWrapper<>();
        if (StrKit.notBlank(startDate)) {
            wrapper.ge("issueDate", startDate);
        }
        if (StrKit.notBlank(endDate)) {
            wrapper.le("issueDate", endDate);
        }
        if (accountIdList != null && accountIdList.size() > 0) {
            wrapper.in("accountId", accountIdList);
        }
        return this.list(wrapper.orderByAsc("accountId", "issueDate", "createdTime"));
    }
}
