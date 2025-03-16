package com.example.musiconline.service;

import com.example.musiconline.config.mybatis.page.TableDataInfo;
import com.example.musiconline.domain.bo.SysOperLogBo;
import com.example.musiconline.domain.vo.SysOperLogVo;
import com.example.musiconline.log.event.OperLogEvent;

/**
 * 操作日志服务
 */
public interface OperLogService {

    /**
     * 保存操作日志
     */
    void saveLog(OperLogEvent operLogEvent);

    /**
     * 查询操作日志
     */
    TableDataInfo<SysOperLogVo> getLogList(SysOperLogBo bo);


    /**
     * 清空操作日志
     */
    void cleanLog();
}
