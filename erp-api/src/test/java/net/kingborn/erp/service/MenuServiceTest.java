package net.kingborn.erp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.kingborn.core.util.JsonKit;
import net.kingborn.erp.rc.model.Menu;
import net.kingborn.erp.rc.service.MenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 测试菜单
 */
@SpringBootTest
public class MenuServiceTest {

    @Autowired
    private MenuService menuService;

    /**
     * 新增
     */
    @Test
    public void testAdd() {
        Menu menu = new Menu();
        menu.setParentId(null);
        menu.setIcon("icon-user");
        menu.setTitle("用户管理");
        menu.setPath("/user");
        menu.setSortNumber(1);

        menuService.save(menu);

        System.out.println(menu);
    }

    @Test
    public void testPaginate() {
        Page<Menu> menuPage = menuService.page(new Page<>(1, 2));

        for (Menu menu : menuPage.getRecords()) {
            menu.put("test", 1);
        }
        System.out.println(JsonKit.toJson(menuPage));
    }

}
