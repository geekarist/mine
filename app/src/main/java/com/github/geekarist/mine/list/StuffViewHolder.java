package com.github.geekarist.mine.list;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.github.geekarist.mine.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StuffViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.view_stuff_item_description)
    TextView mDescriptionText;
    @Bind(R.id.view_stuff_item_image)
    ImageView mImageView;
    private RequestManager mGlide;

    public StuffViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mGlide = Glide.with(itemView.getContext());
    }

    public void setDescription(String description) {
        mDescriptionText.setText(description);
    }

    public void setImage(String imagePath) {
        if (imagePath != null) {
            Uri uri = Uri.parse(imagePath);
            mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mGlide.load(uri).centerCrop().into(mImageView);
        }
    }
}
