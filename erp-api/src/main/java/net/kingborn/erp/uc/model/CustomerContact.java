package net.kingborn.erp.uc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.kingborn.core.model.BaseModel;

import java.util.Date;

/**
 * 客户联系人
 */
@Data
@TableName("uc_customer_contact")
public class CustomerContact extends BaseModel<CustomerContact> {

    /**
     * 客户联系人ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 客户ID
     */
    private String customerId;

    /**
     * 名称
     */
    private String name;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 职位
     */
    private String position;

    /**
     * QQ号
     */
    private String qq;

    /**
     * 地址
     */
    private String address;

    /**
     * 是否首要联系人
     */
    @TableField("`primary`")
    private boolean primary;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新时间
     */
    private Date updatedTime;

}
