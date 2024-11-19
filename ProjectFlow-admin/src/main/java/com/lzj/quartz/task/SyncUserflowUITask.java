package com.lzj.quartz.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SyncUserflowUITask {
//    @Scheduled(fixedRate = 5000)
//    public void runTask() {
//        System.out.println("定时任务正在执行：" + System.currentTimeMillis());
//    }

    // 定时任务使用 Cron 表达式，每30秒执行一次
//    @Scheduled(cron = "0/30 * * * * ?")
//    public void cronTask() {
//       log.info("流程设计器与系统用户同步任务正在执行");
//    }

}
