package net.kingborn.erp.fc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.kingborn.erp.fc.model.AccountRecord;

import java.util.List;

/**
 * 账单服务
 */
public interface AccountRecordService extends IService<AccountRecord> {

    /**
     * 获取账单列表
     *
     * @param targetId
     * @return
     */
    List<AccountRecord> findListByBusiness(String targetId);

    /**
     * 根据目标ID删除
     *
     * @param targetId
     */
    void deleteByBusiness(String targetId);

    /**
     * 新增记录列表
     *
     * @param recordList
     */
    void addRecordList(List<AccountRecord> recordList, String type, String issueDate, String businessType, String businessId);

    /**
     * 获取现金银行报表
     *
     * @param startDate
     * @param endDate
     * @param accountIdList
     * @return
     */
    List<AccountRecord> analysisList(String startDate, String endDate, List<String> accountIdList);
}
