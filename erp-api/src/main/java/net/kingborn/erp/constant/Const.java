package net.kingborn.erp.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 常量
 */
@Configuration
public class Const {

    /** 开发模式 */
    public static boolean DEV_MODE;

    @Value("${erp.devMode}")
    public void setDevMode(boolean devMode) {
        DEV_MODE = devMode;
    }

}
