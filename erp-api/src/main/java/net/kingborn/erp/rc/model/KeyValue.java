package net.kingborn.erp.rc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 键值对
 */
@Data
@TableName("rc_key_value")
public class KeyValue {

    /**
     * 键值对ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 键
     */
    @TableField("`key`")
    private String key;

    /**
     * 值
     */
    private String value;

    /**
     * 编码
     */
    private String code;

    /**
     * 保留的int字段
     */
    private int reservedInt;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新时间
     */
    private Date updatedTime;

}
