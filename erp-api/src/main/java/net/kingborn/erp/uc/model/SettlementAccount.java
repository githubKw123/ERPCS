package net.kingborn.erp.uc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.kingborn.core.model.BaseModel;

import java.util.Date;

/**
 * 结算账户
 */
@Data
@TableName("uc_settlement_account")
public class SettlementAccount extends BaseModel<SettlementAccount> {

    /**
     * 账户ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 余额日期
     */
    private Date balanceTime;

    /**
     * 期初余额
     */
    private double beginBalance;

    /**
     * 当前余额
     */
    private double currentBalance;

    /**
     * 类型
     */
    private String type;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新时间
     */
    private Date updatedTime;

}
