package com.example.musiconline.log.event;


import com.example.musiconline.service.OperLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 异步调用日志服务
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class LogEventListener {

    private final OperLogService operateLogService;

    /**
     * 保存系统日志记录
     */
    @Async
    @EventListener
    public void saveLog(OperLogEvent operLogEvent) {

        operateLogService.saveLog(operLogEvent);
    }


}
