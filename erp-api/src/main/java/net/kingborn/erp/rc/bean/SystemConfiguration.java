package net.kingborn.erp.rc.bean;

import lombok.Data;
import net.kingborn.core.util.JsonKit;

import java.util.Date;

/**
 * 系统配置
 */
@Data
public class SystemConfiguration {

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 公司地址
     */
    private String companyAddress;

    /**
     * 公司电话
     */
    private String companyPhone;

    /**
     * 公司传真
     */
    private String companyFax;

    /**
     * 公司邮编
     */
    private String companyPostCode;

    /**
     * 启用时间
     */
    private Date startTime;

    /**
     * 本位币
     */
    private String currency;

    /**
     * 数量精度
     */
    private int quantityPrecision;

    /**
     * 单价精度
     */
    private int pricePrecision;

    /**
     * 存货计价方法
     */
    private int inventoryValuationMethod;

    /**
     * 是否检查负库存
     */
    private boolean checkNegativeStock;

    public static void main(String[] args) {
        SystemConfiguration configuration = new SystemConfiguration();
        configuration.setCompanyName("精博科技");
        configuration.setCompanyAddress("福建福州");
        configuration.setCompanyPhone("18905013333");
        configuration.setCompanyFax("0591-88888888");
        configuration.setCompanyPostCode("350000");
        configuration.setStartTime(new Date());
        configuration.setCurrency("RMB");
        configuration.setQuantityPrecision(2);
        configuration.setPricePrecision(2);
        configuration.setInventoryValuationMethod(10);

        System.out.println(JsonKit.toJson(configuration));
    }

}
