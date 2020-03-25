package com.yurets_y.sarafan.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.yurets_y.sarafan.domain.Message;
import com.yurets_y.sarafan.domain.Views;
import com.yurets_y.sarafan.dto.EventType;
import com.yurets_y.sarafan.dto.ObjectType;
import com.yurets_y.sarafan.repo.MessageRepo;
import com.yurets_y.sarafan.util.WsSender;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;

@RestController
@RequestMapping("message")
public class MessageController {
    private final MessageRepo messageRepo;
    private final BiConsumer<EventType,Message> wsSender;

    @Autowired
    public MessageController(MessageRepo messageRepo, WsSender sender) {

        this.messageRepo = messageRepo;
        this.wsSender = sender.getSender(ObjectType.MESSAGE,Views.IdName.class);
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
        Message updatedMessage = messageRepo.save(message);

        wsSender.accept(EventType.CREATE,updatedMessage);

        return updatedMessage;
    }
    /*Редактирование существующего сообщения*/
    @PutMapping("{id}")
    public Message update(
            @PathVariable("id") Message messageFromDB,
            @RequestBody Message message){
        BeanUtils.copyProperties(message,messageFromDB,"id");

        Message updatedMessage = messageRepo.save(message);
        wsSender.accept(EventType.UPDATE,updatedMessage);

        return updatedMessage;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message){
        messageRepo.delete(message);
        wsSender.accept(EventType.REMOVE,message);
    }

//
//    @MessageMapping("/changeMessage")
//    @SendTo("/topic/activity")
//    public Message change(Message message){
//        return messageRepo.save(message);
//    }

}
