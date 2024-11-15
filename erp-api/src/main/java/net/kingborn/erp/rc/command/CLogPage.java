package net.kingborn.erp.rc.command;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.kingborn.core.command.BaseCommand;
import net.kingborn.core.command.Command;
import net.kingborn.core.command.Param;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.constant.Define;
import net.kingborn.erp.rc.model.Log;
import net.kingborn.erp.rc.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 日志分页列表
 */
@Command
public class CLogPage extends BaseCommand {

    @Autowired
    private LogService logService;

    @Param(defaultValue = "{}")
    private JSONObject query; // 查询对象
    @Param(defaultValue = Define.CURRENT)
    private long current;
    @Param(defaultValue = Define.SIZE)
    private long size;

    @Override
    protected void init() throws Exception {
        String startTime = query.getString("startTime");
        if (StrKit.notBlank(startTime)) {
            query.put("startTime", startTime + " 00:00:00");
        }

        String endTime = query.getString("endTime");
        if (StrKit.notBlank(endTime)) {
            query.put("endTime", endTime + " 23:59:59");
        }
    }

    @Override
    protected void doCommand() throws Exception {
        Page<Log> logPage = logService.pageSearch(current, size, query);

        data.put("logPage", logPage);
    }

}
