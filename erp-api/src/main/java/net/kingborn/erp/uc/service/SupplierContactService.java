package net.kingborn.erp.uc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.kingborn.erp.uc.model.CustomerContact;
import net.kingborn.erp.uc.model.SupplierContact;

import java.util.List;

/**
 * 供应商联系人服务
 */
public interface SupplierContactService extends IService<SupplierContact> {
    /**
     * 根据供应商删除
     * @param supplierId
     */
    void deleteBySupplier(String supplierId);

    /**
     * 根据客户获取联系人列表
     *
     * @param supplierId
     * @return
     */
    List<SupplierContact> findListBySupplier(String supplierId);
}
