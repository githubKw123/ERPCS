package net.kingborn.erp.rc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.kingborn.erp.rc.bean.SystemConfiguration;
import net.kingborn.erp.rc.model.KeyValue;

/**
 * 键值对服务
 */
public interface KeyValueService extends IService<KeyValue> {

    /**
     * 根据键和类型获取对象
     *
     * @param key
     * @param code
     * @return
     */
    KeyValue findByKeyAndCode(String key, String code);

    /**
     * 根据键删除
     *
     * @param key
     */
    void deleteByKey(String key);

    /**
     * 获取系统配置
     *
     * @return
     */
    SystemConfiguration getSystemConfiguration();

    /**
     * 设置系统设置
     *
     * @param configuration
     */
    void setSystemConfiguration(SystemConfiguration configuration);

}
