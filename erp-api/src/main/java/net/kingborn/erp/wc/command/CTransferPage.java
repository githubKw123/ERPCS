package net.kingborn.erp.wc.command;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.rc.model.DictItem;
import net.kingborn.erp.rc.service.DictItemService;
import net.kingborn.erp.uc.model.Product;
import net.kingborn.erp.uc.model.Warehouse;
import net.kingborn.erp.uc.service.ProductService;
import net.kingborn.erp.uc.service.WarehouseService;
import net.kingborn.erp.wc.model.Transfer;
import net.kingborn.erp.wc.model.TransferProduct;
import net.kingborn.erp.wc.service.TransferProductService;
import net.kingborn.erp.wc.service.TransferService;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.uc.model.User;
import net.kingborn.erp.uc.service.UserService;
import net.kingborn.erp.util.SimpleValidator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 调拨单分页列表
 */
@Command
public class CTransferPage extends BaseCommand {

    @Autowired
    private TransferService transferService;
    @Autowired
    private UserService userService;
    @Autowired
    private TransferProductService transferProductService;
    @Autowired
    private ProductService productService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private DictItemService dictItemService;

    @Param(defaultValue = "{}")
    private JSONObject query; // 查询对象
    @Param(defaultValue = Define.CURRENT)
    private long current;
    @Param(defaultValue = Define.SIZE)
    private long size;

    @Override
    protected void init() throws Exception {
        String startDate = query.getString("startDate");
        if (StrKit.notBlank(startDate)) {
            Assert.notFalse(SimpleValidator.validateDate(startDate), "起始时间不正确！");
        }

        String endDate = query.getString("endDate");
        if (StrKit.notBlank(endDate)) {
            Assert.notFalse(SimpleValidator.validateDate(endDate), "结束时间不正确！");
        }
    }

    @Override
    protected void doCommand() throws Exception {
        Page<Transfer> TransferPage = transferService.pageSearch(current, size, query);

        User auditor;
        User lister;
        for (Transfer transfer : TransferPage.getRecords()) {
            transfer.put("productList", getProductList(transfer));

            if (StrKit.notBlank(transfer.getListerId())) {
                lister = userService.getById(transfer.getListerId());
                transfer.put("listerName", lister.getName());
            }

            if (StrKit.notBlank(transfer.getAuditorId())) {
                auditor = userService.getById(transfer.getAuditorId());
                transfer.put("auditorName", auditor.getName());
            }
        }

        data.put("transferPage", TransferPage);
    }

    /**
     * 获取商品信息列表
     *
     * @param transfer
     * @return
     */
    private List<TransferProduct> getProductList(Transfer transfer) {
        List<TransferProduct> transferProductList = transferProductService.findListByTransfer(transfer.getId());
        for (TransferProduct transferProduct : transferProductList) {
            Product product = productService.getById(transferProduct.getProductId());
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

        return transferProductList;
    }
}
