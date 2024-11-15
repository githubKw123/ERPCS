package net.kingborn.erp.fc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.erp.fc.model.AccountRecord;
import net.kingborn.erp.fc.model.Payment;
import net.kingborn.erp.fc.model.PaymentIssue;
import net.kingborn.erp.fc.service.AccountRecordService;
import net.kingborn.erp.fc.service.PaymentIssueService;
import net.kingborn.erp.fc.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 付款单详情
 */
@Command
public class CPaymentDetail extends BaseCommand {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private AccountRecordService accountService;
    @Autowired
    private PaymentIssueService issueService;

    @Param(required = true)
    private String paymentId; // 付款订单ID
    
    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        Payment payment = paymentService.getById(paymentId);
        Assert.notNull(payment, "ID为【" + paymentId + "】的付款订单不存在！");

        List<AccountRecord> accountList = accountService.findListByBusiness(payment.getId());
        payment.put("accountList", accountList);

        List<PaymentIssue> issueList = issueService.findListByPayment(payment.getId());
        payment.put("issueList", issueList);

        data.put("payment", payment);
    }
}
