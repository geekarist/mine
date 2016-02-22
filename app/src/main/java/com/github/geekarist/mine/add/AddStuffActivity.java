package com.github.geekarist.mine.add;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.EditText;

import com.github.geekarist.mine.R;
import com.github.geekarist.mine.list.Thing;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class AddStuffActivity extends Activity {

    @Bind(R.id.stuff_item_description)
    EditText itemDescriptionEdit;

    private Gson mGson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_add_stuff);
        mGson = new Gson();
    }

    @OnClick(R.id.add_stuff_button_save)
    public void saveItem() {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String thingsJson = defaultSharedPreferences.getString("THINGS", "[]");
        Type typeOfThingList = new TypeToken<List<Thing>>() {
        }.getType();
        List<Thing> things = mGson.fromJson(thingsJson, typeOfThingList);
        String description = String.valueOf(itemDescriptionEdit.getText());
        things.add(new Thing(description));
        String updatedThingsJson = mGson.toJson(things);
        defaultSharedPreferences.edit().putString("THINGS", updatedThingsJson).apply();
        finish();
    }
}
