package net.kingborn.erp.rc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.core.exception.BizException;
import net.kingborn.erp.rc.model.Category;
import net.kingborn.erp.rc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 删除类别
 */
@Command
public class CCategoryDelete extends BaseCommand {

    @Autowired
    private CategoryService categoryService;

    @Param(required = true)
    private String categoryId; // 分类ID

    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        Category category = categoryService.getById(categoryId);
        Assert.notNull(category, "ID为【" + category + "】的分类不存在！");

        List<Category> childList = categoryService.findListByType(category.getType(), null, category.getId());
        if (childList != null && childList.size() > 0) {
            throw new BizException("请先删除子分类！");
        }

        categoryService.removeById(categoryId);
    }
}
