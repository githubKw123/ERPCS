package net.kingborn.erp.uc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.erp.uc.model.Product;
import net.kingborn.erp.uc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 商品启停
 */
@Command
public class CProductSwitchActive extends BaseCommand {

    @Autowired
    private ProductService productService;

    @Param(required = true)
    private String productId;

    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        Product product = productService.getById(productId);
        Assert.notNull(product, "ID为【" + productId + "】的商品不存在！");

        product.setActive(!product.isActive());
        productService.saveOrUpdate(product);

        data.put("product", product);
    }
}
