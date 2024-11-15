package net.kingborn.erp.fc.controller;

import net.kingborn.core.controller.BaseController;
import net.kingborn.core.controller.Controller;
import net.kingborn.core.entry.Result;
import net.kingborn.erp.fc.command.*;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 收款控制器
 */
@Controller("/collection")
public class CollectionController extends BaseController {

    /**
     * 分页
     */
    @PostMapping("/page")
    public Result page() {
        return doAction(CCollectionPage.class);
    }
    
    /**
     * 入库单详情
     */
    @PostMapping("/detail")
    public Result detail() {
        return doAction(CCollectionDetail.class);
    }

    /**
     * 创建一个新的单据编号
     */
    @PostMapping("/createCode")
    public Result createCode() {
        return doAction(CCollectionCreateCode.class);
    }

    /**
     * 入库单保存
     */
    @PostMapping("/save")
    public Result save() {
        return doAction(CCollectionSave.class);
    }

    /**
     * 入库单删除
     */
    @PostMapping("/delete")
    public Result delete() {
        return doAction(CCollectionDelete.class);
    }

}
