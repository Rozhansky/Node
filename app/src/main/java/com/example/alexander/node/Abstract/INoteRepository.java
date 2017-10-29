package com.example.alexander.node.Abstract;

import com.example.alexander.node.Model.Note;

import java.util.Collection;

/**
 * Created by W11B on 29.10.2017.
 */

public interface INoteRepository {
    Collection<Note> getFavoriteNotes();
    Collection<Note> getChildsNote(int id);
    Note getNote(int id);
    int deleteNote(int id);
    void saveNote(Note note);
}
