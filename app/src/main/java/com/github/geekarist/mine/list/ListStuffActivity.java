package com.github.geekarist.mine.list;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.geekarist.mine.R;
import com.github.geekarist.mine.Thing;
import com.github.geekarist.mine.add.AddStuffActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ListStuffActivity extends Activity {

    private View mAddButton;
    private Gson mGson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_list_stuff);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.list_stuff_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String thingsJson = defaultSharedPreferences.getString("THINGS", "[]");
        Type typeOfThingList = new TypeToken<List<Thing>>() {
        }.getType();
        mGson = new Gson();
        List<Thing> things = mGson.fromJson(thingsJson, typeOfThingList);
        StuffAdapter adapter = new StuffAdapter(things);
        mRecyclerView.setAdapter(adapter);
        mAddButton = findViewById(R.id.list_stuff_button_add);
        mAddButton.setOnClickListener(v -> ListStuffActivity.this.startActivity(new Intent(ListStuffActivity.this, AddStuffActivity.class)));
    }

}
