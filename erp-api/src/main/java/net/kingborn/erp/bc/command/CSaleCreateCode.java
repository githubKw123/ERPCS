package net.kingborn.erp.bc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.BizException;
import net.kingborn.erp.constant.Define;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 销售单创建编码
 */
@Command
public class CSaleCreateCode extends BaseCommand {

    private static SimpleDateFormat codeFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    private static Random random = new Random();

    @Param(required = true)
    private String type;

    @Override
    protected void init() throws Exception {
        if (!Define.validateSaleType(type)) {
            throw new BizException("销售类型不正确！");
        }
    }

    @Override
    protected void doCommand() throws Exception {
        String code;
        if (Define.BUSINESS_TYPE_SALE_SELL.equals(type)) {
            code = "SE" + codeFormatter.format(new Date()) + random.nextInt(10) + random.nextInt(10);

        } else {
            code = "RE" + codeFormatter.format(new Date()) + random.nextInt(10) + random.nextInt(10);
        }

        data.put("code", code);
    }
}
