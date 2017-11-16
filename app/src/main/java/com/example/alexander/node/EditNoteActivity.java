package com.example.alexander.node;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.alexander.node.Abstract.INoteRepository;
import com.example.alexander.node.Concrete.FactoryNoteRepository;
import com.example.alexander.node.Model.Note;

public class EditNoteActivity extends AppCompatActivity {

    private CardView cardView;
    //private ScrollView scrollView;
    private Note note;
    private EditText editText;
    private INoteRepository nr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        android.support.v7.widget.Toolbar tb = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(tb);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //scrollView = (ScrollView) findViewById(R.id.scrollView);
        cardView = (CardView) findViewById(R.id.card3);
        Intent intent = getIntent();
         note = (Note)intent.getSerializableExtra("note");
        //ImageView saveNote = (ImageView) findViewById(R.id.saveNote);
        editText = (EditText) findViewById(R.id.editText);
        editText.setText(note.getNoteText());
        final FactoryNoteRepository fr = new FactoryNoteRepository(this);
        nr =  fr.getRepository();

        editText.setCursorVisible(false);
        //cardView.requestFocus();

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.requestFocus();
                editText.setSelection(editText.getText().length());
                System.out.println("Тап зашел");
                //editText.setSelection(editText.getText().length());
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                editText.setCursorVisible(true);


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.save_note){
            String str = editText.getText().toString();
            note.setNoteText(str);
            nr.saveNote(note);
            finish();
        }
        return true;
    }
}
