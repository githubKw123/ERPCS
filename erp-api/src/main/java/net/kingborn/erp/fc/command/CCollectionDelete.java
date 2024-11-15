package net.kingborn.erp.fc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.erp.fc.model.Collection;
import net.kingborn.erp.fc.service.CollectionIssueService;
import net.kingborn.erp.fc.service.CollectionService;
import net.kingborn.erp.fc.service.AccountRecordService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 收款单删除
 */
@Command
public class CCollectionDelete extends BaseCommand {

    @Autowired
    private CollectionService collectionService;
    @Autowired
    private CollectionIssueService issueService;
    @Autowired
    private AccountRecordService accountService;

    @Param(required = true)
    private String collectionId;
    
    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        Collection Collection = collectionService.getById(collectionId);
        Assert.notNull(Collection, "ID为【" + collectionId + "】的收款订单不存在！");

        collectionService.removeById(collectionId);

        issueService.deleteByCollection(collectionId);
        accountService.deleteByBusiness(collectionId);
    }
}
