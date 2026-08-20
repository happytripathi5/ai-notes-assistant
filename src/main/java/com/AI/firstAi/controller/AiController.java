package com.AI.firstAi.controller;

import com.AI.firstAi.entity.Note;
import com.AI.firstAi.service.NoteService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AiController {
    ChatClient chatClient;
    //create the object reference for service
    NoteService noteService;

    //inject along with the chatclient
    public AiController(ChatClient.Builder builder,NoteService noteservice) {
        this.chatClient = builder.build();
        this.noteService=noteservice;
        //injected noteservice object
    }


    @GetMapping("note/ask")
    ///ai/note/ask?message=Hello --- this will be the manner
    //http://localhost:8080/note/ask?message=kya haal hai can test in this way
    public String generate(@RequestParam String message){
       String instruction="you are a java interviewer, ask one question at a time dont answer immediately";
//        String a= chatClient.prompt(message).call().content();

        String a= chatClient.prompt(message).system(instruction).call().content();        return a;



    }

    //will be making another controller for getting all notes and summarise
    @GetMapping("note/summarise")
//    GET http://localhost:8080/note/summarise
    public String summarise(){
        List<Note>notes= noteService.get();

        StringBuilder str= new StringBuilder();

        //LOOP THROUGH LIST
        for(Note note:notes){
            str.append("Title: ");
            str.append(note.getTitle());
            str.append("\nContent: ");
            str.append(note.getContent());
            str.append("\n");

        }
       String string= str.toString();
        String prompt="Summarise these notes: \n" +string;
        return chatClient.prompt(prompt).call().content();

    }





}
