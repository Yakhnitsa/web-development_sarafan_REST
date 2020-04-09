package com.yurets_y.sarafan.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.yurets_y.sarafan.domain.Message;
import com.yurets_y.sarafan.domain.User;
import com.yurets_y.sarafan.domain.Views;
import com.yurets_y.sarafan.dto.MessagePageDto;
import com.yurets_y.sarafan.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private final MessageService messageService;

    @Value("${spring.profiles.active}")
    private String profile;

    private final ObjectWriter objectWriter;

    public MainController(MessageService messageService, ObjectMapper objectMapper) {
        this.messageService = messageService;
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
            Sort orders = Sort.by(Sort.Direction.DESC, "id");
            PageRequest pageRequest = PageRequest.of(0, MessageController.MESSAGES_PER_PAGE, orders);
            MessagePageDto messagePageDto= messageService.getAll(pageRequest);
            messages = objectWriter.writeValueAsString(messagePageDto.getMessages());
            data.put("currentPage",messagePageDto.getCurrentPage());
            data.put("totalPages", messagePageDto.getTotalPages());

        }


        model.addAttribute("frontendData", data);
        model.addAttribute("messages",messages);
        model.addAttribute("isDevMode", "dev".equals(profile));
        return "index";
    }

    @PostMapping("/test")
    @ResponseBody
    public void test(Message message){
        System.out.println(message);
    }
}
