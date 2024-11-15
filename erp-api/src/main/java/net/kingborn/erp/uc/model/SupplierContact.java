package net.kingborn.erp.uc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.kingborn.core.model.BaseModel;

import java.util.Date;

/**
 * 供应商联系人
 */
@Data
@TableName("uc_supplier_contact")
public class SupplierContact extends BaseModel<SupplierContact> {

    /**
     * 供应商联系人ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 供应商ID
     */
    private String supplierId;

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
