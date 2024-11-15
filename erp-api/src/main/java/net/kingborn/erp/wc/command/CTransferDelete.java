package net.kingborn.erp.wc.command;

import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.exception.Assert;
import net.kingborn.erp.wc.model.Transfer;
import net.kingborn.erp.wc.service.TransferProductService;
import net.kingborn.erp.wc.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 调拨单删除
 */
@Command
public class CTransferDelete extends BaseCommand {

    @Autowired
    private TransferService transferService;
    @Autowired
    private TransferProductService productService;

    @Param(required = true)
    private String transferId;
    
    @Override
    protected void init() throws Exception {

    }

    @Override
    protected void doCommand() throws Exception {
        Transfer transfer = transferService.getById(transferId);
        Assert.notNull(transfer, "ID为【" + transferId + "】的调拨订单不存在！");

        transferService.removeById(transferId);

        productService.deleteByTransfer(transferId);
    }
}
