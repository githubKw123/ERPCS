package net.kingborn.erp.bc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.kingborn.core.model.BaseModel;

import java.util.Date;

/**
 * 销货单
 */
@Data
@TableName("bc_sale")
public class Sale extends BaseModel<Sale> {

    /**
     * 销售单ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 类型：buy、售货 returned、退货
     */
    private String type;

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
     * 销售人ID（职员ID）
     */
    private String sellerId;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 地址
     */
    private String address;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 数量
     */
    private double quantity;

    /**
     * 折扣金额
     */
    private double discountAmount;

    /**
     * 金额
     */
    private double amount;

    /**
     * 优惠率
     */
    private double preferentialRate;

    /**
     * 优惠金额
     */
    private double preferentialAmount;

    /**
     * 优惠后金额
     */
    private double preferredAmount;

    /**
     * 客户费用
     */
    private double customerFee;

    /**
     * 本次收款
     */
    private double currentAmount;

    /**
     * 本次欠款
     */
    private double debtAmount;

    /**
     * 收款状态：10、未收/退款 20、部分收/退款 30、全部收/退款
     */
    private int status;

    /**
     * 销售附件
     */
    private String attachments;

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
