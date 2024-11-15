package net.kingborn.erp.api.bc;

import net.kingborn.erp.api.TestUtil;
import net.kingborn.erp.bc.model.*;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.wc.model.IssueProduct;
import net.kingborn.test.BaseUrl;
import net.kingborn.test.TestCommand;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 保存客户订单
 */
public class TestOrderSave extends TestCommand {

    @Override
    public void init() throws Exception {
        headerMap = TestUtil.getAuthHeader();

        Order order = new Order();
        order.setCode("CO2021042106410645359");
        order.setIssueDate("2021-04-21");
        order.setDeliveryDate("2021-05-03");
        order.setBusinessType(Define.ORDER_BUSINESS_TYPE_ORDER);
        order.setCustomerId("1295569375012671489");
        order.setTotalAmount(1000.0);
        order.setDiscountedAmount(800.0);
        order.setQuantity(11.5);
        order.setDiscountRate(20.0);
        order.setListerId("1289333755057299456");
        postParamsMap.put("order", order);

        List<IssueProduct> productList = new ArrayList<>();
        IssueProduct product = new IssueProduct();
        product.setProductId("1298462990858530818");
        product.setWarehouseId("1295254961130692610");
        product.setQuantity(1.5);
        product.setPrice(110.2);
        product.setDiscountRate(20.0);
        product.setDiscountAmount(113.0);
        product.setAmount(1000.0);
        product.setRemark("很好的产品！");
        productList.add(product);
        postParamsMap.put("productList", productList);
    }

    @Test
    @Override
    public void doTest() {
        System.out.println(formatJson(doPost()));
    }

    @Override
    protected BaseUrl getBaseUrl() {
        return new BaseUrl("localhost", 9090, "/order/save");
    }

}
