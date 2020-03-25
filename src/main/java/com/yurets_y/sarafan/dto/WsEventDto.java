package com.yurets_y.sarafan.dto;


import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonView;
import com.yurets_y.sarafan.domain.Views;

import java.util.Objects;

@JsonView(Views.Id.class)
public class WsEventDto {
    private ObjectType objectType;
    private EventType eventType;

    @JsonRawValue
    private String body;

    public WsEventDto(ObjectType objectType, EventType eventType, String body) {
        this.objectType = objectType;
        this.eventType = eventType;
        this.body = body;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WsEventDto that = (WsEventDto) o;
        return objectType == that.objectType &&
                eventType == that.eventType &&
                Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {

        return Objects.hash(objectType, eventType, body);
    }

    @Override
    public String toString() {
        return "WsEventDto{" +
                "objectType=" + objectType +
                ", eventType=" + eventType +
                ", body='" + body + '\'' +
                '}';
    }
}
