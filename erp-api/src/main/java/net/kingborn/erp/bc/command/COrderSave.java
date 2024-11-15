package net.kingborn.erp.bc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.core.exception.BizException;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.bc.model.Order;
import net.kingborn.erp.bc.service.OrderService;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.rc.model.Category;
import net.kingborn.erp.rc.service.CategoryService;
import net.kingborn.erp.uc.model.*;
import net.kingborn.erp.uc.service.*;
import net.kingborn.erp.wc.model.IssueProduct;
import net.kingborn.erp.wc.service.IssueProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户订单保存
 */
@Command
public class COrderSave extends BaseCommand {

    @Autowired
    private OrderService orderService;
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
    private CategoryService categoryService;

    @Param(required = true)
    private Order order;
    @Param(defaultValue = "[]")
    private List<IssueProduct> productList;

    private Order persistedOrder;
    
    @Override
    protected void init() throws Exception {
        if (!Define.validateOrderBusinessType(order.getBusinessType())) {
            throw new BizException("客户订单类型不正确！");
        }
    }

    @Override
    protected void doCommand() throws Exception {
        // 校验数据
        Assert.notBlank(order.getCustomerId(), "车间ID不能为空！");
        Customer customer = customerService.getById(order.getCustomerId());
        Assert.notNull(customer, "ID为【" + order.getCustomerId() + "】的车间不存在！");

        Assert.notBlank(order.getCategoryId(), "产品ID不能为空！");
        Category category = categoryService.getById(order.getCategoryId());
        Assert.notNull(category, "ID为【" + order.getCategoryId() + "】的产品不存在！");

        // 计算
        if (StrKit.isBlank(order.getId())) {
            persistedOrder = new Order();

            // TODO 校验编码是否合法
            persistedOrder.setCode(order.getCode());
            persistedOrder.setChecked(false);

        } else {
            persistedOrder = orderService.getById(order.getId());
            Assert.notNull(persistedOrder, "ID为【" + order.getId() + "】的客户订单不存在！");

            // 删除原来的商品
            issueProductService.deleteByBusiness(order.getId());
        }

        persistedOrder.setIssueDate(order.getIssueDate());
        persistedOrder.setDeliveryDate(order.getDeliveryDate());
        persistedOrder.setBusinessType(order.getBusinessType());
        persistedOrder.setCustomerId(order.getCustomerId());
        persistedOrder.setCategoryId(order.getCategoryId());
        persistedOrder.setTotalAmount(order.getTotalAmount());
        persistedOrder.setDiscountedAmount(order.getDiscountedAmount());
        persistedOrder.setQuantity(getQuantity());
        persistedOrder.setDiscountRate(order.getDiscountRate());
        persistedOrder.setListerId(order.getListerId());
        orderService.saveOrUpdate(persistedOrder);

        // 新增商品
        addProductList();

        data.put("order", persistedOrder);
    }

    /**
     * 获取总数量
     *
     * @return
     */
    private Double getQuantity() {
        double quantity = 0.0d;
        for (IssueProduct product : productList) {
            quantity += quantity+1;
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
        for (IssueProduct orderProduct : productList) {
            Assert.notBlank(orderProduct.getProductId(), "商品ID不能为空！");
            Product product = productService.getById(orderProduct.getProductId());
            Assert.notNull(product, "ID为【" + orderProduct.getProductId() + "】的商品不存在！");


            Assert.notBlank(orderProduct.getWarehouseId(), "仓库ID不能为空！");
            Warehouse warehouse = warehouseService.getById(orderProduct.getWarehouseId());
            Assert.notNull(warehouse, "ID为【" + orderProduct.getWarehouseId() + "】的仓库不存在！");

            persistedIssueProduct = new IssueProduct();
            persistedIssueProduct.setBusinessType(Define.BUSINESS_TYPE_SALE_ORDER);
            persistedIssueProduct.setBusinessId(persistedOrder.getId());
            persistedIssueProduct.setProductId(product.getId());
            persistedIssueProduct.setWarehouseId(warehouse.getId());

            // TODO 校验数据
            persistedIssueProduct.setQuantity(orderProduct.getQuantity());
            persistedIssueProduct.setPrice(orderProduct.getPrice());
            persistedIssueProduct.setDiscountRate(orderProduct.getDiscountRate());
            persistedIssueProduct.setDiscountAmount(orderProduct.getDiscountAmount());
            persistedIssueProduct.setAmount(orderProduct.getAmount());
            persistedIssueProduct.setRemark(orderProduct.getRemark());

            persistedIssueProductList.add(persistedIssueProduct);
        }

        issueProductService.saveBatch(persistedIssueProductList);
    }
}
