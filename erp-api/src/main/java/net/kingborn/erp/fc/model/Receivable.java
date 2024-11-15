package net.kingborn.erp.fc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.kingborn.core.model.BaseModel;

import java.util.Date;

/**
 * 应收账款
 */
@Data
@TableName("fc_receivable")
public class Receivable extends BaseModel<Receivable> {

    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 客户ID
     */
    private String customerId;

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
     * 增加应收款金额
     */
    private double increasedAmount;

    /**
     * 付款应收款金额
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
