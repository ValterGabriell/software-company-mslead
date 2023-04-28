package io.github.valtergabriell.mslead.infra.external;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {
    @Bean
    public Queue config(){
        String queueName = "create-client-queue";
        return new Queue(queueName, true);
    }



}
