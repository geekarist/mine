package com.github.geekarist.mine.list;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.geekarist.mine.R;
import com.github.geekarist.mine.Thing;
import com.github.geekarist.mine.add.AddStuffActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

class StuffAdapter extends RecyclerView.Adapter<StuffViewHolder> implements StuffViewHolder.ItemSelectListener {
    private final Context mContext;
    private List<Thing> mThings;
    private SharedPreferences.OnSharedPreferenceChangeListener mChangeListener;
    private Gson mGson;
    private SharedPreferences mDefaultSharedPreferences;

    public StuffAdapter(Context context) {
        mContext = context;
        mGson = new Gson();
    }

    @Override
    public StuffViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_view_stuff_item, parent, false);
        return new StuffViewHolder(this, view);
    }

    @Override
    public void onBindViewHolder(StuffViewHolder holder, int position) {
        String description = mThings.get(position).getDescription();
        holder.setDescription(description);
        holder.setImage(mThings.get(position).getImagePath());
    }

    @Override
    public int getItemCount() {
        return mThings.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mDefaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(recyclerView.getContext());
        mChangeListener = (sharedPreferences, key) -> {
            if ("THINGS".equals(key)) {
                loadThings();
            }
        };
        mDefaultSharedPreferences.registerOnSharedPreferenceChangeListener(mChangeListener);
        loadThings();
    }

    private void loadThings() {
        String thingsJson = mDefaultSharedPreferences.getString("THINGS", "[]");
        Type typeOfThingList = new TypeToken<List<Thing>>() {
        }.getType();
        mThings = mGson.fromJson(thingsJson, typeOfThingList);
        notifyDataSetChanged();
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mDefaultSharedPreferences.unregisterOnSharedPreferenceChangeListener(mChangeListener);
    }

    @Override
    public void startChangingItem(int position) {
        Thing thing = mThings.get(position);
        Intent intent = AddStuffActivity.newIntent(mContext, thing);
        mContext.startActivity(intent);
    }
}
