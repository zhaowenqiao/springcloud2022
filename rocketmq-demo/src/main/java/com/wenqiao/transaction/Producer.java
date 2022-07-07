package com.wenqiao.transaction;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Producer {

    public static void main(String[] args) throws Exception{
        //1.创建消息生产者producer，并制定生产者组名
        TransactionMQProducer producer = new TransactionMQProducer("group1");
        //2.指定Nameserver地址
        producer.setNamesrvAddr("192.168.96.128:9876");
        producer.setTransactionListener(new TransactionListener() {
            //本地事务的状态
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                String tags = message.getTags();
                if(StringUtils.equals(tags, "Tag1")){
                    return LocalTransactionState.COMMIT_MESSAGE;
                }else if(StringUtils.equals(tags, "Tag2")){
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }else if(StringUtils.equals(tags, "Tag3")){
                    return LocalTransactionState.UNKNOW;
                }
                return LocalTransactionState.UNKNOW;
            }

            //回查询本地事务的状态
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });

        //3.启动producer
        producer.start();
        String[] arr = {"Tag1","Tag2","Tag3"};

        for (int i = 0; i < 3; i++) {
            //4.创建消息对象，指定主题Topic、Tag和消息体
            /**
             * 参数一：消息主题Topic
             * 参数二：消息Tag
             * 参数三：消息内容
             */
            Message msg = new Message("transactionTopic", arr[i], ("Hello World" + i).getBytes());

            //5.发送消息
//            SendResult result = producer.send(msg);
            TransactionSendResult transactionSendResult = producer.sendMessageInTransaction(msg, null);
            System.out.println("发送结果:" + transactionSendResult);

            //线程睡1秒
//            TimeUnit.SECONDS.sleep(1);
        }

        //6.关闭生产者producer
//        producer.shutdown();
    }
}
