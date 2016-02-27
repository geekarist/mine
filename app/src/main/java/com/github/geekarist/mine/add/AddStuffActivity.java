package com.github.geekarist.mine.add;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.geekarist.mine.R;
import com.github.geekarist.mine.list.Thing;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddStuffActivity extends Activity {

    private static final int TAKE_PICTURE_REQUEST_CODE = 1;

    @Bind(R.id.add_stuff_item_description)
    EditText mItemDescriptionEdit;
    @Bind(R.id.add_stuff_item_image_view)
    ImageView mItemImage;

    private Gson mGson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_add_stuff);
        mGson = new Gson();
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (TAKE_PICTURE_REQUEST_CODE == requestCode && Activity.RESULT_OK == resultCode) {
            Glide.with(this).load(data.getData()).centerCrop().into(mItemImage);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.add_stuff_item_button_take_picture)
    public void takePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, TAKE_PICTURE_REQUEST_CODE);
        // TODO: take picture: http://developer.android.com/training/camera/photobasics.html
    }

    @OnClick(R.id.add_stuff_button_save)
    public void saveItem() {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String thingsJson = defaultSharedPreferences.getString("THINGS", "[]");
        Type typeOfThingList = new TypeToken<List<Thing>>() {
        }.getType();
        List<Thing> things = mGson.fromJson(thingsJson, typeOfThingList);
        String description = String.valueOf(mItemDescriptionEdit.getText());
        things.add(new Thing(description));
        String updatedThingsJson = mGson.toJson(things);
        defaultSharedPreferences.edit().putString("THINGS", updatedThingsJson).apply();
        finish();
    }
}
