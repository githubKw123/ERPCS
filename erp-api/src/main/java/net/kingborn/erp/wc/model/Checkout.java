package net.kingborn.erp.wc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.kingborn.core.model.BaseModel;

import java.util.Date;

/**
 * 出库
 */
@Data
@TableName("wc_checkout")
public class Checkout extends BaseModel<Checkout> {

    /**
     * 出库ID
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
     * 类型：10、其他入库 20、盘亏
     */
    private int type;

    /**
     * 客户ID
     */
    private String customerId;

    /**
     * 出库成本
     */
    private double amount;

    /**
     * 数量
     */
    private double quantity;

    /**
     * 制单人ID
     */
    private String listerId;

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
