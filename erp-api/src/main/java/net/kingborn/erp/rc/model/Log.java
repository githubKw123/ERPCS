package net.kingborn.erp.rc.model;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.kingborn.core.model.BaseModel;

import java.util.Date;

/**
 * 用户
 */
@Data
@TableName("rc_log")
public class Log extends BaseModel<Log> {

    /**
     * 日志ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 类型
     */
    private int type;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 内容
     */
    private String content;

    public JSONObject getContentObject() {
        return getJSONObject(content);
    }

    public void setContentObject(JSONObject contetnObject) {
        setContent(contetnObject == null ? "{}" : contetnObject.toJSONString());

    }

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新时间
     */
    private Date updatedTime;

}
