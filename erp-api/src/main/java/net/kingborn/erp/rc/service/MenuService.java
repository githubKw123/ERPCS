package net.kingborn.erp.rc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.kingborn.erp.rc.model.Menu;

import java.util.List;

/**
 * 菜单服务
 */
public interface MenuService extends IService<Menu> {

    /**
     * 通过parentId获取列表
     *
     * @param parentId
     * @return
     */
    List<Menu> findListByParentId(String parentId);
}
