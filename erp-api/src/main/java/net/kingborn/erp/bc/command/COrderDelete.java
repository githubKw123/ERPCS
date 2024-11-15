package net.kingborn.erp.bc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.erp.bc.model.Order;
import net.kingborn.erp.bc.service.OrderService;
import net.kingborn.erp.wc.service.IssueProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 客户订单删除
 */
@Command
@Transactional
public class COrderDelete extends BaseCommand {

    @Autowired
    private OrderService orderService;
    @Autowired
    private IssueProductService productService;

    @Param(required = true)
    private String orderId;

    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        Order order = orderService.getById(orderId);
        Assert.notNull(order, "ID为【" + orderId + "】的客户订单不存在！");

        orderService.removeById(orderId);

        productService.deleteByBusiness(orderId);
    }
}
