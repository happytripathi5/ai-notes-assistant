package com.AI.firstAi.service;

import com.AI.firstAi.entity.Note;
import com.AI.firstAi.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class NoteService {

    NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository){
        this.noteRepository= noteRepository ;
    }
    public Note save(Note note){
        return noteRepository.save(note);

    }

    public Note update(Long id, Note note){
        Optional<Note> notee= noteRepository.findById(id);
        if(notee.isPresent()){
            Note newnote=notee.get();
            newnote.setTitle(note.getTitle());
            newnote.setContent(note.getContent());

            return noteRepository.save(newnote);

        }
        else{
            throw new RuntimeException("Note not found");
        }

    }
    public void delete(Long id){
        noteRepository.deleteById(id);
    }

    //will make method to get though a id

    public Note getbyID(Long id){

        Optional<Note> note=noteRepository.findById(id);
        if(note.isPresent()){
            return note.get();
        }
        else{
            throw new RuntimeException("not found");
        }
    }

    public List<Note> get(){

        return noteRepository.findAll();
    }

}
