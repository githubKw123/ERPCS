package net.kingborn.erp.fc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.kingborn.core.model.BaseModel;

import java.util.Date;

/**
 * 账单
 */
@Data
@TableName("fc_account_record")
public class AccountRecord extends BaseModel<AccountRecord> {

    /**
     * 账单ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 类型
     */
    private String type;

    /**
     * 单据日期
     */
    private String issueDate;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 业务ID
     */
    private String businessId;

    /**
     * 账户ID
     */
    private String accountId;

    /**
     * 结算金额
     */
    private double amount;

    /**
     * 结算方式
     */
    private String settlementType;

    /**
     * 结算号
     */
    private String settlementCode;

    /**
     * 当前余额
     */
    private double currentAmount;

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
