package net.kingborn.erp.bc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 客户订单创建新的编号
 */
@Command
public class COrderCreateCode extends BaseCommand {

    private static SimpleDateFormat codeFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    private static Random random = new Random();

    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        String code = "CO" + codeFormatter.format(new Date()) + random.nextInt(10) + random.nextInt(10);

        data.put("code", code);
    }
}
