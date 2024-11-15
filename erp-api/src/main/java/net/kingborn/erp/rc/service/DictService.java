package net.kingborn.erp.rc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.kingborn.erp.rc.model.Dict;

/**
 * 字典服务
 */
public interface DictService extends IService<Dict> {

    /**
     * 根据编码获取对象
     *
     * @param code
     * @return
     */
    Dict findByCode(String code);

}
