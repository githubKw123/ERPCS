package net.kingborn.erp.wc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.erp.wc.model.Transfer;
import net.kingborn.erp.wc.model.TransferProduct;
import net.kingborn.erp.wc.service.TransferProductService;
import net.kingborn.erp.wc.service.TransferService;
import net.kingborn.erp.rc.model.DictItem;
import net.kingborn.erp.rc.service.DictItemService;
import net.kingborn.erp.uc.model.Product;
import net.kingborn.erp.uc.model.Warehouse;
import net.kingborn.erp.uc.service.ProductService;
import net.kingborn.erp.uc.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 调拨单详情
 */
@Command
public class CTransferDetail extends BaseCommand {

    @Autowired
    private TransferService transferService;
    @Autowired
    private TransferProductService transferProductService;
    @Autowired
    private ProductService productService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private DictItemService dictItemService;

    @Param(required = true)
    private String transferId; // 调拨订单ID
    
    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        Transfer transfer = transferService.getById(transferId);
        Assert.notNull(transfer, "ID为【" + transferId + "】的调拨订单不存在！");

        List<TransferProduct> productList = transferProductService.findListByTransfer(transfer.getId());
        for (TransferProduct transferProduct : productList) {
            Product product = productService.getById(transferProduct.getProductId());
            Assert.notNull(product, "ID为【" + transferProduct.getProductId() + "】的商品不存在！");
            transferProduct.put("productName", product.getName());

            DictItem dictItem = dictItemService.getById(product.getUnitId());
            Assert.notNull(dictItem, "ID为【" + product.getUnitId() + "】的单位不存在！");
            transferProduct.put("unitName", dictItem.getName());

            Warehouse fromWarehouse = warehouseService.getById(transferProduct.getFromWarehouseId());
            Assert.notNull(fromWarehouse, "ID为【" + transferProduct.getFromWarehouseId() + "】的调出仓库不存在！");
            transferProduct.put("fromWarehouseName", fromWarehouse.getName());

            Warehouse toWarehouse = warehouseService.getById(transferProduct.getToWarehouseId());
            Assert.notNull(toWarehouse, "ID为【" + transferProduct.getToWarehouseId() + "】的调入仓库不存在！");
            transferProduct.put("toWarehouseName", toWarehouse.getName());
        }
        transfer.put("productList", productList);

        data.put("transfer", transfer);
    }
}
