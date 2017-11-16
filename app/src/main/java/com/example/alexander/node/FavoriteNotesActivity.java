package com.example.alexander.node;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.alexander.node.Abstract.INoteRepository;
import com.example.alexander.node.Concrete.NoteRepository;
import com.example.alexander.node.Model.Note;

/**
 * Created by Alexander on 13.11.2017.
 */

public class FavoriteNotesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecycleAdapterFavorite adapter;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        LinearLayoutManager verticalLinearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(verticalLinearLayoutManager);
        INoteRepository rn = new NoteRepository(this);
        adapter = new RecycleAdapterFavorite(rn);

        recyclerView.setAdapter(adapter);

        Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.update();
    }
}
