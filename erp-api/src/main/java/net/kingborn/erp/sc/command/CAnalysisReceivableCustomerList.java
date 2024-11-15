package net.kingborn.erp.sc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.bc.model.Sale;
import net.kingborn.erp.bc.service.SaleService;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.fc.model.Collection;
import net.kingborn.erp.fc.model.Receivable;
import net.kingborn.erp.fc.service.CollectionService;
import net.kingborn.erp.fc.service.ReceivableService;
import net.kingborn.erp.uc.model.Customer;
import net.kingborn.erp.uc.service.CustomerService;
import net.kingborn.erp.util.SimpleValidator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 客户对账单
 */
@Command
public class CAnalysisReceivableCustomerList extends BaseCommand {

    @Autowired
    private ReceivableService receivableService;
    @Autowired
    private SaleService saleService;
    @Autowired
    private CollectionService collectionService;

    /** 开始时间 */
    private @Param
    String startDate;
    /** 结束时间 */
    private @Param String endDate;
    /** 客户ID列表 */
    private @Param(required = true)
    String customerId;

    @Override
    protected void init() throws Exception {
        if (StrKit.notBlank(startDate)) {
            Assert.notFalse(SimpleValidator.validateDate(startDate), "起始时间不正确！");
        }
        if (StrKit.notBlank(endDate)) {
            Assert.notFalse(SimpleValidator.validateDate(endDate), "结束时间不正确！");
        }
    }

    @Override
    protected void doCommand() throws Exception {
        List<Receivable> receivableList = receivableService.listByCustomer(startDate, endDate, customerId);
        for (Receivable receivable : receivableList) {
            if (receivable.getBusinessType().equals(Define.BUSINESS_TYPE_SALE_SELL)
                    || receivable.getBusinessType().equals(Define.BUSINESS_TYPE_SALE_RETURNED)) {
                Sale sale = saleService.getById(receivable.getBusinessId());
                Assert.notNull(sale, "ID为【" + receivable.getBusinessId() + "】的销货订单不存在！");

                receivable.put("issueCode", sale.getCode());
                receivable.put("businessTypeName", receivable.getBusinessType().equals(Define.BUSINESS_TYPE_SALE_SELL) ?
                        "销货" : "销退");
                receivable.put("amount", sale.getAmount());
                receivable.put("discountAmount", sale.getDiscountAmount());
                receivable.put("preferredAmount", sale.getPreferredAmount());
                receivable.put("actualAmount", sale.getCurrentAmount());

            } else if (receivable.getBusinessType().equals(Define.BUSINESS_TYPE_COLLECTION)) {
                Collection collection = collectionService.getById(receivable.getBusinessId());
                Assert.notNull(collection, "ID为【" + receivable.getBusinessId() + "】的收款单不存在！");

                receivable.put("issueCode", collection.getCode());
                receivable.put("businessTypeName", "收款");
                receivable.put("actualAmount", collection.getCollectAmount());
            }
        }

        data.put("receivableList", receivableList);
    }
}
