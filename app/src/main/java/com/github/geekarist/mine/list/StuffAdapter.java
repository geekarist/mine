package com.github.geekarist.mine.list;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.geekarist.mine.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

class StuffAdapter extends RecyclerView.Adapter<StuffViewHolder> {
    private List<Thing> mThings;
    private SharedPreferences.OnSharedPreferenceChangeListener mChangeListener;
    private Gson mGson;

    public StuffAdapter() {
        this(Collections.emptyList());
    }

    public StuffAdapter(List<Thing> things) {
        mThings = things;
        mGson = new Gson();
    }

    @Override
    public StuffViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_view_stuff_item, parent, false);
        return new StuffViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StuffViewHolder holder, int position) {
        String description = mThings.get(position).getDescription();
        holder.setDescription(description);
    }

    @Override
    public int getItemCount() {
        return mThings.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(recyclerView.getContext());
        mChangeListener = (sharedPreferences, key) -> {
            if ("THINGS".equals(key)) {
                String thingsJson = defaultSharedPreferences.getString("THINGS", "[]");
                Type typeOfThingList = new TypeToken<List<Thing>>() {
                }.getType();
                mThings = mGson.fromJson(thingsJson, typeOfThingList);
                notifyDataSetChanged();
            }
        };
        defaultSharedPreferences.registerOnSharedPreferenceChangeListener(mChangeListener);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(recyclerView.getContext());
        defaultSharedPreferences.unregisterOnSharedPreferenceChangeListener(mChangeListener);
    }
}
