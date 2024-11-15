package net.kingborn.erp.fc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.kingborn.erp.fc.model.Payable;
import net.kingborn.erp.fc.model.Receivable;
import org.springframework.stereotype.Component;

/**
 * 应付账款Dao
 */
@Component
public interface PayableDao extends BaseMapper<Payable> {
}
