package net.kingborn.erp.bc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.core.exception.BizException;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.bc.model.*;
import net.kingborn.erp.fc.model.Receivable;
import net.kingborn.erp.fc.service.ReceivableService;
import net.kingborn.erp.wc.service.IssueProductService;
import net.kingborn.erp.bc.service.SaleService;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.fc.model.AccountRecord;
import net.kingborn.erp.fc.service.AccountRecordService;
import net.kingborn.erp.uc.model.*;
import net.kingborn.erp.uc.service.*;
import net.kingborn.erp.wc.model.IssueProduct;
import net.kingborn.erp.wc.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 销售单保存
 */
@Command
public class CSaleSave extends BaseCommand {

    @Autowired
    private SaleService saleService;
    @Autowired
    private IssueProductService issueProductService;
    @Autowired
    private SettlementAccountService settlementAccountService;
    @Autowired
    private ProductService productService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private AccountRecordService accountRecordService;
    @Autowired
    private StockService stockService;
    @Autowired
    private ReceivableService receivableService;

    @Param(required = true)
    private Sale sale;
    @Param(defaultValue = "[]")
    private List<IssueProduct> productList;
    @Param(defaultValue = "[]")
    private List<AccountRecord> accountList;

    private Sale persistedSale;
    
    @Override
    protected void init() throws Exception {
        if (!Define.validateSaleType(sale.getType())) {
            throw new BizException("销售类型不正确！");
        }
    }

    @Override
    protected void doCommand() throws Exception {
        // 校验数据
        Assert.notBlank(sale.getCustomerId(), "客户ID不能为空！");
        Customer customer = customerService.getById(sale.getCustomerId());
        Assert.notNull(customer, "ID为【" + sale.getCustomerId() + "】的客户不存在！");

        Assert.notBlank(sale.getSellerId(), "销售员ID不能为空！");
        Employee seller = employeeService.getById(sale.getSellerId());
        Assert.notNull(seller, "ID为【" + sale.getSellerId() + "】的用户不存在！");

        // 计算
        if (StrKit.isBlank(sale.getId())) {
            persistedSale = new Sale();
            persistedSale.setType(sale.getType());

            // 校验编码是否合法
            validateCode(sale.getCode());
            persistedSale.setCode(sale.getCode());
            persistedSale.setChecked(false);

        } else {
            persistedSale = saleService.getById(sale.getId());
            Assert.notNull(persistedSale, "ID为【" + sale.getId() + "】的销售订单不存在！");

            // 删除原来的商品
            issueProductService.deleteByBusiness(sale.getId());
        }

        persistedSale.setIssueDate(sale.getIssueDate());
        persistedSale.setCustomerId(sale.getCustomerId());
        persistedSale.setSellerId(sale.getSellerId());
        persistedSale.setContactName(sale.getContactName());
        persistedSale.setAddress(sale.getAddress());
        persistedSale.setPhone(sale.getPhone());
        persistedSale.setDiscountAmount(sale.getDiscountAmount());
        persistedSale.setAmount(sale.getAmount());
        persistedSale.setQuantity(getQuantity());
        persistedSale.setPreferentialRate(sale.getPreferentialRate());
        persistedSale.setPreferentialAmount(sale.getPreferentialAmount());
        persistedSale.setPreferredAmount(sale.getPreferredAmount());
        persistedSale.setCustomerFee(sale.getCustomerFee());
        persistedSale.setCurrentAmount(sale.getCurrentAmount());
        persistedSale.setDebtAmount(sale.getDebtAmount());
        persistedSale.setStatus(Define.SALE_STATUS_PART); // TODO 完善此处
        persistedSale.setAttachments(sale.getAttachments());
        persistedSale.setListerId(sale.getListerId());
        persistedSale.setRemark(sale.getRemark());
        saleService.saveOrUpdate(persistedSale);

        // 新增商品
        addProductList();

        // 新增账户记录
        String accountType = persistedSale.getType().equals(Define.BUSINESS_TYPE_SALE_SELL) ?
                Define.ACCOUNT_RECORD_TYPE_IN : Define.ACCOUNT_RECORD_TYPE_OUT;
        for (AccountRecord record : accountList) {
            record.setAmount(persistedSale.getCurrentAmount());
        }
        accountRecordService.addRecordList(accountList, accountType, persistedSale.getIssueDate(), persistedSale.getType(), persistedSale.getId());

        // 处理应收账款
        handleReceivable();

        data.put("sale", persistedSale);
    }

    /**
     * 处理应收账款
     */
    private void handleReceivable() {
        if (StrKit.notBlank(sale.getId())) {
            // 删除原来的应收账款记录
            receivableService.deleteByBusiness(sale.getId());
        }

        if (Define.BUSINESS_TYPE_SALE_SELL.equals(persistedSale.getType())) {
            receivableService.businessAdd(persistedSale.getCustomerId(), persistedSale.getIssueDate(),
                    Define.BUSINESS_TYPE_SALE_SELL, persistedSale.getId(), persistedSale.getDebtAmount(), 0);

        } else { // 销退
            receivableService.businessAdd(persistedSale.getCustomerId(), persistedSale.getIssueDate(),
                    Define.BUSINESS_TYPE_SALE_RETURNED, persistedSale.getId(), - persistedSale.getDebtAmount(), 0);
        }
    }

    /**
     * 校验单据编号是否合法
     *
     * @param code
     */
    private void validateCode(String code) {
        Sale sale = saleService.findByCode(code);
        if (sale != null) {
            throw new BizException("单据编号为【" + code + "】的销售单已经存在！");
        }
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
            persistedIssueProduct.setIssueDate(persistedSale.getIssueDate());
            persistedIssueProduct.setBusinessType(persistedSale.getType());
            persistedIssueProduct.setBusinessId(persistedSale.getId());
            persistedIssueProduct.setProductId(product.getId());
            persistedIssueProduct.setWarehouseId(warehouse.getId());

            // TODO 校验数据
            persistedIssueProduct.setQuantity(issueProduct.getQuantity());
            persistedIssueProduct.setPrice(issueProduct.getPrice());
            persistedIssueProduct.setDiscountRate(issueProduct.getDiscountRate());
            persistedIssueProduct.setDiscountAmount(issueProduct.getDiscountAmount());
            persistedIssueProduct.setAmount(issueProduct.getAmount());
            persistedIssueProduct.setCode(issueProduct.getCode());
            persistedIssueProduct.setRemark(issueProduct.getRemark());

            // 处理库存
            String stockType = persistedSale.getType().equals(Define.BUSINESS_TYPE_PURCHASE_BUY) ?
                    Define.STOCK_TYPE_IN : Define.STOCK_TYPE_OUT;
            stockService.handleStock(persistedIssueProduct, stockType);

            persistedIssueProductList.add(persistedIssueProduct);
        }

        issueProductService.saveBatch(persistedIssueProductList);
    }
}
