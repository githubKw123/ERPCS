package net.kingborn.erp.fc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.kingborn.erp.fc.model.Receivable;

import java.util.List;


/**
 * 应收账款服务
 */
public interface ReceivableService extends IService<Receivable> {

    /**
     * 业务新增
     *
     * @param customerId
     * @param issueDate
     * @param type
     * @param businessId
     * @param increasedAmount
     * @param paidAmount
     */
    void businessAdd(String customerId, String issueDate, String type, String businessId, double increasedAmount, double paidAmount);

    /**
     * 根据业务ID删除
     *
     * @param businessId
     */
    void deleteByBusiness(String businessId);

    /**
     * 获取应收账款明细表
     *
     * @param startDate
     * @param endDate
     * @param customerIdList
     * @return
     */
    List<Receivable> analysisList(String startDate, String endDate, List<String> customerIdList);

    /**
     * 根据用户获取列表
     *
     * @param startDate
     * @param endDate
     * @param customerId
     * @return
     */
    List<Receivable> listByCustomer(String startDate, String endDate, String customerId);
}
