package net.kingborn.erp.wc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.kingborn.core.model.BaseModel;

import java.util.Date;

/**
 * 库存
 */
@Data
@TableName("wc_stock")
public class Stock extends BaseModel<Stock> {

    /**
     * 库存ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 仓库ID
     */
    private String warehouseId;

    /**
     * 数量
     */
    private double quantity;

    /**
     * 单价
     */
    private double price;

    /**
     * 成本
     */
    private double amount;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新时间
     */
    private Date updatedTime;

}
