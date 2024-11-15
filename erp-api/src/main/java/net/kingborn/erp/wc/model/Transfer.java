package net.kingborn.erp.wc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.kingborn.core.model.BaseModel;

import java.util.Date;

/**
 * 调拨
 */
@Data
@TableName("wc_transfer")
public class Transfer extends BaseModel<Transfer> {

    /**
     * 调拨ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 单据日期
     */
    private String issueDate;

    /**
     * 单据编号
     */
    private String code;

    /**
     * 数量
     */
    private double quantity;

    /**
     * 审核状态
     */
    private boolean checked;

    /**
     * 制单人ID
     */
    private String listerId;

    /**
     * 审核人ID
     */
    private String auditorId;

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
