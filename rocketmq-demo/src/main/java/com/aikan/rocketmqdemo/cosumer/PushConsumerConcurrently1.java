package com.aikan.rocketmqdemo.cosumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;

/**
 * @author aikan
 */
@Slf4j
public class PushConsumerConcurrently1 {
    public static final String PRODUCER_GROUP = "ExtPro-consumer";
    public static final String NAMESRV_ADDRESS = "172.28.11.23:9876";
    //自定义实力服务名称时，防止重复，否则集群消费会乱
    public static final String INSTANCENAME = "ExtPro-consumer001";
    public static final String TOPIC = "ExtPro_2019";
    public static final String expression = "*";


    public static void main(String[] args) throws MQClientException {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(PRODUCER_GROUP);
        consumer.subscribe(TOPIC, expression);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.setInstanceName(INSTANCENAME);
        consumer.setNamesrvAddr(NAMESRV_ADDRESS);

        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            for (MessageExt messageExt : msgs) {
                String messageBody = null;
                try {
                    messageBody = new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //输出消息内容
                log.info(Thread.currentThread().getName() + " 消费响应：msgId : " + messageExt.getMsgId() + ",  msgBody : " + messageBody);
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        consumer.start();
    }
}
