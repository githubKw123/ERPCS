package net.kingborn.erp.rc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.rc.dao.CategoryDao;
import net.kingborn.erp.rc.model.Category;
import net.kingborn.erp.rc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分类服务实现
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<Category> findListByType(int type, JSONObject query, String parentId) {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("type", type);
        if (StrKit.isBlank(parentId)) {
            wrapper.isNull("parentId");
        } else {
            wrapper.eq("parentId", parentId);
        }

        if (query != null && StrKit.notBlank(query.getString("name"))) {
            wrapper.like("name", "%" + query.getString("name") + "%");
        }

        wrapper.orderByAsc("sortNumber", "createdTime");

        return categoryDao.selectList(wrapper);
    }
}
