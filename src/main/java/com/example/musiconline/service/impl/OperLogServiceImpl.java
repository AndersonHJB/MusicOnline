package com.example.musiconline.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.musiconline.config.mybatis.page.TableDataInfo;
import com.example.musiconline.domain.SysOperLog;
import com.example.musiconline.domain.bo.SysOperLogBo;
import com.example.musiconline.domain.vo.SysOperLogVo;
import com.example.musiconline.log.event.OperLogEvent;
import com.example.musiconline.mapper.SysOperLogMapper;
import com.example.musiconline.service.OperLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 操作日志 服务层处理
 */
@Service
@RequiredArgsConstructor
public class OperLogServiceImpl implements OperLogService {

    private final SysOperLogMapper sysOperLogMapper;
    @Override
    public void saveLog(OperLogEvent operLogEvent) {

        SysOperLog sysOperLog = new SysOperLog();
        BeanUtils.copyProperties(operLogEvent, sysOperLog);
        sysOperLogMapper.insert(sysOperLog);
    }

    @Override
    public TableDataInfo<SysOperLogVo> getLogList(SysOperLogBo bo) {
        LambdaQueryWrapper<SysOperLog> queryWrapper = new LambdaQueryWrapper<>();
        //关键字全字段模糊查询
        String keyword = bo.getKeyword();
        if (keyword != null && !keyword.isBlank()) {
            queryWrapper.and(qw -> qw.like(SysOperLog::getTitle, keyword)
                    .or().like(SysOperLog::getMethod, keyword)
                    .or().like(SysOperLog::getRequestMethod, keyword)
                    .or().like(SysOperLog::getOperName, keyword)
                    .or().like(SysOperLog::getOperUrl, keyword)
                    .or().like(SysOperLog::getOperIp, keyword)
                    .or().like(SysOperLog::getOperParam, keyword)
                    .or().like(SysOperLog::getJsonResult, keyword)
                    .or().like(SysOperLog::getErrorMsg, keyword));
        }
        IPage<SysOperLogVo> sysOperLogVoIPage = sysOperLogMapper.selectVoPage(bo.build(), queryWrapper);

        return TableDataInfo.build(sysOperLogVoIPage);
    }

    @Override
    public void cleanLog() {

        sysOperLogMapper.delete(null);
    }
}
