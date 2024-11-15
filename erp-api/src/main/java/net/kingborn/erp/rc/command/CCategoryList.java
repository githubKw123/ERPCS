package net.kingborn.erp.rc.command;

import com.alibaba.fastjson.JSONObject;
import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.rc.model.Category;
import net.kingborn.erp.rc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 分类列表
 */
@Command
public class CCategoryList extends BaseCommand {

    @Autowired
    private CategoryService categoryService;

    @Param(required = true)
    private Integer type; // 类型
    @Param(defaultValue = "{}")
    private JSONObject query; // 查询参数

    @Override
    protected void init() throws Exception {
        Assert.notFalse(Define.validateCategoryType(type), "类别不正确！");
    }

    @Override
    protected void doCommand() throws Exception {
        List<Category> categoryList = categoryService.findListByType(type, query, null);

        // 如果是商品，那么级联获取子类别
        if (type == Define.CATEGORY_TYPE_PRODUCT) {
            findSubCategoryList(categoryList);
        }

        data.put("categoryList", categoryList);
    }

    /**
     * 获取子列表
     *
     * @param categoryList
     */
    private void findSubCategoryList(List<Category> categoryList) {
        if (categoryList == null || categoryList.size() == 0) {
            return;
        }

        for (Category category : categoryList) {
            List<Category> childList = categoryService.findListByType(category.getType(), query, category.getId());
            category.put("childList", childList);

            findSubCategoryList(childList);
        }
    }
}
