package one;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RabbitConsumer {
    private static final String queue_name="queue_demo";
    private static final String ip="127.0.0.1";
    //private static final int port=5672;
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
       // Address[] addresses=new Address[]{new Address(ip,port)};
        ConnectionFactory factory=new ConnectionFactory();
        factory.setUsername("quest");
        factory.setPassword("quest");
        factory.setHost(ip);
        Connection connection=factory.newConnection();
        final Channel channel=connection.createChannel();
        channel.basicQos(64);
        Consumer consumer=new DefaultConsumer(channel){
          public void handleDelivery(String consumerTag,Envelope envelope,AMQP.BasicProperties properties,byte[] body) throws IOException {
              System.out.println("recv message:"+new String(body));
              try{
                  TimeUnit.SECONDS.sleep(1);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
              channel.basicAck(envelope.getDeliveryTag(),false);

          }
        };
        channel.basicConsume(queue_name,consumer);
        TimeUnit.SECONDS.sleep(5);
        channel.close();
        connection.close();
    }
}
