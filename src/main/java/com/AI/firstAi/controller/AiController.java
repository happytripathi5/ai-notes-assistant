package com.AI.firstAi.controller;

import com.AI.firstAi.service.NoteService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AiController {
    ChatClient chatClient;


    public AiController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }


    @GetMapping("note/ask")
    ///ai/note/ask?message=Hello --- this will be the manner
    //http://localhost:8080/note/ask?message=kya haal hai can test in this way
    public String generate(@RequestParam String message){
       String instruction="you are a java interviewer, ask one question at a time dont answer immediately";
//        String a= chatClient.prompt(message).call().content();

        String a= chatClient.prompt(message).system(instruction).call().content();        return a;



    }





}
