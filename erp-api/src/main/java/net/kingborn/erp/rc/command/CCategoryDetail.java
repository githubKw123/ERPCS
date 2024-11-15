package net.kingborn.erp.rc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.erp.rc.model.Category;
import net.kingborn.erp.rc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 根据ID获取分类
 */
@Command
public class CCategoryDetail extends BaseCommand {

    @Autowired
    private CategoryService categoryService;

    @Param(required = true)
    private String categoryId;

    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        Category category = categoryService.getById(categoryId);
        Assert.notNull(category, "ID为【" + categoryId + "】的分类不存在！");

        data.put("category", category);
    }
}
