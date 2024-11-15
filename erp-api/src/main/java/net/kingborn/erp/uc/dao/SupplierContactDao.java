package net.kingborn.erp.uc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.kingborn.erp.uc.model.CustomerContact;
import net.kingborn.erp.uc.model.SupplierContact;
import org.springframework.stereotype.Component;

/**
 * 供应商联系人Dao
 */
@Component
public interface SupplierContactDao extends BaseMapper<SupplierContact> {
}
