package net.kingborn.erp.fc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.kingborn.core.model.BaseModel;

import java.util.Date;

/**
 * 收款单据
 */
@Data
@TableName("fc_collection_issue")
public class CollectionIssue extends BaseModel<CollectionIssue> {

    /**
     * 收款单据ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 收款ID
     */
    private String collectionId;

    /**
     * 源单编码
     */
    private String sourceCode;

    /**
     * 类别：10、销货 20、退货
     */
    private int type;

    /**
     * 单据日期
     */
    private String issueDate;

    /**
     * 单据金额
     */
    private double issueAmount;

    /**
     * 已核销金额
     */
    private double verifiedAmount;

    /**
     * 未核销金额
     */
    private double unverifiedAmount;

    /**
     * 本次核销金额
     */
    private double currentVerifiedAmount;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新时间
     */
    private Date updatedTime;

}
