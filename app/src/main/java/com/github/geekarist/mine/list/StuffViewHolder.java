package com.github.geekarist.mine.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.geekarist.mine.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StuffViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.view_stuff_item_description)
    TextView mDescriptionText;

    public StuffViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setDescription(String description) {
        mDescriptionText.setText(description);
    }
}
