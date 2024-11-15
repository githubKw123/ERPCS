package net.kingborn.erp.sc.model;

import lombok.Data;
import net.kingborn.core.model.BaseModel;

/**
 * 采购按供应商汇总
 */
@Data
public class PurchaseSupplierSummary extends BaseModel<PurchaseSupplierSummary> {

    /**
     * 供应商ID
     */
    private String supplierId;

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
     * 价格
     */
    private double price;

    /**
     * 采购金额
     */
    private double amount;

}
