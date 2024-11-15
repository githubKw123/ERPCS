package net.kingborn.erp.fc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.fc.dao.PayableDao;
import net.kingborn.erp.fc.model.Payable;
import net.kingborn.erp.fc.service.PayableService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayableServiceImpl extends ServiceImpl<PayableDao, Payable> implements PayableService {

    @Override
    public void businessAdd(String supplierId, String issueDate, String businessType, String businessId, double increasedAmount, double paidAmount) {
        Payable payable = new Payable();
        payable.setSupplierId(supplierId);
        payable.setIssueDate(issueDate);
        payable.setBusinessType(businessType);
        payable.setBusinessId(businessId);
        payable.setIncreasedAmount(increasedAmount);
        payable.setPaidAmount(paidAmount);
        save(payable);
    }

    @Override
    public void deleteByBusiness(String businessId) {
        this.remove(new QueryWrapper<Payable>().eq("businessId", businessId));
    }

    @Override
    public List<Payable> analysisList(String startDate, String endDate, List<String> supplierIdList) {
        QueryWrapper<Payable> wrapper = new QueryWrapper<>();
        if (StrKit.notBlank(startDate)) {
            wrapper.ge("issueDate", startDate);
        }
        if (StrKit.notBlank(endDate)) {
            wrapper.le("issueDate", endDate);
        }
        if (supplierIdList != null && supplierIdList.size() > 0) {
            wrapper.in("supplierId", supplierIdList);
        }
        return this.list(wrapper.orderByAsc("supplierId", "issueDate", "createdTime"));
    }

    @Override
    public List<Payable> listBySupplier(String startDate, String endDate, String supplierId) {
        QueryWrapper<Payable> wrapper = new QueryWrapper<>();
        if (StrKit.notBlank(startDate)) {
            wrapper.ge("issueDate", startDate);
        }
        if (StrKit.notBlank(endDate)) {
            wrapper.le("issueDate", endDate);
        }
        if (StrKit.notBlank(supplierId)) {
            wrapper.eq("supplierId", supplierId);
        }
        return this.list(wrapper.orderByAsc("issueDate", "createdTime"));
    }
}
