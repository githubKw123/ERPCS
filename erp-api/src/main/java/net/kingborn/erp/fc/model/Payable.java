package net.kingborn.erp.fc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.kingborn.core.model.BaseModel;

import java.util.Date;

/**
 * 应付账款
 */
@Data
@TableName("fc_payable")
public class Payable extends BaseModel<Payable> {

    /**
     * ID
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
     * 业务类型
     */
    private String businessType;

    /**
     * 业务ID
     */
    private String businessId;

    /**
     * 增加应付款金额
     */
    private double increasedAmount;

    /**
     * 付款应付款金额
     */
    private double paidAmount;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新时间
     */
    private Date updatedTime;

}
