package net.kingborn.erp.wc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.kingborn.erp.wc.model.TransferProduct;

import java.util.List;

/**
 * 调拨商品服务
 */
public interface TransferProductService extends IService<TransferProduct> {
    /**
     * 获取调拨订单商品列表
     *
     * @param transferId
     * @return
     */
    List<TransferProduct> findListByTransfer(String transferId);

    /**
     * 根据调拨订单删除
     *
     * @param transferId
     */
    void deleteByTransfer(String transferId);
}
