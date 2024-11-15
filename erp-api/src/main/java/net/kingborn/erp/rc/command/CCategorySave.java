package net.kingborn.erp.rc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.rc.model.Category;
import net.kingborn.erp.rc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 保存分类
 */
@Command
public class CCategorySave extends BaseCommand {

    @Autowired
    private CategoryService categoryService;

    @Param(required = true)
    private Category category;

    @Override
    protected void init() throws Exception {
        Assert.notFalse(Define.validateCategoryType(category.getType()), "类别不正确！");

        Assert.notBlank(category.getName(), "名称不能为空！");
    }

    @Override
    protected void doCommand() throws Exception {
        Category persistedCategory;
        if (category.getId() == null) {
            persistedCategory = new Category();
            persistedCategory.setType(category.getType());
        } else {
            persistedCategory = categoryService.getById(category.getId());
            Assert.notNull(persistedCategory, "ID为【" + category.getId() + "】的分类不存在！");
        }
        persistedCategory.setName(category.getName());

        if (category.getType() == Define.CATEGORY_TYPE_PRODUCT && StrKit.notBlank(category.getParentId())) {
            Category parent = categoryService.getById(category.getParentId());
            Assert.notNull(parent, "ID为【" + category.getParentId() + "】的分类不存在！");

            persistedCategory.setParentId(category.getParentId());
        }

        categoryService.saveOrUpdate(persistedCategory);

        data.put("category", persistedCategory);
    }
}
