package net.kingborn.erp.wc.command;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.wc.model.Checkout;
import net.kingborn.erp.wc.service.CheckoutService;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.uc.model.Customer;
import net.kingborn.erp.uc.model.User;
import net.kingborn.erp.uc.service.CustomerService;
import net.kingborn.erp.uc.service.UserService;
import net.kingborn.erp.util.SimpleValidator;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 出库单分页列表
 */
@Command
public class CCheckoutPage extends BaseCommand {

    @Autowired
    private CheckoutService checkoutService;
    @Autowired
    private UserService userService;
    @Autowired
    private CustomerService customerService;

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
        Page<Checkout> checkoutPage = checkoutService.pageSearch(current, size, query);

        Customer customer;
        User lister;
        for (Checkout checkout : checkoutPage.getRecords()) {
            customer = customerService.getById(checkout.getCustomerId());
            checkout.put("customerName", customer.getName());

            if (StrKit.notBlank(checkout.getListerId())) {
                lister = userService.getById(checkout.getListerId());
                checkout.put("listerName", lister.getName());
            }
        }

        data.put("checkoutPage", checkoutPage);
    }
}
