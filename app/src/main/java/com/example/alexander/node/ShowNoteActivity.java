package com.example.alexander.node;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alexander.node.Abstract.INoteRepository;
import com.example.alexander.node.Concrete.FactoryNoteRepository;
import com.example.alexander.node.Model.Note;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ShowNoteActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager verticalLinearLayoutManager;
    private RecyclerAdapter adapter;
    private ImageView editNote;
    private ImageView deleteNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);

        recyclerView = (RecyclerView)findViewById(R.id.recycler2);
        LinearLayoutManager verticalLinearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(verticalLinearLayoutManager);

        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);

        FactoryNoteRepository f = new FactoryNoteRepository(this);
        final INoteRepository r = f.getRepository();
        Intent intent = getIntent();
        final int idNote = intent.getIntExtra("id_note",0);
        adapter.addAll(r.getChildsNote(idNote));

        Note note = r.getNote(idNote);
        TextView noteText = (TextView) findViewById(R.id.note2);
        noteText.setText(note.getNoteText());
        TextView title = (TextView) findViewById(R.id.title2);
        title.setText(note.getTitle());

        editNote = (ImageView) findViewById(R.id.editNote);
        editNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowNoteActivity.this, EditNoteActivity.class);
                startActivity(intent);
            }
        });

        deleteNote = (ImageView) findViewById(R.id.deleteNote);
        deleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               r.deleteNote(idNote);
            }
        });

    }

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{
        private ArrayList<Note> items = new ArrayList<>();

        public void addAll(Collection<Note> fakeItems) {
            int pos = getItemCount();
            this.items.addAll(fakeItems);
            notifyItemRangeInserted(pos, this.items.size());
        }

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);

            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolder holder, int position){
            holder.bind(items.get(position));
        }

        @Override
        public int getItemCount(){
            return items.size();
        }
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView noteText;
        private TextView title;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            noteText = (TextView) itemView.findViewById(R.id.note);
            title = (TextView) itemView.findViewById(R.id.title);

        }

        public void bind(Note note) {
            noteText.setText(note.getNoteText());
            title.setText(note.getTitle());
        }
    }
}
