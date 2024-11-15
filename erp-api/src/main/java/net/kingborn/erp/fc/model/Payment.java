package net.kingborn.erp.fc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.kingborn.core.model.BaseModel;

import java.util.Date;

/**
 * 付款
 */
@Data
@TableName("fc_payment")
public class Payment extends BaseModel<Payment> {

    /**
     * 收款ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 单据日期
     */
    private String issueDate;

    /**
     * 单据编号
     */
    private String code;

    /**
     * 供应商ID
     */
    private String supplierId;

    /**
     * 付款金额
     */
    private double paidAmount;

    /**
     * 单据金额
     */
    private double issueAmount;

    /**
     * 折扣金额
     */
    private double discountAmount;

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
     * 预付款
     */
    private double advancePaidAmount;

    /**
     * 制单人ID
     */
    private String listerId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新时间
     */
    private Date updatedTime;

}
