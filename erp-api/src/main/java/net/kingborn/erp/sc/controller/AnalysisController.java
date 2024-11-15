package net.kingborn.erp.sc.controller;


import net.kingborn.core.controller.BaseController;
import net.kingborn.core.controller.Controller;
import net.kingborn.core.entry.Result;
import net.kingborn.erp.sc.command.*;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 分析控制器
 */
@Controller("/analysis")
public class AnalysisController extends BaseController {

    /**
     * 采购明细表
     */
    @PostMapping("/purchase/detailList")
    public Result purchaseDetailList() {
        return doAction(CAnalysisPurchaseDetailList.class);
    }

    /**
     * 采购汇总表（按商品）
     */
    @PostMapping("/purchase/productSummaryList")
    public Result purchaseProductSummaryList() {
        return doAction(CAnalysisPurchaseProductSummaryList.class);
    }

    /**
     * 采购汇总表（按供应商）
     */
    @PostMapping("/purchase/supplierSummaryList")
    public Result purchaseSupplierSummaryList() {
        return doAction(CAnalysisPurchaseSupplierSummaryList.class);
    }

    /**
     * 销售明细表
     */
    @PostMapping("/sale/detailList")
    public Result saleDetailList() {
        return doAction(CAnalysisSaleDetailList.class);
    }

    /**
     * 销售汇总表（按商品）
     */
    @PostMapping("/sale/productSummaryList")
    public Result saleProductSummaryList() {
        return doAction(CAnalysisSaleProductSummaryList.class);
    }

    /**
     * 销售汇总表（按客户）
     */
    @PostMapping("/sale/customerSummaryList")
    public Result saleCustomerSummaryList() {
        return doAction(CAnalysisSaleCustomerSummaryList.class);
    }

    /**
     * 收发明细表
     */
    @PostMapping("/stock/detailList")
    public Result stockDetailList() {
        return doAction(CAnalysisStockDetailList.class);
    }

    /**
     * 库存余额表
     */
    @PostMapping("/stock/amountList")
    public Result stockAmountList() {
        return doAction(CAnalysisStockAmountList.class);
    }

    /**
     * 收发汇总表
     */
    @PostMapping("/stock/summaryList")
    public Result stockSummaryList() {
        return doAction(CAnalysisStockSummaryList.class);
    }

    /**
     * 现金银行报表
     */
    @PostMapping("/account/detailList")
    public Result accountDetailList() {
        return doAction(CAnalysisAccountDetailList.class);
    }

    /**
     * 应收账款明细表
     */
    @PostMapping("/receivable/detailList")
    public Result receivableDetailList() {
        return doAction(CAnalysisReceivableDetailList.class);
    }

    /**
     * 应付账款明细表
     */
    @PostMapping("/payable/detailList")
    public Result payableDetailList() {
        return doAction(CAnalysisPayableDetailList.class);
    }

    /**
     * 客户对账单
     */
    @PostMapping("/receivable/customerList")
    public Result receivableCustomerList() {
        return doAction(CAnalysisReceivableCustomerList.class);
    }

    /**
     * 供应商对账单
     */
    @PostMapping("/payable/supplierList")
    public Result payableSupplierList() {
        return doAction(CAnalysisPayableSupplierList.class);
    }

    /**
     * 其他收支明细表
     */
    @PostMapping("/flow/detailList")
    public Result flowDetailList() {
        return doAction(CAnalysisFlowDetailList.class);
    }

}
