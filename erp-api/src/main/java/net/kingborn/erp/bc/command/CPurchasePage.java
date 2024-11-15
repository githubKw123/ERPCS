package net.kingborn.erp.bc.command;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.core.exception.BizException;
import net.kingborn.core.log.Logger;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.bc.model.Purchase;
import net.kingborn.erp.bc.service.PurchaseService;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.uc.model.Supplier;
import net.kingborn.erp.uc.model.User;
import net.kingborn.erp.uc.service.SupplierService;
import net.kingborn.erp.uc.service.UserService;
import net.kingborn.erp.util.SimpleValidator;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 购货单分页列表
 */
@Command
public class CPurchasePage extends BaseCommand {
    private static Logger logger = Logger.getLogger(CPurchasePage.class);

    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private UserService userService;
    @Autowired
    private SupplierService supplierService;

    @Param(defaultValue = "{}")
    private JSONObject query; // 查询对象
    @Param(defaultValue = Define.CURRENT)
    private long current;
    @Param(defaultValue = Define.SIZE)
    private long size;

    @Override
    protected void init() throws Exception {
        String type = query.getString("type");
        if (!Define.validatePurchaseType(type)) {
            throw new BizException("类型不正确！");
        }

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
        Page<Purchase> purchasePage = purchaseService.pageSearch(current, size, query);

        Supplier supplier;
        User auditor;
        User lister;
        for (Purchase purchase : purchasePage.getRecords()) {
            supplier = supplierService.getById(purchase.getSupplierId());
            purchase.put("supplierName", supplier.getName());

//            if (StrKit.notBlank(purchase.getListerId())) {
//                lister = userService.getById(purchase.getListerId());
//                purchase.put("listerName", lister.getName());
//            }

            if (StrKit.notBlank(purchase.getAuditorId())) {
                auditor = userService.getById(purchase.getAuditorId());
                purchase.put("auditorName", auditor.getName());
            }
        }

        data.put("purchasePage", purchasePage);
    }
}
