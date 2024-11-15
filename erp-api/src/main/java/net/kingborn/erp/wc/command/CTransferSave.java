package net.kingborn.erp.wc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.core.exception.BizException;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.wc.model.Transfer;
import net.kingborn.erp.wc.model.TransferProduct;
import net.kingborn.erp.wc.service.StockService;
import net.kingborn.erp.wc.service.TransferProductService;
import net.kingborn.erp.uc.model.*;
import net.kingborn.erp.uc.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 调拨单保存
 */
@Command
public class CTransferSave extends BaseCommand {

    @Autowired
    private net.kingborn.erp.wc.service.TransferService transferService;
    @Autowired
    private TransferProductService transferProductService;
    @Autowired
    private StockService stockService;
    @Autowired
    private ProductService productService;
    @Autowired
    private WarehouseService warehouseService;

    @Param(required = true)
    private Transfer transfer;
    @Param(defaultValue = "[]")
    private List<TransferProduct> productList;

    private Transfer persistedTransfer;
    
    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        // 计算
        if (StrKit.isBlank(transfer.getId())) {
            persistedTransfer = new Transfer();

            // 校验编码是否合法
            validateCode(transfer.getCode());
            persistedTransfer.setCode(transfer.getCode());
            persistedTransfer.setChecked(false);

        } else {
            persistedTransfer = transferService.getById(transfer.getId());
            Assert.notNull(persistedTransfer, "ID为【" + transfer.getId() + "】的调拨订单不存在！");

            // 删除原来的商品
            transferProductService.deleteByTransfer(transfer.getId());
        }

        persistedTransfer.setIssueDate(transfer.getIssueDate());
        persistedTransfer.setQuantity(getQuantity());
        persistedTransfer.setListerId(transfer.getListerId());
        persistedTransfer.setRemark(transfer.getRemark());
        transferService.saveOrUpdate(persistedTransfer);

        // 新增商品
        addProductList();

        data.put("transfer", persistedTransfer);
    }

    /**
     * 校验单据编号是否合法
     *
     * @param code
     */
    private void validateCode(String code) {
        Transfer transfer = transferService.findByCode(code);
        if (transfer != null) {
            throw new BizException("单据编号为【" + code + "】的调拨单已经存在！");
        }
    }

    /**
     * 获取总数量
     *
     * @return
     */
    private Double getQuantity() {
        double quantity = 0.0d;
        for (TransferProduct product : productList) {
            quantity += product.getQuantity();
        }

        return quantity;
    }

    /**
     * 新增商品列表
     */
    private void addProductList() {
        if (productList == null || productList.size() == 0) return;

        List<TransferProduct> persistedTransferProductList = new ArrayList<>();
        TransferProduct persistedTransferProduct;
        for (TransferProduct transferProduct : productList) {
            Assert.notBlank(transferProduct.getProductId(), "商品ID不能为空！");
            Product product = productService.getById(transferProduct.getProductId());
            Assert.notNull(product, "ID为【" + transferProduct.getProductId() + "】的商品不存在！");

            Assert.notBlank(transferProduct.getFromWarehouseId(), "调出仓库ID不能为空！");
            Warehouse fromWarehouse = warehouseService.getById(transferProduct.getFromWarehouseId());
            Assert.notNull(fromWarehouse, "ID为【" + transferProduct.getFromWarehouseId() + "】的仓库不存在！");

            Assert.notBlank(transferProduct.getToWarehouseId(), "调入仓库ID不能为空！");
            Warehouse toWarehouse = warehouseService.getById(transferProduct.getToWarehouseId());
            Assert.notNull(toWarehouse, "ID为【" + transferProduct.getToWarehouseId() + "】的仓库不存在！");

            persistedTransferProduct = new TransferProduct();
            persistedTransferProduct.setTransferId(persistedTransfer.getId());
            persistedTransferProduct.setIssueDate(persistedTransfer.getIssueDate());
            persistedTransferProduct.setProductId(product.getId());
            persistedTransferProduct.setFromWarehouseId(fromWarehouse.getId());
            persistedTransferProduct.setToWarehouseId(toWarehouse.getId());

            // TODO 校验数据
            persistedTransferProduct.setQuantity(transferProduct.getQuantity());
            persistedTransferProduct.setRemark(transferProduct.getRemark());

            // 处理库存
            stockService.handTransferStock(persistedTransferProduct);

            persistedTransferProductList.add(persistedTransferProduct);
        }

        transferProductService.saveBatch(persistedTransferProductList);
    }
}
