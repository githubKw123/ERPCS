package net.kingborn.erp.rc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.kingborn.core.exception.Assert;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.constant.KeyValueCode;
import net.kingborn.erp.rc.bean.SystemConfiguration;
import net.kingborn.erp.rc.dao.KeyValueDao;
import net.kingborn.erp.rc.model.KeyValue;
import net.kingborn.erp.rc.service.KeyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 键值对服务实现
 */
@Service
public class KeyValueServiceImpl extends ServiceImpl<KeyValueDao, KeyValue> implements KeyValueService {

    @Autowired
    private KeyValueDao kvDao;

    @Override
    public KeyValue findByKeyAndCode(String key, String code) {
        QueryWrapper<KeyValue> wrapper = new QueryWrapper<>();
        wrapper.eq("`key`", key);
        wrapper.eq("code", code);

        return kvDao.selectOne(wrapper);
    }

    @Override
    public void deleteByKey(String key) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("`key`", key);

        kvDao.delete(wrapper);
    }

    @Override
    public SystemConfiguration getSystemConfiguration() {
        KeyValue kv = findByKeyAndCode(Define.SYSTEM_CONFIGURATION_KEY, KeyValueCode.SETTINGS);
        Assert.notNull(kv, "系统设置不存在！");

        return JSONObject.parseObject(kv.getValue(), SystemConfiguration.class);
    }

    @Override
    public void setSystemConfiguration(SystemConfiguration configuration) {
        KeyValue kv = findByKeyAndCode(Define.SYSTEM_CONFIGURATION_KEY, KeyValueCode.SETTINGS);
        Assert.notNull(kv, "系统设置不存在！");

        kv.setValue(JSONObject.toJSONString(configuration));

        saveOrUpdate(kv);
    }
}
