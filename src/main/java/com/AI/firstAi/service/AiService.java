package com.AI.firstAi.service;

import com.AI.firstAi.dto.KeyPointResponse;
import com.AI.firstAi.entity.Note;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;

@Service
public class AiService {
    ChatClient chatClient;

    NoteService noteService;

    private static final Set<String> STOP_WORDS = new HashSet<>(Arrays.asList("where", "what", "is", "how", "why", "are"));


    private static final String SYSTEM_INSTRUCTION =
            "You are a study assistant.\n" +
                    "Use only the provided notes.\n" +
                    "Don't use outside knowledge.\n" +
                    "If something isn't in the notes, don't make it up.\n" +
                    "Return only valid JSON.\n" +
                    "Do not include markdown, code fences, explanations, or extra text.";


    private static final String QUESTION_SYSTEM_INSTRUCTION =
            "You are a study assistant.\n" +
                    "Use only the provided notes to answer the question.\n" +
                    "Do not use outside knowledge.\n" +
                    "If the answer is not present in the notes, say you don't know based on the notes.\n" +
                    "Answer the question normally and clearly.";

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

    public List<KeyPointResponse> generateKeyPoints() {
        List<Note> notes = noteService.get();
        //LETS MAKE THE STR TAKE THE NOTES as string and for this special method add one more thing to generate the key points in given format;
        String str = buildNoteContext(notes)
                + "\nReturn exactly 2 key points for each note."
                + "\nReturn one JSON object for each note."
                + "\nReturn all objects inside a JSON array."
                + "\nEach object must contain exactly these fields:"
                + "\ntitle"
                + "\nfirstKeyPoint"
                + "\nsecondKeyPoint";

        return chatClient.prompt(str).system(SYSTEM_INSTRUCTION).call().entity(new ParameterizedTypeReference<List<KeyPointResponse>>() {
        });


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

    public List<String> extractKeywords(String Question) {
        List<String> list = new ArrayList<>();
        String[] words = Question.split(" ");

        for (String str : words) {
            str = str.toLowerCase();
            str = str.replaceAll("[^a-zA-Z]", "");
            if (!STOP_WORDS.contains(str)) {
                list.add(str);

            }
        }

        return list;
    }


    public List<Note> getRelevantNotes(List<String> list) {

        List<Note> relevantNotes = new ArrayList<>();

        List<Note> notes = noteService.get();

        for (Note notee : notes) {

            String title = notee.getTitle().toLowerCase();
            String content=notee.getContent().toLowerCase();


            for (String keyword : list) {

                if (title.contains(keyword)) {
                    relevantNotes.add(notee);
                    break;
                }

                else if(content.contains(keyword)){
                    relevantNotes.add(notee);
                    break;
                }
            }
        }
        return relevantNotes;
    }


//    public String askquestionAiMatch(String question) {
//
//        System.out.println("1. Question received: " + question);
//
//        List<String> list = extractKeywords(question);
//        System.out.println("2. Keywords: " + list);
//
//        List<Note> relevantNotes = getRelevantNotes(list);
//        System.out.println("3. Relevant notes: " + relevantNotes.size());
//
//        String context = buildNoteContext(relevantNotes);
//        System.out.println("4. Context built");
//
//        String prompt = context + "\nMy question is: " + question;
//        System.out.println("5. Calling AI...");
//
//        return chatClient.prompt(prompt)
//                .system(SYSTEM_INSTRUCTION)
//                .call()
//                .content();
//    }
//
//
//
//
//    }
public String askquestionAiMatch(String question) {

    System.out.println("1. Question received: " + question);

    List<String> list = extractKeywords(question);
    System.out.println("2. Keywords: " + list);

    List<Note> relevantNotes = getRelevantNotes(list);
    System.out.println("3. Relevant notes: " + relevantNotes.size());

    String context = buildNoteContext(relevantNotes);
    System.out.println("4. Context built");

    String prompt = context + "\nMy question is: " + question;
    System.out.println("5. Calling AI...");

    String response = chatClient.prompt(prompt)
            .system(QUESTION_SYSTEM_INSTRUCTION)
            .call()
            .content();

    System.out.println("6. AI response received");
    System.out.println(response);

    return response;
}
}
