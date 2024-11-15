package net.kingborn.erp.sc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.bc.model.Purchase;
import net.kingborn.erp.bc.model.Sale;
import net.kingborn.erp.bc.service.PurchaseService;
import net.kingborn.erp.bc.service.SaleService;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.fc.model.Collection;
import net.kingborn.erp.fc.model.Payable;
import net.kingborn.erp.fc.model.Payment;
import net.kingborn.erp.fc.model.Receivable;
import net.kingborn.erp.fc.service.CollectionService;
import net.kingborn.erp.fc.service.PayableService;
import net.kingborn.erp.fc.service.PaymentService;
import net.kingborn.erp.fc.service.ReceivableService;
import net.kingborn.erp.uc.model.Customer;
import net.kingborn.erp.uc.model.Supplier;
import net.kingborn.erp.uc.service.CustomerService;
import net.kingborn.erp.uc.service.SupplierService;
import net.kingborn.erp.util.SimpleValidator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 应付账款明细表
 */
@Command
public class CAnalysisPayableDetailList extends BaseCommand {

    @Autowired
    private PayableService payableService;
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private SupplierService supplierService;

    /** 开始时间 */
    private @Param
    String startDate;
    /** 结束时间 */
    private @Param String endDate;
    /** 供应商ID列表 */
    private @Param
    List<String> supplierIdList;

    @Override
    protected void init() throws Exception {
        if (StrKit.notBlank(startDate)) {
            Assert.notFalse(SimpleValidator.validateDate(startDate), "起始时间不正确！");
        }
        if (StrKit.notBlank(endDate)) {
            Assert.notFalse(SimpleValidator.validateDate(endDate), "结束时间不正确！");
        }
    }

    @Override
    protected void doCommand() throws Exception {
        List<Payable> payableList = payableService.analysisList(startDate, endDate, supplierIdList);
        for (Payable payable : payableList) {
            Supplier supplier = supplierService.getById(payable.getSupplierId());
            Assert.notNull(supplier, "ID为【" + payable.getSupplierId() + "】的供应商不存在！");
            payable.put("supplierName", supplier.getName());

            if (payable.getBusinessType().equals(Define.BUSINESS_TYPE_PURCHASE_BUY)
                    || payable.getBusinessType().equals(Define.BUSINESS_TYPE_PURCHASE_REFUND)) {
                Purchase purchase = purchaseService.getById(payable.getBusinessId());
                Assert.notNull(purchase, "ID为【" + payable.getBusinessId() + "】的购货订单不存在！");

                payable.put("issueCode", purchase.getCode());
                payable.put("businessTypeName", payable.getBusinessType().equals(Define.BUSINESS_TYPE_PURCHASE_BUY) ?
                        "购货" : "购退");

            } else if (payable.getBusinessType().equals(Define.BUSINESS_TYPE_PAYMENT)) {
                Payment payment = paymentService.getById(payable.getBusinessId());
                Assert.notNull(payment, "ID为【" + payable.getBusinessId() + "】的付款单不存在！");

                payable.put("issueCode", payment.getCode());
                payable.put("businessTypeName", "付款");
            }
        }

        data.put("payableList", payableList);
    }
}
