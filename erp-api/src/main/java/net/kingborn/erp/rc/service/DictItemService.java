package net.kingborn.erp.rc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.kingborn.erp.rc.model.DictItem;

import java.util.List;

/**
 * 字典项服务
 */
public interface DictItemService extends IService<DictItem> {

    /**
     * 根据字典编码获取列表
     * @param dictCode
     * @return
     */
    List<DictItem> findListByCode(String dictCode);

    /**
     * 验证字典项是否在字典中
     *
     * @param id
     * @param dictCode
     * @return
     */
    boolean validate(String id, String dictCode);
}
