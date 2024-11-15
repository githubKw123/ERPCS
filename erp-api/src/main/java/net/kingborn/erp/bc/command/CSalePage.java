package net.kingborn.erp.bc.command;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.bc.model.Sale;
import net.kingborn.erp.bc.service.SaleService;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.uc.model.Customer;
import net.kingborn.erp.uc.model.Employee;
import net.kingborn.erp.uc.model.User;
import net.kingborn.erp.uc.service.CustomerService;
import net.kingborn.erp.uc.service.EmployeeService;
import net.kingborn.erp.uc.service.UserService;
import net.kingborn.erp.util.SimpleValidator;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 销售单分页列表
 */
@Command
public class CSalePage extends BaseCommand {

    @Autowired
    private SaleService saleService;
    @Autowired
    private UserService userService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private EmployeeService employeeService;

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
        Page<Sale> salePage = saleService.pageSearch(current, size, query);

        Customer customer;
        Employee seller;
        User auditor;
        User lister;
        for (Sale sale : salePage.getRecords()) {
            customer = customerService.getById(sale.getCustomerId());
            sale.put("customerName", customer.getName());

            if (StrKit.notBlank(sale.getSellerId())) {
                seller = employeeService.getById(sale.getSellerId());
                sale.put("sellerName", seller.getName());
            }

            if (StrKit.notBlank(sale.getListerId())) {
                lister = userService.getById(sale.getListerId());
                sale.put("listerName", lister.getName());
            }

            if (StrKit.notBlank(sale.getAuditorId())) {
                auditor = userService.getById(sale.getAuditorId());
                sale.put("auditorName", auditor.getName());
            }
        }

        data.put("salePage", salePage);
    }
}
