package net.kingborn.erp.bc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.kingborn.core.model.BaseModel;

import java.util.Date;

/**
 * 客户订单
 */
@Data
@TableName("bc_order")
public class Order extends BaseModel<Order> {

    /**
     * 客户订单ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 单据日期
     */
    private String issueDate;

    /**
     * 交货日期
     */
    private String deliveryDate;

    /**
     * 单据编号
     */
    private String code;

    /**
     * 业务类型
     */
    private int businessType;

    /**
     * 客户ID
     */
    private String customerId;

    /**
     * 品类ID
     */
    private String categoryId;

    /**
     * 总金额
     */
    private double totalAmount;

    /**
     * 优惠后金额
     */
    private double discountedAmount;

    /**
     * 数量
     */
    private double quantity;

    /**
     * 优惠率
     */
    private double discountRate;

    /**
     * 制单人ID
     */
    private String listerId;

    /**
     * 审核人ID
     */
    private String auditorId;

    /**
     * 审核状态
     */
    private boolean checked;

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
