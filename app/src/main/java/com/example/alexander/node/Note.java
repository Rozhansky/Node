package com.example.alexander.node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander on 29.09.2017.
 */

public class Note {
    private String noteText;
    private String title;

    public Note(String noteText, String title){
        this.noteText = noteText;
        this.title = title;
    }

    public String getNoteText(){
        return noteText;
    }

    public String getTitle(){
        return title;
    }

    public static List<Note> getFakeItems(){
        ArrayList<Note> itemsList = new ArrayList<>();
        itemsList.add(new Note("aaaaa","asdsa"));
        itemsList.add(new Note("sssss","asdsa"));
        itemsList.add(new Note("ddddd","asdsa"));
        itemsList.add(new Note("fffff","asdsa"));
        itemsList.add(new Note("gggggg","asdsa"));
        itemsList.add(new Note("hhhhhh","asdsa"));
        itemsList.add(new Note("jjjjj","asdsa"));
        itemsList.add(new Note("kkkkkk","asdsa"));
        itemsList.add(new Note("llllll","asdsa"));
        itemsList.add(new Note("nnnnn","asdsa"));
        return itemsList;
    }
}
