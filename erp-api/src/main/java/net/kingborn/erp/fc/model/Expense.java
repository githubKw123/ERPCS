package net.kingborn.erp.fc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.kingborn.core.model.BaseModel;

import java.util.Date;

/**
 * 支出
 */
@Data
@TableName("fc_expense")
public class Expense extends BaseModel<Expense> {

    /**
     * 支出ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 供应商ID
     */
    private String supplierId;

    /**
     * 单据日期
     */
    private String issueDate;

    /**
     * 单据编号
     */
    private String code;

    /**
     * 金额
     */
    private double amount;

    /**
     * 付款金额
     */
    private double paidAmount;

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
