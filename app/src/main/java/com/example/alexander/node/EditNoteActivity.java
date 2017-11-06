package com.example.alexander.node;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
        final CardView cardView = (CardView) findViewById(R.id.card3);
        Intent intent = getIntent();
        final Note note = (Note)intent.getSerializableExtra("note");
        ImageView saveNote = (ImageView) findViewById(R.id.saveNote);
        final EditText editText = (EditText) findViewById(R.id.editText);
        editText.setText(note.getNoteText());
        final FactoryNoteRepository fr = new FactoryNoteRepository(this);
        final INoteRepository nr =  fr.getRepository();


        /*editText.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);*/

        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = editText.getText().toString();
                note.setNoteText(str);
                nr.saveNote(note);
                finish();
            }
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.requestFocus();
                editText.setSelection(editText.getText().length());
                //editText.setSelection(editText.getText().length());
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);


            }
        });

    }
}
