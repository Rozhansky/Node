package com.example.alexander.node;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.alexander.node.Model.Note;

/**
 * Created by W11B on 30.10.2017.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private TextView noteText;
    private TextView title;
    private int idNote;

    public RecyclerViewHolder(View itemView,final Context context) {
        super(itemView);
        noteText = (TextView) itemView.findViewById(R.id.note);
        title = (TextView) itemView.findViewById(R.id.title);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowNoteActivity.class);
                intent.putExtra("id_note", idNote);
                context.startActivity(intent);
            }
        });
    }

    public void bind(Note note) {
        noteText.setText(note.getNoteText());
        title.setText(note.getTitle());
        idNote = note.getId();
    }
}
