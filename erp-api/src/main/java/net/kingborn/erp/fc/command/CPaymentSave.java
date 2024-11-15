package net.kingborn.erp.fc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.core.exception.BizException;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.fc.model.AccountRecord;
import net.kingborn.erp.fc.model.Payment;
import net.kingborn.erp.fc.model.PaymentIssue;
import net.kingborn.erp.fc.service.AccountRecordService;
import net.kingborn.erp.fc.service.PayableService;
import net.kingborn.erp.fc.service.PaymentIssueService;
import net.kingborn.erp.fc.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 付款单保存
 */
@Command
public class CPaymentSave extends BaseCommand {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private AccountRecordService accountService;
    @Autowired
    private PaymentIssueService issueService;
    @Autowired
    private PayableService payableService;

    @Param(required = true)
    private Payment payment;
    @Param(defaultValue = "[]")
    private List<PaymentIssue> issueList;
    @Param(defaultValue = "[]")
    private List<AccountRecord> accountList;

    private Payment persistedPayment;
    
    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        // 计算
        if (StrKit.isBlank(payment.getId())) {
            persistedPayment = new Payment();

            // 校验编码是否合法
            validateCode(payment.getCode());
            persistedPayment.setCode(payment.getCode());

        } else {
            persistedPayment = paymentService.getById(payment.getId());
            Assert.notNull(persistedPayment, "ID为【" + payment.getId() + "】的付款订单不存在！");

            // 删除原来的账户
            accountService.deleteByBusiness(payment.getId());

            // 删除关联的单据
            issueService.deleteByPayment(payment.getId());
        }

        persistedPayment.setSupplierId(payment.getSupplierId());
        persistedPayment.setIssueDate(payment.getIssueDate());
        persistedPayment.setPaidAmount(payment.getPaidAmount());
        persistedPayment.setIssueAmount(payment.getIssueAmount());
        persistedPayment.setDiscountAmount(payment.getDiscountAmount());
        persistedPayment.setVerifiedAmount(payment.getVerifiedAmount());
        persistedPayment.setUnverifiedAmount(payment.getUnverifiedAmount());
        persistedPayment.setCurrentVerifiedAmount(payment.getCurrentVerifiedAmount());
        persistedPayment.setAdvancePaidAmount(payment.getAdvancePaidAmount());
        persistedPayment.setListerId(payment.getListerId());
        persistedPayment.setRemark(payment.getRemark());
        paymentService.saveOrUpdate(persistedPayment);

        // 新增账户
        accountService.addRecordList(accountList, Define.ACCOUNT_RECORD_TYPE_OUT, persistedPayment.getIssueDate(), Define.BUSINESS_TYPE_PAYMENT, persistedPayment.getId());

        // 新增单据
        addIssueList();

        // 处理应付账款
        handlePayable();

        data.put("payment", persistedPayment);
    }

    /**
     * 处理应付账款
     */
    private void handlePayable() {
        if (StrKit.notBlank(payment.getId())) {
            // 删除原来的应收账款记录
            payableService.deleteByBusiness(payment.getId());
        }

        payableService.businessAdd(persistedPayment.getSupplierId(), persistedPayment.getIssueDate(),
                Define.BUSINESS_TYPE_PAYMENT, persistedPayment.getId(), 0, persistedPayment.getAdvancePaidAmount());
    }

    /**
     * 校验单据编号是否合法
     *
     * @param code
     */
    private void validateCode(String code) {
        Payment Payment = paymentService.findByCode(code);
        if (Payment != null) {
            throw new BizException("单据编号为【" + code + "】的入库单已经存在！");
        }
    }

    /**
     * 新增商品列表
     */
    private void addIssueList() {
        if (issueList == null || issueList.size() == 0) return;

        List<PaymentIssue> persistedIssueList = new ArrayList<>();
        PaymentIssue persistedIssue;
        for (PaymentIssue issue : issueList) {
            persistedIssue = new PaymentIssue();
            persistedIssue.setPaymentId(persistedPayment.getId());
            persistedIssue.setSourceCode(issue.getSourceCode());
            persistedIssue.setType(issue.getType());
            persistedIssue.setIssueDate(issue.getIssueDate());
            persistedIssue.setIssueAmount(issue.getIssueAmount());
            persistedIssue.setVerifiedAmount(issue.getVerifiedAmount());
            persistedIssue.setUnverifiedAmount(issue.getUnverifiedAmount());
            persistedIssue.setCurrentVerifiedAmount(issue.getCurrentVerifiedAmount());

            persistedIssueList.add(persistedIssue);
        }

        issueService.saveBatch(persistedIssueList);
    }
}
