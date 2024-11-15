package net.kingborn.erp.wc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.core.exception.BizException;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.wc.model.Checkout;
import net.kingborn.erp.wc.model.IssueProduct;
import net.kingborn.erp.wc.model.Stock;
import net.kingborn.erp.wc.service.CheckoutService;
import net.kingborn.erp.uc.model.*;
import net.kingborn.erp.uc.service.*;
import net.kingborn.erp.wc.service.IssueProductService;
import net.kingborn.erp.wc.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 出库单保存
 */
@Command
public class CCheckoutSave extends BaseCommand {

    @Autowired
    private CheckoutService checkoutService;
    @Autowired
    private IssueProductService issueProductService;
    @Autowired
    private ProductService productService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private StockService stockService;

    @Param(required = true)
    private Checkout checkout;
    @Param(defaultValue = "[]")
    private List<IssueProduct> productList;

    private Checkout persistedCheckout;
    
    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        // 计算
        if (StrKit.isBlank(checkout.getId())) {
            persistedCheckout = new Checkout();

            // 校验编码是否合法
            validateCode(checkout.getCode());
            persistedCheckout.setCode(checkout.getCode());

        } else {
            persistedCheckout = checkoutService.getById(checkout.getId());
            Assert.notNull(persistedCheckout, "ID为【" + checkout.getId() + "】的出库订单不存在！");

            // 删除原来的商品
            issueProductService.deleteByBusiness(checkout.getId());
        }

        persistedCheckout.setIssueDate(checkout.getIssueDate());
        persistedCheckout.setType(checkout.getType());
        persistedCheckout.setCustomerId(checkout.getCustomerId());
        persistedCheckout.setAmount(getAmount());
        persistedCheckout.setQuantity(getQuantity());
        persistedCheckout.setListerId(checkout.getListerId());
        persistedCheckout.setRemark(checkout.getRemark());
        checkoutService.saveOrUpdate(persistedCheckout);

        // 新增商品
        addProductList();

        data.put("checkout", persistedCheckout);
    }

    /**
     * 校验单据编号是否合法
     *
     * @param code
     */
    private void validateCode(String code) {
        Checkout checkout = checkoutService.findByCode(code);
        if (checkout != null) {
            throw new BizException("单据编号为【" + code + "】的出库单已经存在！");
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
            quantity += product.getQuantity();
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
            persistedIssueProduct.setIssueDate(persistedCheckout.getIssueDate());
            if (Define.CHECKOUT_TYPE_LOSS == checkout.getType()) {
                persistedIssueProduct.setBusinessType(Define.BUSINESS_TYPE_CHECKOUT_LOSS);
            } else {
                persistedIssueProduct.setBusinessType(Define.BUSINESS_TYPE_CHECKOUT_OTHER);
            }

            persistedIssueProduct.setBusinessId(persistedCheckout.getId());
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
