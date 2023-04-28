package io.github.valtergabriell.mslead.infra.external.send;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.valtergabriell.mslead.application.domain.dto.ClientAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SendNewClientToQueue {

    private final RabbitTemplate rabbitTemplate;
    private final Queue createClientQueue;

    public SendNewClientToQueue(RabbitTemplate rabbitTemplate, Queue createClientQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.createClientQueue = createClientQueue;
    }

    public void createNewClient(ClientAccount clientAccount) {
        log.info("Payload recebido: " + clientAccount.toString());
        String payload = convertToJson(clientAccount);
        rabbitTemplate.convertAndSend(createClientQueue.getName(), payload);
    }

    private String convertToJson(ClientAccount clientAccount) {
        ObjectMapper om = new ObjectMapper();
        try {
            return om.writeValueAsString(clientAccount);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
