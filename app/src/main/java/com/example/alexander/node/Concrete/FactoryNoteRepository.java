package com.example.alexander.node.Concrete;

import android.content.ContentValues;
import android.content.Context;

import com.example.alexander.node.Abstract.INoteRepository;

/**
 * Created by W11B on 30.10.2017.
 */

public class FactoryNoteRepository {
    Context context;
    public FactoryNoteRepository(Context context){
        this.context = context;
    }
    public INoteRepository getRepository(){
        return new NoteRepository(context);
    }
}
