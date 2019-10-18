package com.aikan.rocketmqdemo.cosumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * orderly消费模式，一线程 一个队列 顺序消费
 * @author aikan
 */
@Slf4j
public class PushConsumer6 {
    public static final String PRODUCER_GROUP = "ExtPro-consumer";
    public static final String NAMESRV_ADDRESS = "172.28.11.23:9876";
    public static final String INSTANCENAME = "ExtPro-consumer06";
    public static final String TOPIC = "ExtPro_2019";
    public static final String expression = "*";


    public static void main(String[] args) throws MQClientException {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(PRODUCER_GROUP);
        consumer.subscribe(TOPIC, expression);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.setInstanceName(INSTANCENAME);
        consumer.setNamesrvAddr(NAMESRV_ADDRESS);

        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                msgs.forEach(messageExt -> {
                    log.info(Thread.currentThread().getName() + " 队列为：" + messageExt.getQueueId() +" 内容为："+ new String(messageExt.getBody()));
                });
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();
    }
}
