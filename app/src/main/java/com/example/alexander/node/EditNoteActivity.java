package com.example.alexander.node;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alexander.node.Abstract.INoteRepository;
import com.example.alexander.node.Concrete.FactoryNoteRepository;
import com.example.alexander.node.Model.Note;

public class EditNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        Intent intent = getIntent();
        final Note note = (Note)intent.getSerializableExtra("note");
        ImageView saveNote = (ImageView) findViewById(R.id.saveNote);
        final TextView editText = (TextView) findViewById(R.id.editText);
        editText.setText(note.getNoteText());
        final FactoryNoteRepository fr = new FactoryNoteRepository(this);
        final INoteRepository nr =  fr.getRepository();

        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = editText.getText().toString();
                note.setNoteText(str);
                nr.saveNote(note);
                finish();
            }
        });

    }
}
