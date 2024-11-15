package net.kingborn.erp.bc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.erp.bc.model.Order;
import net.kingborn.erp.bc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 客户订单切换审查
 */
@Command
public class COrderSwitchCheck extends BaseCommand {

    @Autowired
    private OrderService orderService;

    @Param(required = true)
    private String orderId;

    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        Order order = orderService.getById(orderId);
        Assert.notNull(order, "ID为【" + orderId + "】的客户订单不存在！");

        order.setChecked(!order.isChecked());
        orderService.saveOrUpdate(order);

        data.put("order", order);
    }
}
