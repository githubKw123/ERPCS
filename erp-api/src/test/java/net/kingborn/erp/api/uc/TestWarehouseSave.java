package net.kingborn.erp.api.uc;

import net.kingborn.erp.api.TestUtil;
import net.kingborn.erp.uc.model.Employee;
import net.kingborn.erp.uc.model.Warehouse;
import net.kingborn.test.BaseUrl;
import net.kingborn.test.TestCommand;
import org.junit.Test;

/**
 * 保存仓库
 */
public class TestWarehouseSave extends TestCommand {
    @Override
    public void init() throws Exception {
        headerMap = TestUtil.getAuthHeader();

        Warehouse warehouse = new Warehouse();
        warehouse.setCode("taijiang");
        warehouse.setName("台江");

        postParamsMap.put("warehouse", warehouse);
    }

    @Test
    @Override
    public void doTest() {
        System.out.println(formatJson(doPost()));
    }

    @Override
    protected BaseUrl getBaseUrl() {
        return new BaseUrl("localhost", 9090, "/warehouse/save");
    }
}
