package com.example.alexander.node;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.alexander.node.Abstract.INoteRepository;
import com.example.alexander.node.Concrete.FactoryNoteRepository;
import com.example.alexander.node.Model.Image;
import com.example.alexander.node.Model.Note;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ShowNoteActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager verticalLinearLayoutManager;
    private RecyclerAdapter adapter;
    private ImageView editNote;
    private ImageView deleteNote;
    TextView noteText;
    TextView title;
    private Note note;
    INoteRepository r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);

        android.support.v7.widget.Toolbar tb = (android.support.v7.widget.Toolbar) findViewById(R.id.tb);
        setSupportActionBar(tb);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        FactoryNoteRepository f = new FactoryNoteRepository(this);
        r = f.getRepository();

        recyclerView = (RecyclerView)findViewById(R.id.recycler2);
        LinearLayoutManager verticalLinearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(verticalLinearLayoutManager);

        Intent intent = getIntent();
        final int idNote = intent.getIntExtra("id_note",0);

        adapter =  new RecyclerAdapter(r ,idNote);
        recyclerView.setAdapter(adapter);

        //adapter.addAll(r.getChildsNote(idNote));
        note = r.getNote(idNote);
        noteText = (TextView) findViewById(R.id.note2);
        noteText.setText(note.getNoteText());
        title = (TextView) findViewById(R.id.title2);
        title.setText(note.getTitle());

        editNote = (ImageView) findViewById(R.id.editNote);
        editNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowNoteActivity.this, EditNoteActivity.class);
                intent.putExtra("note",note);
                startActivity(intent);
            }
        });

        deleteNote = (ImageView) findViewById(R.id.deleteNote);
        deleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r.deleteNote(idNote);
                ShowNoteActivity.this.finish();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabNoteList);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowNoteActivity.this, EditNoteActivity.class);
                Note temp = new Note();
                temp.setIdParent(note.getId());
                intent.putExtra("note", temp);
                startActivity(intent);
            }
        });

        ImageView im = (ImageView)findViewById(R.id.imageNote);
        for (Image image : note.getImages()) {
            try {
                im.setImageBitmap(Const.getBitMap(image.getPath()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.d(Const.LOG,e.getMessage());
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        note = r.getNote(note.getId());
        adapter.update();
        noteText.setText(note.getNoteText());
        title.setText(note.getTitle());
    }
}
