package net.kingborn.erp.rc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.rc.dao.MenuDao;
import net.kingborn.erp.rc.model.Menu;
import net.kingborn.erp.rc.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu> implements MenuService {

    @Autowired
    private MenuDao menuDao;

    @Override
    public List<Menu> findListByParentId(String parentId) {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        if (StrKit.isBlank(parentId)) {
            wrapper.isNull("parentId");
        } else {
            wrapper.eq("parentId", parentId);
        }

        wrapper.orderByAsc("sortNumber");

        return menuDao.selectList(wrapper);
    }
}
