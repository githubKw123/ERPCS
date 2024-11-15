package net.kingborn.erp.sc.model;

import lombok.Data;
import net.kingborn.core.model.BaseModel;

/**
 * 销售按客户汇总
 */
@Data
public class SaleCustomerSummary extends BaseModel<SaleCustomerSummary> {

    /**
     * 客户ID
     */
    private String customerId;

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
