package net.kingborn.erp.fc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.core.exception.BizException;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.fc.model.CollectionIssue;
import net.kingborn.erp.fc.model.AccountRecord;
import net.kingborn.erp.fc.service.CollectionIssueService;
import net.kingborn.erp.fc.service.AccountRecordService;
import net.kingborn.erp.fc.model.Collection;
import net.kingborn.erp.fc.service.CollectionService;
import net.kingborn.erp.fc.service.ReceivableService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 收款单保存
 */
@Command
public class CCollectionSave extends BaseCommand {

    @Autowired
    private CollectionService collectionService;
    @Autowired
    private AccountRecordService accountService;
    @Autowired
    private CollectionIssueService issueService;
    @Autowired
    private ReceivableService receivableService;

    @Param(required = true)
    private Collection collection;
    @Param(defaultValue = "[]")
    private List<CollectionIssue> issueList;
    @Param(defaultValue = "[]")
    private List<AccountRecord> accountList;

    private Collection persistedCollection;
    
    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        // 计算
        if (StrKit.isBlank(collection.getId())) {
            persistedCollection = new Collection();

            // 校验编码是否合法
            validateCode(collection.getCode());
            persistedCollection.setCode(collection.getCode());

        } else {
            persistedCollection = collectionService.getById(collection.getId());
            Assert.notNull(persistedCollection, "ID为【" + collection.getId() + "】的入库订单不存在！");

            // 删除原来的账户
            accountService.deleteByBusiness(collection.getId());

            // 删除关联的单据
            issueService.deleteByCollection(collection.getId());
        }

        persistedCollection.setCustomerId(collection.getCustomerId());
        persistedCollection.setIssueDate(collection.getIssueDate());
        persistedCollection.setCollectAmount(collection.getCollectAmount());
        persistedCollection.setIssueAmount(collection.getIssueAmount());
        persistedCollection.setDiscountAmount(collection.getDiscountAmount());
        persistedCollection.setVerifiedAmount(collection.getVerifiedAmount());
        persistedCollection.setUnverifiedAmount(collection.getUnverifiedAmount());
        persistedCollection.setCurrentVerifiedAmount(collection.getCurrentVerifiedAmount());
        persistedCollection.setAdvanceCollectAmount(collection.getAdvanceCollectAmount());
        persistedCollection.setListerId(collection.getListerId());
        persistedCollection.setRemark(collection.getRemark());
        collectionService.saveOrUpdate(persistedCollection);

        // 新增账户
        accountService.addRecordList(accountList, Define.ACCOUNT_RECORD_TYPE_IN, persistedCollection.getIssueDate(), Define.BUSINESS_TYPE_COLLECTION, persistedCollection.getId());

        // 新增单据
        addIssueList();

        // 处理应收账款
        handleReceivable();

        data.put("collection", persistedCollection);
    }

    /**
     * 处理应收账款
     */
    private void handleReceivable() {
        if (StrKit.notBlank(collection.getId())) {
            // 删除原来的应收账款记录
            receivableService.deleteByBusiness(collection.getId());
        }

        receivableService.businessAdd(persistedCollection.getCustomerId(), persistedCollection.getIssueDate(),
                Define.BUSINESS_TYPE_COLLECTION, persistedCollection.getId(), 0, persistedCollection.getAdvanceCollectAmount());
    }

    /**
     * 校验单据编号是否合法
     *
     * @param code
     */
    private void validateCode(String code) {
        Collection Collection = collectionService.findByCode(code);
        if (Collection != null) {
            throw new BizException("单据编号为【" + code + "】的入库单已经存在！");
        }
    }

    /**
     * 新增商品列表
     */
    private void addIssueList() {
        if (issueList == null || issueList.size() == 0) return;

        List<CollectionIssue> persistedIssueList = new ArrayList<>();
        CollectionIssue persistedIssue;
        for (CollectionIssue issue : issueList) {
            persistedIssue = new CollectionIssue();
            persistedIssue.setCollectionId(persistedCollection.getId());
            persistedIssue.setSourceCode(issue.getSourceCode());
            persistedIssue.setType(issue.getType());
            persistedIssue.setIssueDate(issue.getIssueDate());
            persistedIssue.setIssueAmount(issue.getIssueAmount());
            persistedIssue.setVerifiedAmount(issue.getVerifiedAmount());
            persistedIssue.setUnverifiedAmount(issue.getUnverifiedAmount());
            persistedIssue.setCurrentVerifiedAmount(issue.getCurrentVerifiedAmount());

            persistedIssueList.add(persistedIssue);
        }

        issueService.saveBatch(persistedIssueList);
    }
}
