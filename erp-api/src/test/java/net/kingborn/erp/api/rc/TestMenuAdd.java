package net.kingborn.erp.api.rc;

import net.kingborn.erp.rc.model.Menu;
import net.kingborn.test.TestCommand;
import net.kingborn.test.BaseUrl;
import org.junit.Test;

public class TestMenuAdd extends TestCommand {

    @Override
    public void init() throws Exception {
        Menu menu = new Menu();
        menu.setIcon("test");
        menu.setParentId("10");

        postParamsMap.put("menu", menu);
    }

    @Test
    @Override
    public void doTest() {
        System.out.println(formatJson(doPost()));
    }

    @Override
    protected BaseUrl getBaseUrl() {
        return new BaseUrl("localhost", 9090, "/menu/add");
    }
}
