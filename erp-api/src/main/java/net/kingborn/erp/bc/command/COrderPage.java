package net.kingborn.erp.bc.command;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.bc.model.Order;
import net.kingborn.erp.bc.service.OrderService;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.rc.model.Category;
import net.kingborn.erp.rc.service.CategoryService;
import net.kingborn.erp.uc.model.Customer;
import net.kingborn.erp.uc.model.User;
import net.kingborn.erp.uc.service.CustomerService;
import net.kingborn.erp.uc.service.UserService;
import net.kingborn.erp.util.SimpleValidator;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 客户订单分页列表
 */
@Command
public class COrderPage extends BaseCommand {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CategoryService categoryService;

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
        Page<Order> orderPage = orderService.pageSearch(current, size, query);

        Customer customer;
        User auditor;
        Category category;
        for (Order order : orderPage.getRecords()) {
            customer = customerService.getById(order.getCustomerId());
            order.put("customerName", customer.getName());

            category = categoryService.getById(order.getCategoryId());
            order.put("categoryName", category.getName());


            if (StrKit.notBlank(order.getAuditorId())) {
                auditor = userService.getById(order.getAuditorId());
                order.put("auditorName", auditor.getName());
            }
        }

        data.put("orderPage", orderPage);
    }
}
