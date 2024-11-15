package net.kingborn.erp.rc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.erp.rc.bean.SystemConfiguration;
import net.kingborn.erp.rc.service.KeyValueService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 设置系统设置
 */
@Command
public class CSetSystemConfiguration extends BaseCommand {

    @Autowired
    private KeyValueService kvService;

    @Param(name = "configuration", required = true)
    private SystemConfiguration configuration; // 配置对象

    @Override
    protected void init() throws Exception {
        Assert.notBlank(configuration.getCompanyName(), "公司名称不能为空！");
        Assert.notBlank(configuration.getCompanyAddress(), "公司地址不能为空！");
    }

    @Override
    protected void doCommand() throws Exception {
        SystemConfiguration persistedConfiguration = kvService.getSystemConfiguration();

        persistedConfiguration.setCompanyName(configuration.getCompanyName());
        persistedConfiguration.setCompanyAddress(configuration.getCompanyAddress());
        persistedConfiguration.setCompanyPhone(configuration.getCompanyPhone());
        persistedConfiguration.setCompanyFax(configuration.getCompanyFax());
        persistedConfiguration.setCompanyPostCode(configuration.getCompanyPostCode());
        persistedConfiguration.setQuantityPrecision(configuration.getQuantityPrecision());
        persistedConfiguration.setPricePrecision(configuration.getPricePrecision());
        persistedConfiguration.setCheckNegativeStock(configuration.isCheckNegativeStock());

        kvService.setSystemConfiguration(persistedConfiguration);

        data.put("configuration", persistedConfiguration);
    }
}
