package net.kingborn.erp.rc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.erp.rc.bean.SystemConfiguration;
import net.kingborn.erp.rc.service.KeyValueService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 获取系统设置
 */
@Command
public class CGetSystemConfiguration extends BaseCommand {

    @Autowired
    private KeyValueService kvService;

    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        SystemConfiguration configuration = kvService.getSystemConfiguration();

        data.put("configuration", configuration);
    }
}
