package com.example.alexander.node.Concrete;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.alexander.node.Abstract.INoteRepository;
import com.example.alexander.node.Const;
import com.example.alexander.node.Model.Image;
import com.example.alexander.node.Model.Note;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by W11B on 29.10.2017.
 */

public class NoteRepository implements INoteRepository {
    DBHelper dbh;

    public NoteRepository(Context context) {
        dbh = new DBHelper(context);
    }

    @Override
    public Collection<Note> getNotes(int id) {
        SQLiteDatabase db = dbh.getReadableDatabase();
        db.execSQL("DROP TABLE if exists note");
        db.execSQL("DROP TABLE if exists image");
        dbh.onCreate(db);
        Cursor c;
        Log.d(Const.LOG, "note");
        c = db.query("note", null, null, null, null, null, null);
        if (c.getCount() == 0) {
            Log.d(Const.LOG, "add data");
            ContentValues cv = new ContentValues();
            cv.put("str", "Заметка");
            db.insert("note", null, cv);
            cv.put("str", "Заметка2");
            db.insert("note", null, cv);
            cv = new ContentValues();
            cv.put("path", "/test");
            cv.put("pos", 0);
            cv.put("id_note", 1);
            db.insert("image", null, cv);
        }
        //String table = "note as N left join image as I on N.id = I.id_note";
        //String columns[] = { "N.id as id_note", "str","rate","favorite","I.id as id_image","pos","path"};
        if (id == 0) {
            c = db.query("note", null, null, null, null, null, null);
        } else {
            c = db.query("note", null, "id_parent = ?", new String[]{String.valueOf(id)}, null, null, null);
        }

        Log.d(Const.LOG, "write data");
        ArrayList<Note> notes = new ArrayList<>();
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("id");
            int idParentColIndex = c.getColumnIndex("id_parent");
            int strColIndex = c.getColumnIndex("str");
            int rateColIndex = c.getColumnIndex("rate");
            int favColIndex = c.getColumnIndex("favorite");

            do {
                Note note = new Note();
                note.setId(c.getInt(idColIndex));
                note.setIdParent(c.getInt(idParentColIndex));
                note.setRate(c.getInt(rateColIndex));
                note.setFavorite(c.getInt(favColIndex) == 1 ? true : false);
                note.setNoteText(c.getString(strColIndex));
                notes.add(note);
                Log.d(Const.LOG, note.toString());
            } while (c.moveToNext());
        }
        c.close();
        Log.d(Const.LOG, "end");
        return notes;
    }

    @Override
    public Note getNote(int id) {
        Log.d(Const.LOG,"start getNote");
        Note result = null;
        SQLiteDatabase db = dbh.getReadableDatabase();
        Cursor c;
        String table = "note as N left join image as I on N.id = I.id_note";
        String columns[] = {"N.id as id_note","id_parent", "str", "rate", "favorite", "I.id as id_image", "pos", "path"};
        c = db.query(table, columns, "id_note = ?", new String[]{String.valueOf(id)}, null, null, null);

        if (c.moveToFirst()) {
            int idNoteColIndex = c.getColumnIndex("id_note");
            int idImageColIndex = c.getColumnIndex("id_image");
            int idParentColIndex = c.getColumnIndex("id_parent");
            int strColIndex = c.getColumnIndex("str");
            int rateColIndex = c.getColumnIndex("rate");
            int favColIndex = c.getColumnIndex("favorite");
            int posColIndex = c.getColumnIndex("pos");
            int pathColIndex = c.getColumnIndex("path");

            result =new Note();
            result.setId(c.getInt(idNoteColIndex));
            result.setIdParent(c.getInt(idParentColIndex));
            result.setRate(c.getInt(rateColIndex));
            result.setFavorite(c.getInt(favColIndex) == 1 ? true : false);
            result.setNoteText(c.getString(strColIndex));
            result.setImages(new ArrayList<Image>());
            do{
                int idImage = c.getInt(idImageColIndex);
                if(idImage!=0){
                    Image image = new Image();
                    image.setId(idImage);
                    image.setPath(c.getString(pathColIndex));
                    image.setPos(c.getInt(posColIndex));
                    result.getImages().add(image);
                }
            }while (c.moveToNext());
        }
        Log.d(Const.LOG,"end getNote");
        return result;
    }

    @Override
    public int deleteNote(int id) {
        SQLiteDatabase db = dbh.getWritableDatabase();
        String [] values = new String[]{String.valueOf(id)};
        db.delete("image","id_note = ?",values);
        return db.delete("note","id = ?",values);
    }

    @Override
    public void saveNote(Note note) {
        // TODO: лень
    }
}
