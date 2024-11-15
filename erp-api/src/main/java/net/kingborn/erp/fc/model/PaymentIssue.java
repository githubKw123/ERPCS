package net.kingborn.erp.fc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.kingborn.core.model.BaseModel;

import java.util.Date;

/**
 * 付款单据
 */
@Data
@TableName("fc_payment_issue")
public class PaymentIssue extends BaseModel<PaymentIssue> {

    /**
     * 收款单据ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 付款ID
     */
    private String paymentId;

    /**
     * 源单编码
     */
    private String sourceCode;

    /**
     * 类别：10、购货 20、退货
     */
    private int type;

    /**
     * 单据日期
     */
    private String issueDate;

    /**
     * 单据金额
     */
    private double issueAmount;

    /**
     * 已核销金额
     */
    private double verifiedAmount;

    /**
     * 未核销金额
     */
    private double unverifiedAmount;

    /**
     * 本次核销金额
     */
    private double currentVerifiedAmount;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新时间
     */
    private Date updatedTime;

}
