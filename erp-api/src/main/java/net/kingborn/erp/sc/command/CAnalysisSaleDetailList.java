package net.kingborn.erp.sc.command;

import com.alibaba.fastjson.JSONObject;
import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.bc.model.Sale;
import net.kingborn.erp.wc.model.IssueProduct;
import net.kingborn.erp.wc.service.IssueProductService;
import net.kingborn.erp.bc.service.SaleService;
import net.kingborn.erp.rc.model.DictItem;
import net.kingborn.erp.rc.service.DictItemService;
import net.kingborn.erp.uc.model.Customer;
import net.kingborn.erp.uc.model.Employee;
import net.kingborn.erp.uc.model.Product;
import net.kingborn.erp.uc.model.Warehouse;
import net.kingborn.erp.uc.service.CustomerService;
import net.kingborn.erp.uc.service.EmployeeService;
import net.kingborn.erp.uc.service.ProductService;
import net.kingborn.erp.uc.service.WarehouseService;
import net.kingborn.erp.util.SimpleValidator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 采购明细表
 */
@Command
public class CAnalysisSaleDetailList extends BaseCommand {

    @Autowired
    private SaleService saleService;
    @Autowired
    private IssueProductService issueProductService;
    @Autowired
    private ProductService productService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private DictItemService dictItemService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private EmployeeService employeeService;

    /** 开始时间 */
    private @Param String startDate;
    /** 结束时间 */
    private @Param String endDate;
    /** 客户ID列表 */
    private @Param List<String> customerIdList;
    /** 商品ID列表 */
    private @Param List<String> productIdList;
    /** 仓库ID列表 */
    private @Param List<String> warehouseIdList;
    /** 销售人员ID列表 */
    private @Param List<String> sellerIdList;

    @Override
    protected void init() throws Exception {
        if (StrKit.notBlank(startDate)) {
            Assert.notFalse(SimpleValidator.validateDate(startDate), "起始时间不正确！");
        }
        if (StrKit.notBlank(endDate)) {
            Assert.notFalse(SimpleValidator.validateDate(endDate), "结束时间不正确！");
        }
    }

    @Override
    protected void doCommand() throws Exception {
        JSONObject query = new JSONObject();
        query.put("startDate", startDate);
        query.put("endDate", endDate);
        query.put("customerIdList", customerIdList);
        query.put("productIdList", productIdList);
        query.put("warehouseIdList", warehouseIdList);
        query.put("sellerIdList", sellerIdList);
        List<IssueProduct> productList = issueProductService.analysisSaleList(query);
        for (IssueProduct issueProduct : productList) {
            Sale sale = saleService.getById(issueProduct.getBusinessId());
            Assert.notNull(sale, "ID为【" + issueProduct.getBusinessId() + "】的销货单不存在！");

            Product product = productService.getById(issueProduct.getProductId());
            Assert.notNull(product, "ID为【" + issueProduct.getProductId() + "】的商品不存在！");

            issueProduct.put("productName", product.getName());
            issueProduct.put("spec", product.getSpec());
            issueProduct.put("productCode", product.getCode());

            Customer customer = customerService.getById(sale.getCustomerId());
            Assert.notNull(customer, "ID为【" + sale.getCustomerId() + "】的客户不存在！");
            issueProduct.put("customerName", customer.getName());

            DictItem unit = dictItemService.getById(product.getUnitId());
            issueProduct.put("unitName", unit.getName());

            Warehouse warehouse = warehouseService.getById(issueProduct.getWarehouseId());
            Assert.notNull(warehouse, "ID为【" + issueProduct.getWarehouseId() + "】的仓库不存在！");
            issueProduct.put("warehouseName", warehouse.getName());

            issueProduct.put("issueDate", sale.getIssueDate());
            issueProduct.put("saleCode", sale.getCode());
            issueProduct.put("type", sale.getType());

            Employee employee = employeeService.getById(sale.getSellerId());
            if (employee != null) {
                issueProduct.put("sellerName", employee.getName());
            }
        }

        data.put("productList", productList);
    }
}
