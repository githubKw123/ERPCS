package net.kingborn.erp.uc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.kingborn.erp.uc.model.Product;
import org.springframework.stereotype.Component;

/**
 * 商品Dao
 */
@Component
public interface ProductDao extends BaseMapper<Product> {
}
