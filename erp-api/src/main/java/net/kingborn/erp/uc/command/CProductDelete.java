package net.kingborn.erp.uc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.erp.uc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 商品删除
 */
@Command
public class CProductDelete extends BaseCommand {

    @Autowired
    private ProductService productService;

    @Param(required = true)
    private String productId;

    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        productService.removeById(productId);
    }
}
