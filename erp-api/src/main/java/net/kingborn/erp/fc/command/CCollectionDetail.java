package net.kingborn.erp.fc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.erp.fc.model.CollectionIssue;
import net.kingborn.erp.fc.model.AccountRecord;
import net.kingborn.erp.fc.service.CollectionIssueService;
import net.kingborn.erp.fc.model.Collection;
import net.kingborn.erp.fc.service.CollectionService;
import net.kingborn.erp.fc.service.AccountRecordService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 收款单详情
 */
@Command
public class CCollectionDetail extends BaseCommand {

    @Autowired
    private CollectionService collectionService;
    @Autowired
    private AccountRecordService accountService;
    @Autowired
    private CollectionIssueService issueService;

    @Param(required = true)
    private String collectionId; // 收款订单ID
    
    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        Collection collection = collectionService.getById(collectionId);
        Assert.notNull(collection, "ID为【" + collectionId + "】的收款订单不存在！");

        List<AccountRecord> accountList = accountService.findListByBusiness(collection.getId());
        collection.put("accountList", accountList);

        List<CollectionIssue> issueList = issueService.findListByCollection(collection.getId());
        collection.put("issueList", issueList);

        data.put("collection", collection);
    }
}
