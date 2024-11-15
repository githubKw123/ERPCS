package net.kingborn.erp.fc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.erp.fc.model.Payment;
import net.kingborn.erp.fc.service.AccountRecordService;
import net.kingborn.erp.fc.service.PaymentIssueService;
import net.kingborn.erp.fc.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 付款单删除
 */
@Command
public class CPaymentDelete extends BaseCommand {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentIssueService issueService;
    @Autowired
    private AccountRecordService accountService;

    @Param(required = true)
    private String paymentId;
    
    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        Payment payment = paymentService.getById(paymentId);
        Assert.notNull(payment, "ID为【" + paymentId + "】的付款订单不存在！");

        paymentService.removeById(paymentId);

        issueService.deleteByPayment(paymentId);
        accountService.deleteByBusiness(paymentId);
    }
}
