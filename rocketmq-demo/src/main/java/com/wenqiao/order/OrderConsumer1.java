package com.wenqiao.order;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class OrderConsumer1 {

    public static void main(String[] args) throws Exception{
        //1.创建消费者Consumer，制定消费者组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        //2.指定Nameserver地址
        consumer.setNamesrvAddr("192.168.96.128:9876");
        //3.订阅主题Topic和Tag
        consumer.subscribe("springboot-mq", "*");

        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                for(MessageExt messageExt : list){
                    byte[] body = messageExt.getBody();
                    System.out.println("顺序消息消费:" + new String(body));
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        //5.启动消费者consumer
        consumer.start();
    }

}
