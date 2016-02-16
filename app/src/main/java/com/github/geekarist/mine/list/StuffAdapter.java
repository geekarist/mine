package com.github.geekarist.mine.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.geekarist.mine.R;

import java.util.List;

class StuffAdapter extends RecyclerView.Adapter<StuffViewHolder> {
    private final List<Stuff> mStuff;

    public StuffAdapter(List<Stuff> stuff) {
        mStuff = stuff;
    }

    @Override
    public StuffViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_view_stuff_item, parent);
        return new StuffViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StuffViewHolder holder, int position) {
        holder.setDescription(mStuff.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return mStuff.size();
    }
}
