package net.kingborn.erp.uc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.kingborn.core.model.BaseModel;

import java.util.Date;

/**
 * 商品
 */
@Data
@TableName("uc_product")
public class Product extends BaseModel<Product> {

    /**
     * 商品ID
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
     * 条形码
     */
    private String barcode;

    /**
     * 规格
     */
    private String spec;

    /**
     * 类别ID
     */
    private String categoryId;

    /**
     * 首选仓库ID
     */
    private String primaryWarehouseId;

    /**
     * 计量单位ID
     */
    private String unitId;

    /**
     * 零售价格
     */
    private double retailPrice;

    /**
     * 批发价格
     */
    private double wholesalePrice;

    /**
     * VIP价格
     */
    private double vipPrice;

    /**
     * 折扣价格1
     */
    private double discountRate1;

    /**
     * 折扣价格2
     */
    private double discountRate2;

    /**
     * 预计采购价
     */
    private double estimatedPurchasePrice;

    /**
     * 备注
     */
    private String remark;

    /**
     * 最低库存
     */
    private double minimumStock;

    /**
     * 最高库存
     */
    private double maximumStock;

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
