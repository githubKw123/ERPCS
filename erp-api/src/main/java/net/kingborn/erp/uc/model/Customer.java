package net.kingborn.erp.uc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.kingborn.core.model.BaseModel;

import java.util.Date;

/**
 * 客户
 */
@Data
@TableName("uc_customer")
public class Customer extends BaseModel<Customer> {

    /**
     * 客户ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 客户分类ID
     */
    private String categoryId;

    /**
     * 客户等级
     */
    private String level;

    /**
     * 余额日期
     */
    private Date balanceTime;

    /**
     * 期初应收款
     */
    private double beginReceivableAmount;

    /**
     * 期初预付款
     */
    private double beginPrepaidAmount;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否启用
     */
    private boolean active;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新时间
     */
    private Date updatedTime;

}
