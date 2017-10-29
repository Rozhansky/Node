package com.example.alexander.node.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander on 29.09.2017.
 */

public class Note {
    private int id;
    private int idParent;
    private String noteText;
    private int rate;
    private boolean favorite;
    private ArrayList<Image> images;

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }
    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getNoteText(){
        return noteText;
    }
    public void setNoteText(String str){
        noteText = str;
    }

    public String getTitle(){
        if(noteText.length() > 10){
            return noteText.substring(0,10).concat("...");
        }
        return noteText;
    }

    public static List<Note> getFakeItems(){
        ArrayList<Note> itemsList = new ArrayList<>();
        /*itemsList.add(new Note("aaaaa","asdsa"));
        itemsList.add(new Note("sssss","asdsa"));
        itemsList.add(new Note("ddddd","asdsa"));
        itemsList.add(new Note("fffff","asdsa"));
        itemsList.add(new Note("gggggg","asdsa"));
        itemsList.add(new Note("hhhhhh","asdsa"));
        itemsList.add(new Note("jjjjj","asdsa"));
        itemsList.add(new Note("kkkkkk","asdsa"));
        itemsList.add(new Note("llllll","asdsa"));
        itemsList.add(new Note("nnnnn","asdsa"));*/
        return itemsList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdParent() {
        return idParent;
    }

    public void setIdParent(int idParent) {
        this.idParent = idParent;
    }
}
