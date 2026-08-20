package com.AI.firstAi.controller;

import com.AI.firstAi.entity.Note;
import com.AI.firstAi.service.NoteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class NoteController {

    NoteService noteservice;

    public NoteController(NoteService noteService){
        this.noteservice=noteService;
    }

    @PostMapping("/note")

    public Note save(@RequestBody Note note){
        return noteservice.save(note);

    }

    @PutMapping("/note/{id}")

    public Note update(@RequestBody Note note,@PathVariable Long id){
        return noteservice.update(id,note);
    }

    @DeleteMapping("/note/{id}")

    public void delete(@PathVariable Long id){
        noteservice.delete(id);
    }

    //controller to get a id
    @GetMapping("/note/{id}")
    public Note getting(@PathVariable Long id){
        return noteservice.getbyID(id);
    }

    @GetMapping("/note")
    public List<Note> get(){
        return noteservice.get();
    }



}
