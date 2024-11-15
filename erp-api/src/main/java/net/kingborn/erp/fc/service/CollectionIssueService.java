package net.kingborn.erp.fc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.kingborn.erp.fc.model.CollectionIssue;

import java.util.List;

/**
 * 收款单据服务
 */
public interface CollectionIssueService extends IService<CollectionIssue> {
    /**
     * 获取收款单据列表
     *
     * @param collectionId
     * @return
     */
    List<CollectionIssue> findListByCollection(String collectionId);

    /**
     * 根据收款单据删除
     *
     * @param collectionId
     */
    void deleteByCollection(String collectionId);
}
