package com.wenqiao.base.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * 同步消息
 *
 *
 * namesrvaddr:192.168.96.128:9876
 * console地址:192.168.96.128:8888
 */
public class SyncProducer {
    public static void main(String[] args) throws Exception{
        //1.创建消息生产者producer，并制定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        //2.指定Nameserver地址
        producer.setNamesrvAddr("192.168.96.128:9876");
        //3.启动producer
        producer.start();

        for (int i = 0; i < 10; i++) {
            //4.创建消息对象，指定主题Topic、Tag和消息体
            /**
             * 参数一：消息主题Topic
             * 参数二：消息Tag
             * 参数三：消息内容
             */
            Message msg = new Message("springboot-mq", "Tag1", ("Hello World222" + i).getBytes());
            //5.发送消息
            SendResult result = producer.send(msg);
            //发送状态
            SendStatus status = result.getSendStatus();
            //消息id
            String msgId = result.getMsgId();
            //消息队列id
            int queueId = result.getMessageQueue().getQueueId();

            System.out.println("发送结果:" + result);
            System.out.println("消息状态:" + status);
            System.out.println("消息id:" + msgId);
            System.out.println("接收消息队列id:" + queueId);

            //线程睡1秒
            TimeUnit.SECONDS.sleep(1);
        }

        //6.关闭生产者producer
        producer.shutdown();
    }

}
