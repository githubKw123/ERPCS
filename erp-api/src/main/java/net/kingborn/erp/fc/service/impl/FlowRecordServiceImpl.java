package net.kingborn.erp.fc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.fc.dao.FlowRecordDao;
import net.kingborn.erp.fc.model.FlowRecord;
import net.kingborn.erp.fc.service.FlowRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlowRecordServiceImpl extends ServiceImpl<FlowRecordDao, FlowRecord> implements FlowRecordService {


    @Override
    public List<FlowRecord> findListByBusiness(String businessId) {
        return this.list(new QueryWrapper<FlowRecord>().eq("businessId", businessId));
    }

    @Override
    public void deleteByBusiness(String businessId) {
        this.remove(new QueryWrapper<FlowRecord>().eq("businessId", businessId));
    }

    @Override
    public List<FlowRecord> analysisList(String startDate, String endDate, String categoryId) {
        QueryWrapper<FlowRecord> wrapper = new QueryWrapper<>();
        if (StrKit.notBlank(startDate)) {
            wrapper.ge("issueDate", startDate);
        }
        if (StrKit.notBlank(endDate)) {
            wrapper.le("issueDate", endDate);
        }
        if (StrKit.notBlank(categoryId)) {
            wrapper.eq("categoryId", categoryId);
        }
        return this.list(wrapper.orderByAsc("issueDate", "createdTime"));
    }
}
