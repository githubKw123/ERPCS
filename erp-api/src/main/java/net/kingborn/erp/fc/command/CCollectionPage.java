package net.kingborn.erp.fc.command;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.uc.model.Customer;
import net.kingborn.erp.uc.model.User;
import net.kingborn.erp.uc.service.CustomerService;
import net.kingborn.erp.uc.service.UserService;
import net.kingborn.erp.util.SimpleValidator;
import net.kingborn.erp.fc.model.Collection;
import net.kingborn.erp.fc.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 收款单分页列表
 */
@Command
public class CCollectionPage extends BaseCommand {

    @Autowired
    private CollectionService collectionService;
    @Autowired
    private UserService userService;
    @Autowired
    private CustomerService customerService;

    @Param(defaultValue = "{}")
    private JSONObject query; // 查询对象
    @Param(defaultValue = Define.CURRENT)
    private long current;
    @Param(defaultValue = Define.SIZE)
    private long size;

    @Override
    protected void init() throws Exception {
        String startDate = query.getString("startDate");
        if (StrKit.notBlank(startDate)) {
            Assert.notFalse(SimpleValidator.validateDate(startDate), "起始时间不正确！");
        }

        String endDate = query.getString("endDate");
        if (StrKit.notBlank(endDate)) {
            Assert.notFalse(SimpleValidator.validateDate(endDate), "结束时间不正确！");
        }
    }

    @Override
    protected void doCommand() throws Exception {
        Page<Collection> collectionPage = collectionService.pageSearch(current, size, query);

        Customer customer;
        User lister;
        for (Collection collection : collectionPage.getRecords()) {
            customer = customerService.getById(collection.getCustomerId());
            collection.put("customerName", customer.getName());

            if (StrKit.notBlank(collection.getListerId())) {
                lister = userService.getById(collection.getListerId());
                collection.put("listerName", lister.getName());
            }
        }

        data.put("collectionPage", collectionPage);
    }
}
