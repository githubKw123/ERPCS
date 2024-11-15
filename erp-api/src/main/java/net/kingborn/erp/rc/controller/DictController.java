package net.kingborn.erp.rc.controller;

import net.kingborn.core.controller.BaseController;
import net.kingborn.core.controller.Controller;
import net.kingborn.core.entry.Result;
import net.kingborn.erp.rc.command.CDictItemDelete;
import net.kingborn.erp.rc.command.CDictItemDetail;
import net.kingborn.erp.rc.command.CDictItemList;
import net.kingborn.erp.rc.command.CDictItemSave;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 字典控制器
 */
@Controller("/dict")
public class DictController extends BaseController {

    /**
     * 获取字典项列表
     */
    @PostMapping("/itemList")
    public Result itemList() {
        return doAction(CDictItemList.class);
    }

    /**
     * 字典项详情
     */
    @PostMapping("/itemDetail")
    public Result itemDetail() {
        return doAction(CDictItemDetail.class);
    }

    /**
     * 保存字典项
     */
    @PostMapping("itemSave")
    public Result itemSave() {
        return doAction(CDictItemSave.class);
    }

    /**
     * 删除字典项
     */
    @PostMapping("itemDelete")
    public Result itemDelete() {
        return doAction(CDictItemDelete.class);
    }

}
