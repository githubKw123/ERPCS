package net.kingborn.erp.uc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.erp.rc.model.Category;
import net.kingborn.erp.rc.model.DictItem;
import net.kingborn.erp.rc.service.CategoryService;
import net.kingborn.erp.rc.service.DictItemService;
import net.kingborn.erp.uc.model.Product;
import net.kingborn.erp.uc.model.Supplier;
import net.kingborn.erp.uc.model.Warehouse;
import net.kingborn.erp.uc.service.ProductService;
import net.kingborn.erp.wc.model.IssueProduct;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Command
public class CProductFindbyid extends BaseCommand {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DictItemService dictItemService;

    @Param(required = true)
    private String categoryId; // 购货ID

    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        Category category = categoryService.getById(categoryId);
        Assert.notNull(category, "ID为【" + categoryId+ "】的商品不存在！");

        List<Product> productList = productService.findListBycate(category.getId());
        for (Product Product : productList) {


            DictItem dictItem = dictItemService.getById(Product.getUnitId());
            Assert.notNull(dictItem, "ID为【" + Product.getUnitId() + "】的单位不存在！");
            Product.put("unitName", dictItem.getName());

        }
        data.put("productList", productList);
    }
}
