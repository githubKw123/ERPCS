package net.kingborn.erp.fc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.kingborn.erp.fc.model.FlowRecord;

import java.util.List;

/**
 * 收支服务
 */
public interface FlowRecordService extends IService<FlowRecord> {
    /**
     * 获取收支列表
     *
     * @param businessId
     * @return
     */
    List<FlowRecord> findListByBusiness(String businessId);

    /**
     * 根据业务删除
     *
     * @param businessId
     */
    void deleteByBusiness(String businessId);

    /**
     * 支出明细表
     *
     * @param startDate
     * @param endDate
     * @param categoryId
     * @return
     */
    List<FlowRecord> analysisList(String startDate, String endDate, String categoryId);
}
