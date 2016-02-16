package com.github.geekarist.mine.list;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.geekarist.mine.R;

import java.util.Arrays;

public class ListStuffActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_list_stuff);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.list_stuff_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new StuffAdapter(Arrays.asList(
                new Stuff("Kindle, écran tactile 6 (15,2 cm) antireflet, Wi-Fi (Noir)"),
                new Stuff("Apple iPad Air - 16 Go - Gris Sidéral"))));
    }

}