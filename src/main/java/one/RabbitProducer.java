package one;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitProducer {
    private static final String exchange_name="exchange_demo";
    private static final String routing_key="routingkey_demo";
    private static final String queue_name="queue_demo";
    private static final String ip="127.0.0.1";
    private static final int port=5672;
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory=new ConnectionFactory() ;
        connectionFactory.setHost(ip);
        connectionFactory.setPort(port);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        Connection connection=connectionFactory.newConnection();
        Channel channel=connection.createChannel();
        channel.exchangeDeclare(exchange_name,"direct",true,false,null);
        channel.queueDeclare(queue_name,true,false,false,null);
        channel.queueBind(queue_name,exchange_name,routing_key);
        while(true) {
            String message = "Hello World";
            channel.basicPublish(exchange_name, routing_key, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            //Thread.sleep(10000);
        }

    }
}
