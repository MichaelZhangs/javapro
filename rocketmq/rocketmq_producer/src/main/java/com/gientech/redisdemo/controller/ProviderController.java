package com.gientech.redisdemo.controller;

import com.alibaba.fastjson.JSON;
import com.gientech.redisdemo.info.UserInfo;
import io.lettuce.core.ScriptOutputType;
import org.apache.catalina.User;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.CountDownLatch2;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@RestController
public class ProviderController {

    @GetMapping("/syncMethod")
    public String syncMethod1() throws Exception {
        // 实例化生产者 producer
        DefaultMQProducer producer = new DefaultMQProducer("provider");

        // 设置NameServer的地址
        producer.setNamesrvAddr("127.0.0.1:9876");
        //启动producer 实列
        producer.start();

        for (int i = 0; i < 10; i++) {
            Message msg = new Message("TopicTest", "TagA", ("Hello RocketMq [ " + i + "]").getBytes(StandardCharsets.UTF_8));
            // 发送消息到一个Broker
            SendResult sendResult = producer.send(msg);
            // 通过sendResult返回消息是否成功发送
            System.out.println("===>"+ sendResult);


        }
        // 如果不再发送消息， 关闭producer 实列
        producer.shutdown();
        return "success MQ";
    }

    @GetMapping("/producer2")
    public String asyncMethod() throws Exception {
        // 实例化生产者 producer
        DefaultMQProducer producer = new DefaultMQProducer("provider");

        // 设置NameServer的地址
        producer.setNamesrvAddr("127.0.0.1:9876");
        //启动producer 实列
        producer.start();

        producer.setRetryTimesWhenSendAsyncFailed(0);
        int messageCount = 100;
        //  根据消息数量实列化倒计时计算器
        final CountDownLatch2 countDownLatch = new CountDownLatch2(messageCount);

        for (int i = 0; i < 10; i++) {
            final int index = i;
            Message msg = new Message("TopicTest",
                    "TagA",
                    "orderI0188",
                    ("Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET)));

            // SendCallback 接受异步返回结果的回调
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.printf("%-10d 0k %s %n", index, sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable throwable) {
                    System.out.printf("%-10d Exception %s %n",index, throwable);
                    throwable.printStackTrace();
                }
            });


        }
        //等待5s
        countDownLatch.await(5, TimeUnit.SECONDS);

        // 如果不再发送消息， 关闭producer 实列
        producer.shutdown();
        return "success MQ";
    }

    @GetMapping("/single")
    public String single() throws Exception {
        // 实例化生产者 producer
        DefaultMQProducer producer = new DefaultMQProducer("provider");

        // 设置NameServer的地址
        producer.setNamesrvAddr("127.0.0.1:9876");
        //启动producer 实列
        producer.start();

        for (int i = 0; i < 10; i++) {
            Message msg = new Message("TopicTest", "TagA", ("Hello RocketMq [ " + i + "]").getBytes(StandardCharsets.UTF_8));
            // 发送消息到一个Broker
            producer.sendOneway(msg);

        }
        // 如果不再发送消息， 关闭producer 实列
        producer.shutdown();
        return "success MQ";
    }


    /** 此方式用于实现作业二
     * 通过provide 和 consumer 两个spring boot项目， 基于rocketmq, redis, 实现由provider 发布新注册用户信息
     * consumer则进行有相应主题订阅， 并将订阅消息存入redis中，
     * 在consumer侧， 是西安用户信息查询功能， 并展示到页面
     */
    @GetMapping("/user")
    public String asyncUserInfo() throws Exception{
        // 实例化生产者 producer
        DefaultMQProducer producer = new DefaultMQProducer("provider");

        // 设置NameServer的地址
        producer.setNamesrvAddr("127.0.0.1:9876");
        //启动producer 实列
        producer.start();

        producer.setRetryTimesWhenSendAsyncFailed(0);
        int messageCount = 100;
        //  根据消息数量实列化倒计时计算器
        final CountDownLatch2 countDownLatch = new CountDownLatch2(messageCount);



        for (int i = 0; i < 10; i++) {
            final int index = i;
            // 拼接用户信息
            UserInfo userInfo = new UserInfo(Integer.toString(i), "zhangsan_" + i, "1210000100" + i);
            String s = JSON.toJSONString(userInfo);
            Message msg = new Message("TopicTest",
                    "TagA",
                    "orderI0188",
                    (s.getBytes(RemotingHelper.DEFAULT_CHARSET)));

            // SendCallback 接受异步返回结果的回调
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.printf("%-10d 0k %s %n", index, sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable throwable) {
                    System.out.printf("%-10d Exception %s %n",index, throwable);
                    throwable.printStackTrace();
                }
            });


        }
        //等待5s
        countDownLatch.await(5, TimeUnit.SECONDS);

        // 如果不再发送消息， 关闭producer 实列
        producer.shutdown();


        return  " register ok";
    }

}
