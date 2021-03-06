package com.github.geekarist.mine.list;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.geekarist.mine.R;
import com.github.geekarist.mine.edit.EditStuffActivity;

public class ListStuffActivity extends Activity {

    private View mAddButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_list_stuff);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.list_stuff_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        StuffAdapter adapter = new StuffAdapter(this);

        mRecyclerView.setAdapter(adapter);
        mAddButton = findViewById(R.id.list_stuff_button_add);
        mAddButton.setOnClickListener(v -> ListStuffActivity.this.startActivity(new Intent(ListStuffActivity.this, EditStuffActivity.class)));
    }

}
