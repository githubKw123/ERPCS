package net.kingborn.erp.rc.service;

import net.kingborn.erp.rc.model.KeyValue;

/**
 * 令牌服务
 */
public interface TokenService {

    /**
     * 保存
     *
     * @param code
     * @param seconds
     * @param keys
     * @return
     */
    KeyValue save(String code, int seconds, String... keys);

    /**
     * 校验token
     *
     * @param code
     * @param keys
     * @return
     */
    boolean validate(String code, String... keys);

    /**
     * 删除
     *
     * @param keys
     * @return
     */
    KeyValue delete(String... keys);

    /**
     * 获取对象
     *
     * @param keys
     * @return
     */
    KeyValue getToken(String... keys);

    /**
     * 是否存在
     *
     * @param keys
     * @return
     */
    boolean isExisted(String... keys);

    /**
     * 距离过期时间毫秒数
     *
     * @param keys
     * @return
     */
    long expiredAfterMillis(String... keys);

    /**
     * 已存活毫秒数
     *
     * @param keys
     * @return
     */
    long livedMillis(String... keys);

}
