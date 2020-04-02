package com.yurets_y.sarafan.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.yurets_y.sarafan.domain.User;
import com.yurets_y.sarafan.domain.Views;
import com.yurets_y.sarafan.repo.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private final MessageRepo messageRepo;

    @Value("${spring.profiles.active}")
    private String profile;

    private final ObjectWriter objectWriter;

    public MainController(MessageRepo messageRepo, ObjectMapper objectMapper) {
        this.messageRepo = messageRepo;
        this.objectWriter = objectMapper
                .setConfig(objectMapper.getSerializationConfig())
                .writerWithView(Views.FullMessage.class);
    }

    @GetMapping
    public String getMainPage(Model model, @AuthenticationPrincipal User user) throws JsonProcessingException {
        HashMap<Object, Object> data = new HashMap<>();
        String messages = "{}";
        if(user != null){
            data.put("profile",user);
            messages = objectWriter.writeValueAsString(messageRepo.findAll());

        }


        model.addAttribute("frontendData", data);
        model.addAttribute("messages",messages);
        model.addAttribute("isDevMode", "dev".equals(profile));
        return "index";
    }
}
