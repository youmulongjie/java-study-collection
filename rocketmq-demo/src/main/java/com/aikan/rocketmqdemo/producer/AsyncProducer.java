package com.aikan.rocketmqdemo.producer;


import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.TimeUnit;

/**
 * 异步发送消息
 *
 * @author aikan
 */
@Slf4j
public class AsyncProducer {

    public static final String PRODUCER_GROUP = "ExtPro_producer03";
    public static final String NAMESRV_ADDRESS = "172.28.11.23:9876";

    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP);
        producer.setNamesrvAddr(NAMESRV_ADDRESS);

        //Launch the instance.
        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(0);
        for (int i = 0; i < 100; i++) {
            final int index = i;
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("ExtPro_2019",
                    "TagA",
                    "OrderID188",
                    ("Hello world" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            log.info("调用发送" + i);
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {

                    log.info(Thread.currentThread().getName() + "-调用成功" + index + "---" + new String(msg.getBody()) + sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable e) {
                    log.info("调用" + index + e.getMessage());
                }
            });
        }
        //Shut down once the producer instance is not longer in use.
        TimeUnit.SECONDS.sleep(5);
        producer.shutdown();
    }
}
