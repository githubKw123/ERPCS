package net.kingborn.erp.rc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.kingborn.core.model.BaseModel;

import java.util.Date;

/**
 * 字典项
 */
@Data
@TableName("rc_dict_item")
public class DictItem extends BaseModel<DictItem> {

    /**
     * 字典项ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 字典编码
     */
    private String dictCode;

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
