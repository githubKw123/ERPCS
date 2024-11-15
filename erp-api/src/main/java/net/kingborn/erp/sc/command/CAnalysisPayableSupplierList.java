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
import net.kingborn.erp.util.SimpleValidator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 供应商对账单
 */
@Command
public class CAnalysisPayableSupplierList extends BaseCommand {

    @Autowired
    private PayableService payableService;
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private PaymentService paymentService;

    /** 开始时间 */
    private @Param
    String startDate;
    /** 结束时间 */
    private @Param String endDate;
    /** 供应商ID */
    private @Param(required = true)
    String supplierId;

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
        List<Payable> payableList = payableService.listBySupplier(startDate, endDate, supplierId);
        for (Payable payable : payableList) {
            if (payable.getBusinessType().equals(Define.BUSINESS_TYPE_PURCHASE_BUY)
                    || payable.getBusinessType().equals(Define.BUSINESS_TYPE_PURCHASE_REFUND)) {
                Purchase purchase = purchaseService.getById(payable.getBusinessId());
                Assert.notNull(purchase, "ID为【" + payable.getBusinessId() + "】的购货订单不存在！");

                payable.put("issueCode", purchase.getCode());
                payable.put("businessTypeName", payable.getBusinessType().equals(Define.BUSINESS_TYPE_PURCHASE_BUY) ?
                        "购货" : "购退");
                payable.put("amount", purchase.getAmount());
                payable.put("discountAmount", purchase.getDiscountAmount());
                payable.put("preferredAmount", purchase.getPreferredAmount());
                payable.put("actualAmount", purchase.getCurrentAmount());

            } else if (payable.getBusinessType().equals(Define.BUSINESS_TYPE_PAYMENT)) {
                Payment payment = paymentService.getById(payable.getBusinessId());
                Assert.notNull(payment, "ID为【" + payable.getBusinessId() + "】的付款单不存在！");

                payable.put("issueCode", payment.getCode());
                payable.put("businessTypeName", "付款");
                payable.put("actualAmount", payable.getPaidAmount());
            }
        }

        data.put("payableList", payableList);
    }
}
