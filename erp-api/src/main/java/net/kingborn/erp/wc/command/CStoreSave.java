package net.kingborn.erp.wc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.core.exception.BizException;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.wc.model.IssueProduct;
import net.kingborn.erp.wc.model.Stock;
import net.kingborn.erp.wc.model.Store;
import net.kingborn.erp.wc.service.IssueProductService;
import net.kingborn.erp.wc.service.StockService;
import net.kingborn.erp.wc.service.StoreService;
import net.kingborn.erp.uc.model.*;
import net.kingborn.erp.uc.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 入库单保存
 */
@Command
public class CStoreSave extends BaseCommand {

    @Autowired
    private StoreService storeService;
    @Autowired
    private IssueProductService issueProductService;
    @Autowired
    private ProductService productService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private StockService stockService;

    @Param(required = true)
    private Store store;
    @Param(defaultValue = "[]")
    private List<IssueProduct> productList;

    private Store persistedStore;
    
    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        // 计算
        if (StrKit.isBlank(store.getId())) {
            persistedStore = new Store();

            // 校验编码是否合法
            validateCode(store.getCode());
            persistedStore.setCode(store.getCode());

        } else {
            persistedStore = storeService.getById(store.getId());
            Assert.notNull(persistedStore, "ID为【" + store.getId() + "】的入库订单不存在！");

            // 删除原来的商品
            issueProductService.deleteByBusiness(store.getId());
        }

        persistedStore.setIssueDate(store.getIssueDate());
        persistedStore.setSupplierId(store.getSupplierId());
        persistedStore.setType(store.getType());
        persistedStore.setAmount(getAmount());
        persistedStore.setQuantity(getQuantity());
        persistedStore.setListerId(store.getListerId());
        persistedStore.setRemark(store.getRemark());
        storeService.saveOrUpdate(persistedStore);

        // 新增商品
        addProductList();

        data.put("store", persistedStore);
    }

    /**
     * 校验单据编号是否合法
     *
     * @param code
     */
    private void validateCode(String code) {
        Store store = storeService.findByCode(code);
        if (store != null) {
            throw new BizException("单据编号为【" + code + "】的入库单已经存在！");
        }
    }

    /**
     * 获取总金额
     *
     * @return
     */
    private Double getAmount() {
        double amount = 0.0d;
        for (IssueProduct product : productList) {
            amount += product.getAmount();
        }

        return amount;
    }

    /**
     * 获取总数量
     *
     * @return
     */
    private Double getQuantity() {
        double quantity = 0.0d;
        for (IssueProduct product : productList) {
            quantity = quantity+1;
        }

        return quantity;
    }

    /**
     * 新增商品列表
     */
    private void addProductList() {
        if (productList == null || productList.size() == 0) return;

        List<IssueProduct> persistedIssueProductList = new ArrayList<>();
        IssueProduct persistedIssueProduct;
        for (IssueProduct issueProduct : productList) {
            Assert.notBlank(issueProduct.getProductId(), "商品ID不能为空！");
            Product product = productService.getById(issueProduct.getProductId());
            Assert.notNull(product, "ID为【" + issueProduct.getProductId() + "】的商品不存在！");


            Assert.notBlank(issueProduct.getWarehouseId(), "仓库ID不能为空！");
            Warehouse warehouse = warehouseService.getById(issueProduct.getWarehouseId());
            Assert.notNull(warehouse, "ID为【" + issueProduct.getWarehouseId() + "】的仓库不存在！");

            persistedIssueProduct = new IssueProduct();
            persistedIssueProduct.setIssueDate(persistedStore.getIssueDate());

            if (Define.STORE_TYPE_PROFIT == store.getType()) {
                persistedIssueProduct.setBusinessType(Define.BUSINESS_TYPE_STORE_PROFIT);
            } else {
                persistedIssueProduct.setBusinessType(Define.BUSINESS_TYPE_STORE_OTHER);
            }

            persistedIssueProduct.setBusinessId(persistedStore.getId());
            persistedIssueProduct.setProductId(product.getId());
            persistedIssueProduct.setWarehouseId(warehouse.getId());

            // TODO 校验数据
            persistedIssueProduct.setQuantity(issueProduct.getQuantity());
            persistedIssueProduct.setPrice(issueProduct.getPrice());
            persistedIssueProduct.setAmount(issueProduct.getAmount());
            persistedIssueProduct.setRemark(issueProduct.getRemark());

            // 处理库存
            stockService.handleStock(persistedIssueProduct, Define.STOCK_TYPE_IN);

            persistedIssueProductList.add(persistedIssueProduct);
        }

        issueProductService.saveBatch(persistedIssueProductList);
    }
}
