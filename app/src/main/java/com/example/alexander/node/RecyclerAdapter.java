package com.example.alexander.node;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alexander.node.Abstract.INoteRepository;
import com.example.alexander.node.Model.Note;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by W11B on 30.10.2017.
 */

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    protected INoteRepository nr;
    private int level;
    protected ArrayList<Note> notes;

    public RecyclerAdapter(INoteRepository nr) {
        this.nr = nr;
        this.level = 0;
    }
    public RecyclerAdapter(INoteRepository nr, int level) {
        this.nr = nr;
        this.level = level;
        notes = new ArrayList<>(nr.getChildsNote(level));
    }
    public void update(){
        notes = new ArrayList<>(nr.getChildsNote(level));
        notifyDataSetChanged();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new RecyclerViewHolder(view,parent.getContext());
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.bind(notes.get(position));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}
