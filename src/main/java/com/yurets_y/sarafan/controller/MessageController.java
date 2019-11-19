package com.yurets_y.sarafan.controller;


import com.yurets_y.sarafan.exceptions.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
public class MessageController {
    private int count = 4;

    private List<Map<String,String>> messages = new ArrayList<Map<String,String>>(){{
        add(new HashMap<String,String>(){{put("id","1"); put("text","Some text");}});
        add(new HashMap<String,String>(){{put("id","2"); put("text","Other tex");}});
        add(new HashMap<String,String>(){{put("id","3"); put("text","Third message");}});
    }};

    @GetMapping
    public Object list(){
        return messages;
    }
    @GetMapping("{id}")
    public Map<String,String> oneMessage(@PathVariable String id){
        return getMessage(id);
    }




    /*Добавление нового сообщения*/
    @PostMapping
    public Map<String,String> create(@RequestBody Map<String,String> message){
        message.put("id",String.valueOf(count++));
        messages.add(message);
        return message;
    }
    /*Редактирование существующего сообщения*/
    @PutMapping("{id}")
    public Map<String,String> update(@PathVariable String id, @RequestBody Map<String,String> message){
        Map<String, String> messageFromDB = getMessage(id);
        messageFromDB.putAll(message);
        messageFromDB.put("id",id);

        return messageFromDB;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        Map<String, String> message = getMessage(id);
        messages.remove(message);
    }


    private Map<String, String> getMessage(@PathVariable String id) {
        return messages.stream()
                .filter(message -> message.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }



}
