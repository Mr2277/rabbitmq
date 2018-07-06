package rabbitmq.producer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import rabbitmq.config.ConnectionFactoryConfigure;

import java.io.UnsupportedEncodingException;
import java.util.UUID;
@Controller

public class SendController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/send")
    public String send3() throws UnsupportedEncodingException {
        String uuid = UUID.randomUUID().toString();
        CorrelationData correlationId = new CorrelationData(uuid);
        rabbitTemplate.convertAndSend(ConnectionFactoryConfigure.EXCHANGE, ConnectionFactoryConfigure.ROUTINGKEY2, "哈哈", correlationId);
        return uuid;
    }

}
