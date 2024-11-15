package net.kingborn.erp.rc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.erp.rc.model.DictItem;
import net.kingborn.erp.rc.service.DictItemService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 删除字典项
 */
@Command
public class CDictItemDelete extends BaseCommand {

    @Autowired
    private DictItemService itemService;

    @Param(required = true)
    private String itemId;

    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        DictItem item = itemService.getById(itemId);
        Assert.notNull(item, "ID为【" + itemId + "】的字典项不存在！");

        itemService.removeById(itemId);
    }
}
