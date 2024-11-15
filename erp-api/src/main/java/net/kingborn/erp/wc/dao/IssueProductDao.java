package net.kingborn.erp.wc.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.kingborn.erp.sc.model.PurchaseSupplierSummary;
import net.kingborn.erp.wc.model.IssueProduct;
import net.kingborn.erp.sc.model.SaleCustomerSummary;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 单据商品Dao
 */
@Component
public interface IssueProductDao extends BaseMapper<IssueProduct> {

    /**
     * 销售明细
     * @param query
     * @return
     */
    List<IssueProduct> analysisSaleList(@Param("query") JSONObject query);

    /**
     * 销售明细（按商品）
     *
     * @param query
     * @return
     */
    List<IssueProduct> analysisSaleListByProduct(@Param("query") JSONObject query);

    /**
     * 销售明细（按客户）
     *
     * @param query
     * @return
     */
    List<SaleCustomerSummary> analysisSaleListByCustomer(@Param("query") JSONObject query);

    /**
     * 采购明细
     * @param query
     * @return
     */
    List<IssueProduct> analysisPurchaseList(@Param("query") JSONObject query);

    /**
     * 采购明细（按商品）
     *
     * @param query
     * @return
     */
    List<IssueProduct> analysisPurchaseListByProduct(@Param("query") JSONObject query);

    /**
     * 采购明细（按供应商）
     *
     * @param query
     * @return
     */
    List<PurchaseSupplierSummary> analysisPurchaseListBySupplier(@Param("query") JSONObject query);
}
