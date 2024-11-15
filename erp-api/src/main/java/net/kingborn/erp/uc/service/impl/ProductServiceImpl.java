package net.kingborn.erp.uc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.uc.dao.ProductDao;
import net.kingborn.erp.uc.model.Product;
import net.kingborn.erp.uc.service.ProductService;
import net.kingborn.erp.wc.model.IssueProduct;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品服务实现
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductDao, Product> implements ProductService {
    @Override
    public Page<Product> pageSearch(long current, long size, JSONObject query) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();

        if (StrKit.notBlank(query.getString("categoryId"))) {
            wrapper.eq("categoryId", query.getString("categoryId"));
        }
        if (StrKit.notBlank(query.getString("name"))) {
            wrapper.and(nameWrapper ->
                    nameWrapper.like("code", query.getString("name"))
                    .or()
                    .like("name", query.getString("name"))
                    .or()
                    .like("spec", query.getString("name")));
        }

        wrapper.orderByDesc("createdTime");

        return this.page(new Page<>(current, size), wrapper);
    }

    @Override
    public Product findByCode(String code) {
        return this.getOne(new QueryWrapper<Product>().eq("code", code));
    }

    @Override
    public List<Product> findListBycate(String categoryId) {
        return this.list(new QueryWrapper<Product>().eq("categoryId", categoryId));
    }
}
