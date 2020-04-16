package com.yurets_y.sarafan.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.yurets_y.sarafan.domain.Message;
import com.yurets_y.sarafan.domain.User;
import com.yurets_y.sarafan.domain.Views;
import com.yurets_y.sarafan.dto.MessagePageDto;
import com.yurets_y.sarafan.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("message")
public class MessageController {
    private final MessageService messageService;
    public static final int MESSAGES_PER_PAGE = 3;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    @JsonView(Views.FullMessage.class)
    public MessagePageDto list(
            @AuthenticationPrincipal User user,
            @PageableDefault(size=MESSAGES_PER_PAGE,
                sort={"id"},
                direction = Sort.Direction.DESC
    ) Pageable pageable){
        return messageService.findForUser(pageable, user);
    }

    @GetMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public Message oneMessage(@PathVariable("id") Message message){
        return message;
    }


    /*Добавление нового сообщения*/
    @PostMapping
    @JsonView(Views.FullMessage.class)
    public Message create(
            @RequestBody Message message
            ,@AuthenticationPrincipal User author
    ) throws IOException {
        return messageService.createMessage(message,author);

    }


    /*Редактирование существующего сообщения*/
    @PutMapping("{id}")
    public Message update(
            @PathVariable("id") Message messageFromDB,
            @RequestBody Message message) throws IOException
    {
            return messageService.updateMessage(messageFromDB, message);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message){
        messageService.delete(message);

    }

}
