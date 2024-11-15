package net.kingborn.erp.fc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.fc.dao.ReceivableDao;
import net.kingborn.erp.fc.model.AccountRecord;
import net.kingborn.erp.fc.model.Receivable;
import net.kingborn.erp.fc.service.ReceivableService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceivableServiceImpl extends ServiceImpl<ReceivableDao, Receivable> implements ReceivableService {

    @Override
    public void businessAdd(String customerId, String issueDate, String businessType, String businessId, double increasedAmount, double paidAmount) {
        Receivable receivable = new Receivable();
        receivable.setCustomerId(customerId);
        receivable.setIssueDate(issueDate);
        receivable.setBusinessType(businessType);
        receivable.setBusinessId(businessId);
        receivable.setIncreasedAmount(increasedAmount);
        receivable.setPaidAmount(paidAmount);
        save(receivable);
    }

    @Override
    public void deleteByBusiness(String businessId) {
        this.remove(new QueryWrapper<Receivable>().eq("businessId", businessId));
    }

    @Override
    public List<Receivable> analysisList(String startDate, String endDate, List<String> customerIdList) {
        QueryWrapper<Receivable> wrapper = new QueryWrapper<>();
        if (StrKit.notBlank(startDate)) {
            wrapper.ge("issueDate", startDate);
        }
        if (StrKit.notBlank(endDate)) {
            wrapper.le("issueDate", endDate);
        }
        if (customerIdList != null && customerIdList.size() > 0) {
            wrapper.in("customerId", customerIdList);
        }
        return this.list(wrapper.orderByAsc("customerId", "issueDate", "createdTime"));
    }

    @Override
    public List<Receivable> listByCustomer(String startDate, String endDate, String customerId) {
        QueryWrapper<Receivable> wrapper = new QueryWrapper<>();
        if (StrKit.notBlank(startDate)) {
            wrapper.ge("issueDate", startDate);
        }
        if (StrKit.notBlank(endDate)) {
            wrapper.le("issueDate", endDate);
        }
        if (StrKit.notBlank(customerId)) {
            wrapper.eq("customerId", customerId);
        }
        return this.list(wrapper.orderByAsc("issueDate", "createdTime"));
    }
}
