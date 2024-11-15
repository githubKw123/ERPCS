package net.kingborn.erp.rc.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import net.kingborn.erp.rc.model.Category;

import java.util.List;

/**
 * 类别服务
 */
public interface CategoryService extends IService<Category> {

    /**
     * 获取列表
     *
     * @param type
     * @param query
     * @param parentId
     * @return
     */
    List<Category> findListByType(int type, JSONObject query, String parentId);

}
