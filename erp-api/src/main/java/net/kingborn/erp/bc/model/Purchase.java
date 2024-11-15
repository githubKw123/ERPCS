package net.kingborn.erp.bc.model;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.kingborn.core.model.BaseModel;

import java.util.Date;

/**
 * 购货单
 */
@Data
@TableName("bc_purchase")
public class Purchase extends BaseModel<Purchase> {

    /**
     * 购货单ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 类型：buy、采购 refund、采购退货
     */
    private String type;

    /**
     * 供应商ID
     */
    private String supplierId;

    /**
     * 单据日期
     */
    private String issueDate;

    /**
     * 单据编号
     */
    private String code;

    /**
     * 数量
     */
    private double quantity;

    /**
     * 折扣额
     */
    private double discountAmount;

    /**
     * 购货金额
     */
    private double amount;

    /**
     * 优惠率
     */
    private double preferentialRate;

    /**
     * 优惠金额
     */
    private double preferentialAmount;

    /**
     * 本次付/退款
     */
    private double currentAmount;

    /**
     * 优惠后金额
     */
    private double preferredAmount;

    /**
     * 欠款金额
     */
    private double debtAmount;

    /**
     * 采购合同
     */
    private String contracts;

    public JSONArray getContractArray() {
        return getJSONArray(contracts);
    }

    public void setContractArray(JSONArray contractArray) {
        setContracts(contractArray == null ? "[]" : contractArray.toJSONString());
    }

    /**
     * 付/退款状态：10、未付/退款 20、已付/退部分金额 30、全部付/退款
     */
    private int status;

    /**
     * 制单人ID
     */
    private String listerId;

    /**
     * 审核人ID
     */
    private String auditorId;

    /**
     * 审核状态
     */
    private boolean checked;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新时间
     */
    private Date updatedTime;
}
