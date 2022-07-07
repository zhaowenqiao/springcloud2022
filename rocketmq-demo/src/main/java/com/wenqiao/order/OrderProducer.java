package com.wenqiao.order;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.ArrayList;
import java.util.List;

/**
 * 局部(分区)顺序消息
 */
public class OrderProducer {

    public static void main(String[] args) throws Exception{
        //1.创建消息生产者producer，并制定生产者组名 生产者组和消费者组 没有关系 可以不同
        DefaultMQProducer producer = new DefaultMQProducer("group0");
        //2.指定Nameserver地址
        producer.setNamesrvAddr("192.168.96.128:9876");
        //3.启动producer
        producer.start();

        List<OrderDTO> orderList = getOrderList();

        for(int i = 0;i<orderList.size();i++){
            OrderDTO orderDTO = orderList.get(i);
            Message msg = new Message("springboot-mq","order",orderDTO.toString().getBytes());
            SendResult send = producer.send(msg, new MessageQueueSelector() {
                //list 队列的集合
                //message 消息
                //o 分区
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                    int id = (int) o;
                    int mode = id % list.size();
                    return list.get(mode);
                }
            }, orderList.get(i).getId());
            System.out.println("发送的消息:" + orderDTO);
//            System.out.println("发送的消息结果:" + send);
        }
        producer.shutdown();
    }

    /**
     * 创建订单数据
     */
    private static List<OrderDTO> getOrderList() {
        List list = new ArrayList<OrderDTO>();
        OrderDTO orderDTO1 = new OrderDTO();
        orderDTO1.setId(2036056560);
        orderDTO1.setDesc("创建订单");

        OrderDTO orderDTO2 = new OrderDTO();
        orderDTO2.setId(2036056560);
        orderDTO2.setDesc("支付订单");

        OrderDTO orderDTO3 = new OrderDTO();
        orderDTO3.setId(2036056560);
        orderDTO3.setDesc("修改订单状态");
        list.add(orderDTO1);
        list.add(orderDTO2);
        list.add(orderDTO3);

        OrderDTO orderDTO4 = new OrderDTO();
        orderDTO4.setId(2036056561);
        orderDTO4.setDesc("起床");

        OrderDTO orderDTO5 = new OrderDTO();
        orderDTO5.setId(2036056561);
        orderDTO5.setDesc("刷牙");

        OrderDTO orderDTO6 = new OrderDTO();
        orderDTO6.setId(2036056561);
        orderDTO6.setDesc("上学");
        list.add(orderDTO4);
        list.add(orderDTO5);
        list.add(orderDTO6);

        OrderDTO orderDTO7 = new OrderDTO();
        orderDTO7.setId(2036056562);
        orderDTO7.setDesc("起床");

        OrderDTO orderDTO8 = new OrderDTO();
        orderDTO8.setId(2036056562);
        orderDTO8.setDesc("刷牙");

        OrderDTO orderDTO9 = new OrderDTO();
        orderDTO9.setId(2036056562);
        orderDTO9.setDesc("上学");
        list.add(orderDTO7);
        list.add(orderDTO8);
        list.add(orderDTO9);

        return list;
    }

}
