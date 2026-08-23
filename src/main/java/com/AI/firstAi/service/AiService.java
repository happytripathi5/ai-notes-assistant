package com.AI.firstAi.service;

import com.AI.firstAi.entity.Note;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiService {
    ChatClient chatClient;

    NoteService noteService;

    private static final String SYSTEM_INSTRUCTION = "You are a study assistant.\n" +
            "Use only the provided notes.\n" +
            "Don't use outside knowledge.\n" +
            "If something isn't in the notes, don't make it up.";

    public AiService(ChatClient.Builder builder, NoteService noteService) {
        this.chatClient = builder.build();
        this.noteService = noteService;
    }

    public String generate(String message) {
        String instruction = "you are a java interviewer, ask one question at a time dont answer immediately";
//        String a= chatClient.prompt(message).call().content();

        String a = chatClient.prompt(message).system(instruction).call().content();
        return a;


    }

    //23 aug :- make the Ai service recieves the note

    public String askQuestionsToAi(List<Note> Notes, String question) {

        StringBuilder sb = new StringBuilder();
        for (Note note : Notes) {
            sb.append("title: ");
            sb.append(note.getTitle());
            sb.append("\nContent: ");
            sb.append(note.getContent());
            sb.append("\n");

        }
        String str = "Here is my all notes: \n" + sb.toString() + "\nPlease answer on the basis of my notes only please dont use your mind from outside" + "\n My question is:-" + question;
        //i got the string with the input

        return chatClient.prompt(str).call().content();

    }

    //making this method for generating key poiints (2 key points of top3 notes)
    //right now everything is in the same method, to convert note into string we will create a private helper method down of this method to return the note as string--DONE IT
    //CREATED A STATIC FINAL VARIABLE FOR SYSTEM PROMPT ON TOP OF THE PAGE

    public String generateKeyPoints() {
        List<Note> notes = noteService.get();
        //LETS MAKE THE STR TAKE THE NOTES as string and for this special method add one more thing to generate the key points in given format;
        String str = buildNoteContext(notes) + "\n return only 2 key points of each note in this format: \nTitle: \nKeyPoints: \nFirstkeypoint: \nSeconkeyPoint: \nlisten one thing for keypoints just give the points in the given format";


        return chatClient.prompt(str).system(SYSTEM_INSTRUCTION).call().content();


    }
    //created a helper method for just above class, and specify here if any else-- to convert all notes in the string

    private String buildNoteContext(List<Note> note) {

        StringBuilder sb = new StringBuilder();

        for (Note notee : note) {
            sb.append("Title: ");
            sb.append(notee.getTitle());
            sb.append("\nContent: ");
            sb.append(notee.getContent());
            sb.append("\n");
        }
        return "Here is my all notes: \n" + sb.toString();

    }
}
