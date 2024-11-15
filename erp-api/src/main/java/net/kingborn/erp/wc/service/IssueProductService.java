package net.kingborn.erp.wc.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import net.kingborn.erp.sc.model.PurchaseSupplierSummary;
import net.kingborn.erp.wc.model.IssueProduct;
import net.kingborn.erp.sc.model.SaleCustomerSummary;

import java.util.List;

/**
 * 单据商品服务
 */
public interface IssueProductService extends IService<IssueProduct> {
    /**
     * 获取单据订单商品列表
     *
     * @param businessId
     * @return
     */
    List<IssueProduct> findListByBusiness(String businessId);

    /**
     * 依据类型获取信息
     *
     * @param businessType
     * @return
     */
    List<IssueProduct> findListBytype(String businessType);

    /**
     * 根据订单删除
     *
     * @param businessId
     */
    void deleteByBusiness(String businessId);

    /**
     * 获取销售明细表
     * @param query
     * @return
     */
    List<IssueProduct> analysisSaleList(JSONObject query);

    /**
     * 获取销售汇总表（按商品）
     *
     * @param query
     * @return
     */
    List<IssueProduct> analysisSaleListByProduct(JSONObject query);

    /**
     * 获取销售汇总表（按客户）
     *
     * @param query
     * @return
     */
    List<SaleCustomerSummary> analysisSaleListByCustomer(JSONObject query);

    /**
     * 获取采购明细表
     * @param query
     * @return
     */
    List<IssueProduct> analysisPurchaseList(JSONObject query);

    /**
     * 获取采购汇总表（按商品）
     *
     * @param query
     * @return
     */
    List<IssueProduct> analysisPurchaseListByProduct(JSONObject query);

    /**
     * 获取采购汇总表（按供应商）
     *
     * @param query
     * @return
     */
    List<PurchaseSupplierSummary> analysisPurchaseListBySupplier(JSONObject query);

}
