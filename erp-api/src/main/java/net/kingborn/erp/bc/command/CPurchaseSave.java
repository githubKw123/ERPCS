package net.kingborn.erp.bc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.core.exception.BizException;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.bc.model.Purchase;
import net.kingborn.erp.bc.service.PurchaseService;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.fc.model.AccountRecord;
import net.kingborn.erp.fc.service.AccountRecordService;
import net.kingborn.erp.fc.service.PayableService;
import net.kingborn.erp.uc.model.Product;
import net.kingborn.erp.uc.model.Supplier;
import net.kingborn.erp.uc.model.Warehouse;
import net.kingborn.erp.uc.service.ProductService;
import net.kingborn.erp.uc.service.SettlementAccountService;
import net.kingborn.erp.uc.service.SupplierService;
import net.kingborn.erp.uc.service.WarehouseService;
import net.kingborn.erp.wc.model.IssueProduct;
import net.kingborn.erp.wc.service.IssueProductService;
import net.kingborn.erp.wc.service.StockRecordService;
import net.kingborn.erp.wc.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 购货单保存
 */
@Command
public class CPurchaseSave extends BaseCommand {

    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private IssueProductService issueProductService;
    @Autowired
    private AccountRecordService accountRecordService;
    @Autowired
    private SettlementAccountService settlementAccountService;
    @Autowired
    private ProductService productService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private StockService stockService;
    @Autowired
    private PayableService payableService;

    @Param(required = true)
    private Purchase purchase;
    @Param(defaultValue = "[]")
    private List<IssueProduct> productList;
    @Param(defaultValue = "[]")
    private List<AccountRecord> accountList;

    private Purchase persistedPurchase;

    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        // 校验数据
        Assert.notBlank(purchase.getSupplierId(), "供应商ID不能为空！");
        Supplier supplier = supplierService.getById(purchase.getSupplierId());
        Assert.notNull(supplier, "ID为【" + purchase.getSupplierId() + "】的供应商不存在！");

        Assert.notFalse(Define.validatePurchaseType(purchase.getType()), "类型不正确！");

        // 计算
        if (StrKit.isBlank(purchase.getId())) {
            persistedPurchase = new Purchase();
            persistedPurchase.setType(purchase.getType());

            // TODO 校验编码是否合法
            validatePurchaseCode(purchase.getCode());
            persistedPurchase.setCode(purchase.getCode());
            persistedPurchase.setChecked(false);

        } else {
            persistedPurchase = purchaseService.getById(purchase.getId());
            Assert.notNull(persistedPurchase, "ID为【" + purchase.getId() + "】的购货单不存在！");

            // 删除原来的商品
            issueProductService.deleteByBusiness(purchase.getId());

            // 删除原来的账户
            accountRecordService.deleteByBusiness(purchase.getId());
        }

        persistedPurchase.setSupplierId(purchase.getSupplierId());
        persistedPurchase.setIssueDate(purchase.getIssueDate());
        persistedPurchase.setCode(purchase.getCode());
        persistedPurchase.setQuantity(getPurchaseQuantity());
        persistedPurchase.setDiscountAmount(purchase.getDiscountAmount());
        persistedPurchase.setAmount(getAmount());
        persistedPurchase.setPreferentialRate(purchase.getPreferentialRate());
        persistedPurchase.setPreferentialAmount(purchase.getPreferentialAmount());
        persistedPurchase.setPreferredAmount(purchase.getPreferredAmount());
        persistedPurchase.setCurrentAmount(purchase.getCurrentAmount());
        persistedPurchase.setDebtAmount(purchase.getDebtAmount());
        persistedPurchase.setContracts(purchase.getContracts());
        persistedPurchase.setStatus(Define.PURCHASE_STATUS_UNPAID);
        persistedPurchase.setListerId(purchase.getListerId());
        purchaseService.saveOrUpdate(persistedPurchase);

        // 新增商品
        addProductList();

        // 新增账户
        String accountType = persistedPurchase.getType().equals(Define.BUSINESS_TYPE_PURCHASE_BUY) ?
                Define.ACCOUNT_RECORD_TYPE_OUT : Define.ACCOUNT_RECORD_TYPE_IN;
        for (AccountRecord record : accountList) {
            record.setAmount(persistedPurchase.getCurrentAmount());
        }
        accountRecordService.addRecordList(accountList, accountType, persistedPurchase.getIssueDate(), persistedPurchase.getType(), persistedPurchase.getId());

        // 处理应付账款
        handlePayable();

        data.put("purchase", persistedPurchase);
    }



    /**
     * 处理应付账款
     */
    private void handlePayable() {
        if (StrKit.notBlank(purchase.getId())) {
            // 删除原来的应收账款记录
            payableService.deleteByBusiness(purchase.getId());
        }

        if (Define.BUSINESS_TYPE_PURCHASE_BUY.equals(persistedPurchase.getType())) {
            payableService.businessAdd(persistedPurchase.getSupplierId(), persistedPurchase.getIssueDate(),
                    Define.BUSINESS_TYPE_PURCHASE_BUY, persistedPurchase.getId(), persistedPurchase.getDebtAmount(), 0);

        } else { // 销退
            payableService.businessAdd(persistedPurchase.getSupplierId(), persistedPurchase.getIssueDate(),
                    Define.BUSINESS_TYPE_PURCHASE_REFUND, persistedPurchase.getId(), - persistedPurchase.getDebtAmount(), 0);
        }
    }

    /**
     * 校验单据编号是否合法
     *
     * @param code
     */
    private void validatePurchaseCode(String code) {
        Purchase purchase = purchaseService.findByCode(code);
        if (purchase != null) {
            throw new BizException("单据编号为【" + code + "】的购货单已经存在！");
        }
    }

    /**
     * 获取总数量
     *
     * @return
     */
    private Double getPurchaseQuantity() {
        double quantity = 0.0d;
        for (IssueProduct product : productList) {
            quantity = quantity+1;
        }

        return quantity;
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
            persistedIssueProduct.setCode(issueProduct.getCode());
            persistedIssueProduct.setIssueDate(persistedPurchase.getIssueDate());
            persistedIssueProduct.setBusinessType(persistedPurchase.getType());
            persistedIssueProduct.setBusinessId(persistedPurchase.getId());
            persistedIssueProduct.setSupplierId(persistedPurchase.getSupplierId());
            persistedIssueProduct.setProductId(product.getId());
            persistedIssueProduct.setWarehouseId(warehouse.getId());

            // TODO 校验数据
            persistedIssueProduct.setQuantity(issueProduct.getQuantity());
            persistedIssueProduct.setPrice(issueProduct.getPrice());
            persistedIssueProduct.setDiscountRate(issueProduct.getDiscountRate());
            persistedIssueProduct.setDiscountAmount(issueProduct.getDiscountAmount());
            persistedIssueProduct.setAmount(issueProduct.getAmount());
            persistedIssueProduct.setRemark(issueProduct.getRemark());

            // 处理库存
            String stockType = persistedPurchase.getType().equals(Define.BUSINESS_TYPE_PURCHASE_BUY) ?
                    Define.STOCK_TYPE_IN : Define.STOCK_TYPE_OUT;
            stockService.handleStock(persistedIssueProduct, stockType);

            persistedIssueProductList.add(persistedIssueProduct);
        }

        issueProductService.saveBatch(persistedIssueProductList);
    }
}
