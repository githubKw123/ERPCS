package net.kingborn.erp.rc.service.impl;

import net.kingborn.core.tools.Convert;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.constant.KeyValueCode;
import net.kingborn.erp.rc.model.KeyValue;
import net.kingborn.erp.rc.service.KeyValueService;
import net.kingborn.erp.rc.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 令牌服务
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private KeyValueService kvService;

    /**
     * 保存
     *
     * @param code
     * @param seconds
     * @param keys
     * @return
     */
    @Override
    public KeyValue save(String code, int seconds, String... keys) {
        if (createKey(keys) == null || seconds <= 0 || code == null) {
            return null;
        }

        // 删除相同的key
        kvService.deleteByKey(createKey(keys));

        KeyValue kv = new KeyValue();
        kv.setCode(KeyValueCode.TOKEN);
        kv.setKey(createKey(keys));
        kv.setValue(code);
        kv.setReservedInt((int) (Convert.toUnixTime() + seconds));// 1429883491，int最大21亿，还是够存储的

        kvService.save(kv);

        return kv;
    }

    /**
     * 校验token
     *
     * @param code
     * @param keys
     * @return
     */
    @Override
    public boolean validate(String code, String... keys) {
        if (!isExisted(createKey(keys)) || code == null)
            return false;

        KeyValue kv = getToken(keys);
        if (kv == null) {
            return false;
        }

        return kv.getValue().equalsIgnoreCase(code);
    }

    /**
     * 删除
     *
     * @param keys
     * @return
     */
    @Override
    public KeyValue delete(String... keys) {
        if (StrKit.isBlank(createKey(keys))) {
            return null;
        }

        KeyValue kv = getToken(keys);
        if (kv == null) {
            return null;
        }

        kvService.removeById(kv.getId());

        return kv;
    }

    /**
     * 获取对象
     *
     * @param keys
     * @return
     */
    @Override
    public KeyValue getToken(String... keys) {
        if (StrKit.isBlank(createKey(keys)))
            return null;

        KeyValue token = kvService.findByKeyAndCode(createKey(keys), KeyValueCode.TOKEN);
        if (token != null) {
            long ts = Convert.toUnixTime();

            if ((token.getReservedInt() - ts) >= 0)
                return token;
        }

        return null;
    }

    /**
     * 是否存在
     *
     * @param keys
     * @return
     */
    @Override
    public boolean isExisted(String... keys) {
        if (StrKit.isBlank(createKey(keys)))
            return false;

        return getToken(keys) != null;
    }

    /**
     * 距离过期时间毫秒数
     *
     * @param keys
     * @return
     */
    @Override
    public long expiredAfterMillis(String... keys) {
        if (!isExisted(keys))
            return -1;

        KeyValue kv = getToken(keys);
        if (kv == null) {
            return -1;
        }

        return kv.getReservedInt() - Convert.toUnixTime();
    }

    /**
     * 已存在毫秒数
     *
     * @param keys
     * @return
     */
    @Override
    public long livedMillis(String... keys) {
        if (expiredAfterMillis(createKey(keys)) > 0) {
            KeyValue entry = getToken(keys);

            return Convert.toUnixTime() - entry.getCreatedTime().getTime();
        }

        return -1;
    }

    /**
     * 创建键
     *
     * @param keys
     * @return
     */
    private String createKey(String... keys) {
        if (keys == null || keys.length == 0) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        for (String key : keys) {
            sb.append(key + "_");
        }

        return sb.substring(0, sb.length() - 1);
    }

}
