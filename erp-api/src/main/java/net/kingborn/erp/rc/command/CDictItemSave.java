package net.kingborn.erp.rc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.core.exception.BizException;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.constant.Const;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.rc.model.Dict;
import net.kingborn.erp.rc.model.DictItem;
import net.kingborn.erp.rc.service.DictItemService;
import net.kingborn.erp.rc.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 更新字典项
 */
@Command
public class CDictItemSave extends BaseCommand {

    @Autowired
    private DictService dictService;
    @Autowired
    private DictItemService itemService;

    @Param(required = true)
    private DictItem item;

    @Override
    protected void init() throws Exception {
        Assert.notBlank(item.getName(), "名称不能为空！");
        Assert.notBlank(item.getDictCode(), "字典编码不能为空！");

        System.out.println(Const.DEV_MODE);
        if (!Const.DEV_MODE && (!item.getDictCode().equals(Define.DICT_CODE_UNIT) && !item.getDictCode().equals(Define.DICT_CODE_SETTLEMENT))) {
            throw new BizException("没有权限！");
        }
    }

    @Override
    protected void doCommand() throws Exception {
        Dict dict = dictService.findByCode(item.getDictCode());
        Assert.notNull(dict, "编码为【" + item.getDictCode() + "】的字典不存在！");

        DictItem persistedItem;
        if (StrKit.isBlank(item.getId())) {
            persistedItem = new DictItem();
            persistedItem.setDictCode(item.getDictCode());
            persistedItem.setSortNumber(0);

        } else {
            persistedItem = itemService.getById(item.getId());
            Assert.notNull(persistedItem, "ID为【" + item.getId() + "】的字典项不存在！");
        }
        persistedItem.setName(item.getName());

        itemService.saveOrUpdate(persistedItem);

        data.put("item", persistedItem);
    }
}
