package net.kingborn.erp.wc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.kingborn.core.model.BaseModel;

import java.util.Date;

/**
 * 调拨商品
 */
@Data
@TableName("wc_transfer_product")
public class TransferProduct extends BaseModel<TransferProduct> {

    /**
     * 调拨商品ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 调拨ID
     */
    private String transferId;

    /**
     * 单据日期
     */
    private String issueDate;

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 调出仓库ID
     */
    private String fromWarehouseId;

    /**
     * 调入仓库ID
     */
    private String toWarehouseId;

    /**
     * 数量
     */
    private double quantity;

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
