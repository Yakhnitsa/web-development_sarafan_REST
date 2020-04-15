package com.yurets_y.sarafan.domain;

import com.fasterxml.jackson.annotation.JsonView;

public class TestMessage {
    private Long id;
    @JsonView(Views.IdName.class)
    private String text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
