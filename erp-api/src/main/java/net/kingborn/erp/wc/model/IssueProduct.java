package net.kingborn.erp.wc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.kingborn.core.model.BaseModel;

import java.util.Date;

/**
 * 单据商品
 */
@Data
@TableName("wc_issue_product")
public class IssueProduct extends BaseModel<IssueProduct> {

    /**
     * 销货单商品ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 单据时间
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
     * 商品ID
     */
    private String productId;

    /**
     * 仓库ID
     */
    private String warehouseId;

    /**
     * 供应商ID
     */
    private String supplierId;

    /**
     * 数量
     */
    private double quantity;

    /**
     * 单价
     */
    private double price;

    /**
     * 折扣率
     */
    private double discountRate;

    /**
     * 折扣额
     */
    private double discountAmount;

    /**
     * 购货金额
     */
    private double amount;

    /**
     * 序列号
     */
    private String code;

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
