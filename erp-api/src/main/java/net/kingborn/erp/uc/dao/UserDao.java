package net.kingborn.erp.uc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.kingborn.erp.uc.model.User;
import org.springframework.stereotype.Component;

/**
 * 用户Dao
 */
@Component
public interface UserDao extends BaseMapper<User> {
}
