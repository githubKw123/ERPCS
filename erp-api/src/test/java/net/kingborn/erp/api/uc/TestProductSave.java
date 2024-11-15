package net.kingborn.erp.api.uc;

import net.kingborn.erp.api.TestUtil;
import net.kingborn.erp.uc.model.Product;
import net.kingborn.erp.uc.model.Warehouse;
import net.kingborn.test.BaseUrl;
import net.kingborn.test.TestCommand;
import org.junit.Test;

/**
 * 保存商品
 */
public class TestProductSave extends TestCommand {
    @Override
    public void init() throws Exception {
        headerMap = TestUtil.getAuthHeader();

        Product product = new Product();
        // product.setId("1298026350092500994");
        product.setCode("pingguo");
        product.setName("苹果");
        product.setSpec("超大");
        product.setCategoryId("1292435079284391938");
        product.setPrimaryWarehouseId("1295254961130692610");
        product.setUnitId("1292601852277293057");
        product.setRetailPrice(5.30);
        product.setWholesalePrice(3.8);
        product.setVipPrice(4.2);
        product.setEstimatedPurchasePrice(2.6);
        product.setRemark("保质期到9月30日");
        product.setMinimumStock(100.0);
        product.setMaximumStock(10000.0);

        postParamsMap.put("product", product);
    }

    @Test
    @Override
    public void doTest() {
        System.out.println(formatJson(doPost()));
    }

    @Override
    protected BaseUrl getBaseUrl() {
        return new BaseUrl("localhost", 9090, "/product/save");
    }
}
