package net.kingborn.erp.rc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.kingborn.core.model.BaseModel;

import java.util.Date;

/**
 * 类别
 */
@Data
@TableName("rc_category")
public class Category extends BaseModel<Category> {

    /**
     * 类别ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 类型
     */
    private int type;

    /**
     * 父ID
     */
    private String parentId;

    /**
     * 名称
     */
    private String name;

    /**
     * 排序
     */
    private int sortNumber;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新时间
     */
    private Date updatedTime;

}
