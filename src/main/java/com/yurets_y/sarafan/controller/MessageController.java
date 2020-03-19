package com.yurets_y.sarafan.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.yurets_y.sarafan.domain.Message;
import com.yurets_y.sarafan.domain.Views;
import com.yurets_y.sarafan.repo.MessageRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("message")
public class MessageController {
    private final MessageRepo messageRepo;

    @Autowired
    public MessageController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @GetMapping
    @JsonView(Views.IdName.class)
    public List<Message> list(){
        return messageRepo.findAll();
    }
    @GetMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public Message oneMessage(@PathVariable("id") Message message){
        return message;
    }


    /*Добавление нового сообщения*/
    @PostMapping
    public Message create(@RequestBody Message message){
        message.setCreationTime(LocalDateTime.now());
        return messageRepo.save(message);
    }
    /*Редактирование существующего сообщения*/
    @PutMapping("{id}")
    public Message update(
            @PathVariable("id") Message messageFromDB,
            @RequestBody Message message){
        BeanUtils.copyProperties(message,messageFromDB,"id");

        return messageRepo.save(messageFromDB);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message){
        messageRepo.delete(message);
    }


    @MessageMapping("/changeMessage")
    @SendTo("/topic/activity")
    public Message change(Message message){
        return messageRepo.save(message);
    }

}
