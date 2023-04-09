package com.gientech.redisdemo.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ConsumerController {

    @Resource
    StringRedisTemplate stringRedisTemplate;


    @GetMapping("consumer")
    public void consumer()  throws Exception{


        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer");

        consumer.setNamesrvAddr("127.0.0.1:9876");

        consumer.subscribe("TopicTest", "*");

        // 消费模式 集群模式可以实现负载均衡能力， 广播模式会出现消息的重复消费
        consumer.setMessageModel(MessageModel.BROADCASTING);


        //注册回调实现类， 拉区消息
        consumer.registerMessageListener(new MessageListenerConcurrently(){


            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                System.out.printf("%s Recive New Message:%s %n", Thread.currentThread().getName(), msgs);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //启动消费者实列
        consumer.start();
        System.out.println("consumer Start");

    }

    // 消费用户信息
    @GetMapping("consumeruser")
    public void consumerUser()  throws Exception{


        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumeruser");

        consumer.setNamesrvAddr("127.0.0.1:9876");

        consumer.subscribe("TopicTest", "*");

        // 消费模式 集群模式可以实现负载均衡能力， 广播模式会出现消息的重复消费
        consumer.setMessageModel(MessageModel.CLUSTERING);


        //注册回调实现类， 拉区消息
        consumer.registerMessageListener(new MessageListenerConcurrently(){


            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                System.out.println(msgs);
                for (int i = 0; i < msgs.size(); i++) {
                    MessageExt messageExt = msgs.get(0);

                    byte[] body = messageExt.getBody();
                    String s = new String(body);
                    JSONObject jsonObject = new JSONObject().parseObject(s);
                    System.out.println("jsonobject : " + jsonObject);
                    String id  = (String) jsonObject.get("id");
                    stringRedisTemplate.opsForValue().set(id, s);
                }
                
                
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //启动消费者实列
        consumer.start();
        System.out.println("consumer Start");

    }


    // 查询信息
    @GetMapping(value = "/showuser/{id}")
    public String showInfo(
            @PathVariable(value = "id", required = true) String  id
    ) throws Exception{


        System.out.println("id = " + id);
        return stringRedisTemplate.opsForValue().get(id);

    }

}
