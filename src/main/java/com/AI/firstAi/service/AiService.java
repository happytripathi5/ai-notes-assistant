package com.AI.firstAi.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AiService {
    ChatClient chatClient;

    NoteService noteService;

    public AiService(ChatClient.Builder builder, NoteService noteService){
        this.chatClient=builder.build();
        this.noteService=noteService;
    }

    public String generate(String message){
        String instruction="you are a java interviewer, ask one question at a time dont answer immediately";
//        String a= chatClient.prompt(message).call().content();

        String a= chatClient.prompt(message).system(instruction).call().content();        return a;




    }
    }
