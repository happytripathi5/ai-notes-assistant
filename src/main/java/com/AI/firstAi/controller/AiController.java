package com.AI.firstAi.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AiController {
    ChatClient chatClient;

    public AiController(ChatClient.Builder builder){
        this.chatClient=builder.build();
    }


    @GetMapping("note/ask")
    ///ai/note/ask?message=Hello --- this will be the manner
    //http://localhost:8080/note/ask?message=kya haal hai can test in this way
    public String generate(@RequestParam String message){
       String a= chatClient.prompt(message).call().content();
       return a;



    }
}
