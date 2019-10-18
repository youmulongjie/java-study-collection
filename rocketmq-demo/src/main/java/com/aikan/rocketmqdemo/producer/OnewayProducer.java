package com.aikan.rocketmqdemo.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * oneway模式  只管发送
 * @author aikan
 */
@Slf4j
public class OnewayProducer {
    public static final String PRODUCER_GROUP = "ExtPro_producer02";
    public static final String NAMESRV_ADDRESS = "172.28.11.23:9876";

    public static void main(String[] args) throws Exception{
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP);
        producer.setNamesrvAddr(NAMESRV_ADDRESS);
        //Launch the instance.
        producer.start();
        for (int i = 0; i < 50; i++) {
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("ExtPro_2019" , "TagA" , ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            //Call send message to deliver message to one of brokers.
            log.info("已发送hello" + (i) + " " + msg);
            producer.sendOneway(msg);

        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}
