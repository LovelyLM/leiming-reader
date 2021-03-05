package com.leiming.task;

import com.leiming.service.EvaluationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author LovelyLM
 * @date 2021-03-04 22:57
 * 完成计算任务
 */
@Slf4j
@Component
public class ComputeTask {
    @Resource
    private EvaluationService evaluationService;


    @Scheduled(cron = "0 * * * * ?")
    public void updateEvaluation(){
        evaluationService.updateEvaluation();
        log.info("已更新所有评分");
    }
}
