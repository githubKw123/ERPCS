package net.kingborn.erp.fc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.kingborn.erp.fc.dao.PaymentIssueDao;
import net.kingborn.erp.fc.model.PaymentIssue;
import net.kingborn.erp.fc.service.PaymentIssueService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentIssueServiceImpl extends ServiceImpl<PaymentIssueDao, PaymentIssue> implements PaymentIssueService {


    @Override
    public List<PaymentIssue> findListByPayment(String paymentId) {
        return this.list(new QueryWrapper<PaymentIssue>().eq("paymentId", paymentId));
    }

    @Override
    public void deleteByPayment(String paymentId) {
        this.remove(new QueryWrapper<PaymentIssue>().eq("paymentId", paymentId));
    }
}
