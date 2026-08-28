package com.AI.firstAi.controller;

import com.AI.firstAi.dto.KeyPointResponse;
import com.AI.firstAi.entity.Note;
import com.AI.firstAi.service.AiService;
import com.AI.firstAi.service.NoteService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AiController {
    ChatClient chatClient;
    //create the object reference for service
    NoteService noteService;

    //call AiService bean here ;
    AiService aiService;

    //inject along with the chatclient
    public AiController(ChatClient.Builder builder,NoteService noteservice,AiService aiService) {
        this.chatClient = builder.build();
        this.noteService=noteservice;
        this.aiService=aiService;
        //injected noteservice object
    }


    @GetMapping("note/ask")
    ///ai/note/ask?message=Hello --- this will be the manner
    //http://localhost:8080/note/ask?message=kya haal hai can test in this way
    public String generate(@RequestParam String message){

        return aiService.generate(message);





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

    @GetMapping("/note/askWithAi")
    public String askWithAi(@RequestParam String question){
        List<Note>notes= noteService.get();
        return aiService.askQuestionsToAi(notes,question);
    }


    //26August
    @GetMapping ("/note/getKeyPoints")
    public List<KeyPointResponse> getKeyPointDTO(){
        return aiService.generateKeyPoints();

    }


    @GetMapping("/note/ai/getAiQuestionUsingrelevant")
    public String getAiResponseTitleOrContentMatching(@RequestParam String question){
            return aiService.askquestionAiMatch(question);
    }



}
