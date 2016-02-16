package com.github.geekarist.mine.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.geekarist.mine.R;

public class StuffViewHolder extends RecyclerView.ViewHolder {
    public StuffViewHolder(View itemView) {
        super(itemView);
    }

    public void setDescription(String description) {
        ((TextView) itemView.findViewById(R.id.stuff_item_description)).setText(description);
    }
}
