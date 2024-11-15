package net.kingborn.erp.fc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.kingborn.erp.fc.dao.CollectionIssueDao;
import net.kingborn.erp.fc.model.CollectionIssue;
import net.kingborn.erp.fc.service.CollectionIssueService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionIssueServiceImpl extends ServiceImpl<CollectionIssueDao, CollectionIssue> implements CollectionIssueService {


    @Override
    public List<CollectionIssue> findListByCollection(String collectionId) {
        return this.list(new QueryWrapper<CollectionIssue>().eq("collectionId", collectionId));
    }

    @Override
    public void deleteByCollection(String collectionId) {
        this.remove(new QueryWrapper<CollectionIssue>().eq("collectionId", collectionId));
    }
}
