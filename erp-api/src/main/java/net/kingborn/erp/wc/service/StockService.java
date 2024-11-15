package net.kingborn.erp.wc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.kingborn.erp.uc.model.Product;
import net.kingborn.erp.uc.model.Warehouse;
import net.kingborn.erp.wc.model.IssueProduct;
import net.kingborn.erp.wc.model.Stock;
import net.kingborn.erp.wc.model.TransferProduct;

/**
 * 库存服务
 */
public interface StockService extends IService<Stock> {

    /**
     * 根据商品和仓库获取库存
     *
     * @param productId
     * @param warehouseId
     * @return
     */
    Stock findByProductAndWarehouse(String productId, String warehouseId);

    /**
     * 处理库存
     */
    Stock handleStock(IssueProduct issueProduct, String stockType);

    /**
     * 处理调拨库存
     *
     * @param transferProduct
     */
    void handTransferStock(TransferProduct transferProduct);
}
