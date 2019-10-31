package com.aikan.rocketmqdemo.producer;


import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 事务消息发送，请求1响应后 发送请求2
 *
 * @author aikan
 */
@Slf4j
public class TransactionProducer {
    public static final String PRODUCER_GROUP = "ExtPro_producer04";
    public static final String NAMESRV_ADDRESS = "172.28.11.23:9876";

    public static void main(String[] args) throws InterruptedException, MQClientException {
        TransactionListener transactionListener = new TransactionListenerImpl();
        TransactionMQProducer producer = new TransactionMQProducer(PRODUCER_GROUP);
        ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("client-transaction-msg-check-thread");
                return thread;
            }
        });
        producer.setNamesrvAddr(NAMESRV_ADDRESS);
        producer.setExecutorService(executorService);
        producer.setTransactionListener(transactionListener);
        producer.start();

        String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
        for (int i = 0; i < 10; i++) {
            try {
                Message msg =
                        new Message("ExtPro_2019", tags[i % tags.length], "KEY" + i,
                                ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET)
                        );
                SendResult sendResult = producer.sendMessageInTransaction(msg, null);

                log.info("发送事务消息" + sendResult.toString());
                Thread.sleep(10);
            } catch (MQClientException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        TimeUnit.SECONDS.sleep(50);
        producer.shutdown();
    }
}

/**
 * 回调函数，用于确认发送但未确认的事务消息通过事务ID进行状态判定。
 * 本地需记录事务ID 与 执行结果的对应记录
 * transactionIndex 3取模 0 1 2  模拟事务不同执行结果
 * localTrans 本示例为简单，用map来进行结果集的保存，后续可自行扩充。
 */
@Slf4j
class TransactionListenerImpl implements TransactionListener {
    private AtomicInteger transactionIndex = new AtomicInteger(0);
    private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();
    /**
     * 执行本地事务
     *
     * @param msg
     * @param arg
     * @return
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {

        log.info(Thread.currentThread().getName()+"执行local" + msg.getTransactionId());
        int value = transactionIndex.getAndIncrement();
        int status = value % 3;
        localTrans.put(msg.getTransactionId(), status);
        //第一次发送默认全部为 unknown
        return LocalTransactionState.UNKNOW;
    }
    /**
     * 回调查询事务执行结果
     *
     * @param msg
     * @return
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        log.info(Thread.currentThread().getName()+"执行check" + msg.getTransactionId());
        Integer status = localTrans.get(msg.getTransactionId());
        //回调来检查事务执行最终结果，确认是否 commit消息
        if (null != status) {
            switch (status) {
                case 0:
                    return LocalTransactionState.UNKNOW;
                case 1:
                    return LocalTransactionState.COMMIT_MESSAGE;
                case 2:
                    return LocalTransactionState.ROLLBACK_MESSAGE;
            }
        }
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}

