package net.kingborn.erp.fc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.kingborn.core.model.BaseModel;

import java.util.Date;

/**
 * 收款
 */
@Data
@TableName("fc_collection")
public class Collection extends BaseModel<Collection> {

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
     * 客户ID
     */
    private String customerId;

    /**
     * 收款金额
     */
    private double collectAmount;

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
     * 预收款
     */
    private double advanceCollectAmount;

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
