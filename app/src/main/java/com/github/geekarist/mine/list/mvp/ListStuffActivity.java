package com.github.geekarist.mine.list.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.geekarist.mine.R;

public class ListStuffActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_list_stuff);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.list_stuff_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
