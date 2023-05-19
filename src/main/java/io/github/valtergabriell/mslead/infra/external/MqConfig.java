package io.github.valtergabriell.mslead.infra.external;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {
    @Bean
    public Queue createClientQueue(){
        String queueName = "create-client-queue";
        return new Queue(queueName, true);
    }

    @Bean
    public Queue deleteAccountQueue(){
        String queueName = "delete-client-queue";
        return new Queue(queueName, true);
    }
}
