package com.aikan.elasticjobdemo.config;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyElasticJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
//        switch (shardingContext.getShardingItem()) {
//            case 0:
//                log.info("getShardingItem"+shardingContext.getShardingItem());
//            case 1:
//                log.info("getShardingItem"+shardingContext.getShardingItem());
//            case 2:
//                log.info("getShardingItem"+shardingContext.getShardingItem());
//            case 3:
//                log.info("getShardingItem"+shardingContext.getShardingItem());
//        }
        log.info(String.format("Thread ID: %s, 作业分片总数: %s, " +
                        "当前分片项: %s.当前参数: %s," +
                        "作业名称: %s.作业自定义参数: %s"
                ,
                Thread.currentThread().getId(),
                shardingContext.getShardingTotalCount(),
                shardingContext.getShardingItem(),
                shardingContext.getShardingParameter(),
                shardingContext.getJobName(),
                shardingContext.getJobParameter()
        ));

    }
}
