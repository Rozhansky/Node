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
//TODO: сократить/убрать повторение кода получения Note из Cursor
public class NoteRepository implements INoteRepository {
    DBHelper dbh;

    public NoteRepository(Context context) {
        dbh = new DBHelper(context);
    }

    @Override
    public Collection<Note> getFavoriteNotes() {
        ArrayList<Note> result = new ArrayList<>();
        SQLiteDatabase db = dbh.getReadableDatabase();
        Cursor c;
        c = db.query("note", null, "favorite = ?", new String[]{String.valueOf(1)}, null, null, null);
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
                note.setFavorite(c.getInt(favColIndex) == 1);
                note.setNoteText(c.getString(strColIndex));
                result.add(note);
            } while (c.moveToNext());
        }
        return result;
    }

    @Override
    public Collection<Note> getChildsNote(int id) {
        Log.d(Const.LOG, "start getChildsNote");
        SQLiteDatabase db = dbh.getReadableDatabase();
        /*db.execSQL("DROP TABLE if exists note");
        db.execSQL("DROP TABLE if exists image");
        dbh.onCreate(db);*/
        Cursor c;
        if (id == 0) {
            c = db.query("note", null, "id_parent < 1", null, null, null, null);
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
                note.setFavorite(c.getInt(favColIndex) == 1);
                note.setNoteText(c.getString(strColIndex));
                notes.add(note);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        Log.d(Const.LOG, "end");
        return notes;
    }

    @Override
    public Note getNote(int id) {
        Log.d(Const.LOG, "start getNote");
        Note result = null;
        SQLiteDatabase db = dbh.getReadableDatabase();
        Cursor c;
        String table = "note as N left join image as I on N.id = I.id_note";
        String columns[] = {"N.id as id_note", "id_parent", "str", "rate", "favorite", "I.id as id_image", "pos", "path"};
        c = db.query(table, columns, "N.id = ?", new String[]{String.valueOf(id)}, null, null, null);
        // c = db.query(table, columns, null, null, null, null, null);
        if (c.moveToFirst()) {
            int idNoteColIndex = c.getColumnIndex("id_note");
            int idImageColIndex = c.getColumnIndex("id_image");
            int idParentColIndex = c.getColumnIndex("id_parent");
            int strColIndex = c.getColumnIndex("str");
            int rateColIndex = c.getColumnIndex("rate");
            int favColIndex = c.getColumnIndex("favorite");
            int posColIndex = c.getColumnIndex("pos");
            int pathColIndex = c.getColumnIndex("path");

            result = new Note();
            result.setId(c.getInt(idNoteColIndex));
            result.setIdParent(c.getInt(idParentColIndex));
            result.setRate(c.getInt(rateColIndex));
            result.setFavorite(c.getInt(favColIndex) == 1);
            result.setNoteText(c.getString(strColIndex));
            result.setImages(new ArrayList<Image>());
            do {
                int idImage = c.getInt(idImageColIndex);
                if (idImage != 0) {
                    Image image = new Image();
                    image.setId(idImage);
                    image.setPath(c.getString(pathColIndex));
                    image.setPos(c.getInt(posColIndex));
                    result.getImages().add(image);
                }
            } while (c.moveToNext());
        }
        c = db.query("image", null, null, null, null, null, null);
        while (c.moveToNext()) {
            StringBuilder sb = new StringBuilder();
            for (String str : c.getColumnNames()) {
                sb.append(" - ").append(str).append(":").append(c.getString(c.getColumnIndex(str)));
            }
            Log.d(Const.LOG,sb.toString());
        }
        Log.d(Const.LOG, "end getNote");
        c.close();
        db.close();
        return result;
    }

    @Override
    public int deleteNote(int id) {
        SQLiteDatabase db = dbh.getWritableDatabase();
        String[] values = new String[]{String.valueOf(id)};

        Cursor c;
        c = db.query("note", new String[]{"id_parent"}, "id = ?", values, null, null, null);
        if (c.moveToNext()) {
            int idParent = c.getInt(c.getColumnIndex("id_parent"));
            ContentValues cv = new ContentValues();
            cv.put("id_parent", idParent);
            db.update("note", cv, "id_parent = ?", values);
        }
        db.delete("image", "id_note = ?", values);
        int result = db.delete("note", "id = ?", values);
        db.close();
        return result;
    }

    @Override
    public void saveNote(Note note) {
        Log.d(Const.LOG, "start saveNote");
        SQLiteDatabase db = dbh.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("str", note.getNoteText());
        cv.put("id_parent", note.getIdParent());
        cv.put("rate", note.getRate());
        cv.put("favorite", note.isFavorite());

        if (note.getId() == 0) {
            Log.d(Const.LOG, "create");
            long id = db.insert("note", null, cv);
            note.setId((int)id);
        } else {
            Log.d(Const.LOG, "update");
            String[] temp = new String[]{String.valueOf(note.getId())};
            db.update("note", cv, "id = ?", temp);
            db.delete("image", "id_note = ?", temp);
        }

        if (note.getImages() != null) {
            for (Image image : note.getImages()) {
                cv = new ContentValues();
                cv.put("pos", image.getPos());
                cv.put("path", image.getPath());
                cv.put("id_note", note.getId());
                db.insert("image", null, cv);
            }
        }

        db.close();
        Log.d(Const.LOG, "end saveNote");
    }
}
