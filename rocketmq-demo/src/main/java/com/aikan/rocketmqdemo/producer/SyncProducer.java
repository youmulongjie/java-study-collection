package com.aikan.rocketmqdemo.producer;


import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * 同步发送，请求1响应后 发送请求2
 *
 * @author aikan
 */
@Slf4j
public class SyncProducer {
    public static final String PRODUCER_GROUP = "ExtPro_producer01";
    public static final String NAMESRV_ADDRESS = "172.28.11.23:9876";

    public static void main(String[] args) throws InterruptedException, MQClientException, RemotingException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP);
        producer.setNamesrvAddr(NAMESRV_ADDRESS);
        producer.start();
        int i = 0;
        for (int i1 = 0; i1 < 1000; i1++) {
            Message msg = new Message("ExtPro_2019", "ExtPro_Create", ("Hello ExtPro " + i++).getBytes());
            SendResult sendResult = producer.send(msg);
            log.info("已发送hello" + (i) + " " + sendResult);
        }
        producer.shutdown();

    }
}