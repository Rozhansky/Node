package com.example.alexander.node;

import com.example.alexander.node.Abstract.INoteRepository;

import java.util.ArrayList;

/**
 * Created by Alexander on 13.11.2017.
 */

public class RecycleAdapterFavorite extends RecyclerAdapter {
    @Override
    public void update() {
        notes = new ArrayList<>(nr.getFavoriteNotes());
        notifyDataSetChanged();
    }

    public RecycleAdapterFavorite(INoteRepository nr) {
        super(nr);
        notes = new ArrayList<>(nr.getFavoriteNotes());
    }
}
