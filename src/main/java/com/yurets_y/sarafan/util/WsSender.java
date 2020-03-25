package com.yurets_y.sarafan.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.yurets_y.sarafan.dto.EventType;
import com.yurets_y.sarafan.dto.ObjectType;
import com.yurets_y.sarafan.dto.WsEventDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Component
public class WsSender {
    private final SimpMessagingTemplate TEMPLATE;
    private final ObjectMapper MAPPER;

    public WsSender(SimpMessagingTemplate template, ObjectMapper mapper) {
        this.TEMPLATE = template;
        this.MAPPER = mapper;
    }

    public <T> BiConsumer<EventType,T> getSender(ObjectType objectType, Class view){
        //Настройка маппера для сериализации с помьщью указанного класса view
        ObjectWriter writer = MAPPER.setConfig(MAPPER.getSerializationConfig())
                .writerWithView(view);
        return (EventType eventType,T payload) -> {
            String value = null;
            try {
                // Получение строки json через writer
                value = writer.writeValueAsString(payload);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            //Отправка сообщений по пути (который прописан для вебсокета)
            TEMPLATE.convertAndSend(
                    "/topic/activity",
                    new WsEventDto(objectType,eventType,value)
            );
        };
    }

}
