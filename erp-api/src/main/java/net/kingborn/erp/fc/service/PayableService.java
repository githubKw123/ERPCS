package net.kingborn.erp.fc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.kingborn.erp.fc.model.Payable;

import java.util.List;


/**
 * 应付账款服务
 */
public interface PayableService extends IService<Payable> {

    /**
     * 业务新增
     *
     * @param supplierId
     * @param issueDate
     * @param type
     * @param businessId
     * @param increasedAmount
     * @param paidAmount
     */
    void businessAdd(String supplierId, String issueDate, String type, String businessId, double increasedAmount, double paidAmount);

    /**
     * 根据业务ID删除
     *
     * @param businessId
     */
    void deleteByBusiness(String businessId);

    /**
     * 获取应付账款明细表
     *
     * @param startDate
     * @param endDate
     * @param supplierIdList
     * @return
     */
    List<Payable> analysisList(String startDate, String endDate, List<String> supplierIdList);

    /**
     * 供应商对账单
     *
     * @param startDate
     * @param endDate
     * @param supplierId
     * @return
     */
    List<Payable> listBySupplier(String startDate, String endDate, String supplierId);
}
