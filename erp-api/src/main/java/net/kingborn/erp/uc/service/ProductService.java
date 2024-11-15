package net.kingborn.erp.uc.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import net.kingborn.erp.uc.model.Product;
import net.kingborn.erp.wc.model.IssueProduct;

import java.util.List;

/**
 * 商品服务
 */
public interface ProductService extends IService<Product> {

    /**
     * 分页查询
     *
     * @param current
     * @param size
     * @param query
     * @return
     */
    Page<Product> pageSearch(long current, long size, JSONObject query);

    /**
     * 根据编码获取对象
     *
     * @param code
     * @return
     */
    Product findByCode(String code);

    /**
     * 获取单据订单商品列表
     *
     * @param categoryId
     * @return
     */
    List<Product> findListBycate(String categoryId);
}
