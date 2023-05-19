package io.github.valtergabriell.mslead.infra.external.send;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.valtergabriell.mslead.application.domain.dto.ClientAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ClientQueue {

    private final AmqpTemplate rabbitTemplate;

    public ClientQueue(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void createNewClient(ClientAccount clientAccount) {
        log.info("Dados recebidos: " + clientAccount.toString());
        String payload = convertToJson(clientAccount);
        rabbitTemplate.convertAndSend("create-client-queue", payload);
    }

    public void deleteClient(Long id) {
        String payload = convertToJson(id);
        rabbitTemplate.convertAndSend("delete-client-queue", payload);
    }

    private <T> String convertToJson(T data) {
        ObjectMapper om = new ObjectMapper();
        try {
            return om.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
