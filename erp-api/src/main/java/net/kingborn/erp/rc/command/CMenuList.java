package net.kingborn.erp.rc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.util.JsonKit;
import net.kingborn.erp.interceptor.CurrentUser;
import net.kingborn.erp.rc.model.Menu;
import net.kingborn.erp.rc.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Command
public class CMenuList extends BaseCommand {

    @Autowired
    private MenuService menuService;

    private @Param CurrentUser currentUser;

    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        System.out.println("currentUser: " + JsonKit.toJson(currentUser));

        List<Menu> menuList = menuService.findListByParentId(null);
        for (Menu menu : menuList) {
            List<Menu> childList = menuService.findListByParentId(menu.getId());
            if (childList != null && childList.size() > 0) {
                menu.put("childList", childList);
            }
        }

        data.put("menuList", menuList);
    }
}
