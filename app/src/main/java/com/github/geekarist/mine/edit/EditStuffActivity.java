package com.github.geekarist.mine.edit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.annimon.stream.function.Consumer;
import com.bumptech.glide.Glide;
import com.github.geekarist.mine.R;
import com.github.geekarist.mine.Thing;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditStuffActivity extends AppCompatActivity {

    private static final String EXTRA_THING = "THING";

    private static final int TAKE_PICTURE_REQUEST_CODE = 1;

    @Bind(R.id.edit_stuff_item_description)
    EditText mItemDescriptionEdit;
    @Bind(R.id.edit_stuff_item_image_view)
    ImageView mItemImage;
    @Bind(R.id.my_toolbar)
    Toolbar mToolbar;

    private Gson mGson;
    private String mCurrentPhotoPath;

    public static Intent newIntent(Context context, Thing thing) {
        Intent intent = new Intent(context, EditStuffActivity.class);
        intent.putExtra(EXTRA_THING, thing);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_edit_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.delete == item.getItemId()) {
            modifyThings((things) -> {
                Thing thingToRemove = getIntent().getParcelableExtra(EXTRA_THING);
                things.remove(thingToRemove);
            });
        }
        finish();
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_edit_stuff);
        mGson = new Gson();
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        Thing thingToEdit = getIntent().getParcelableExtra(EXTRA_THING);
        if (thingToEdit != null) {
            mItemDescriptionEdit.setText(thingToEdit.getDescription());
            mCurrentPhotoPath = thingToEdit.getImagePath();
            if (mCurrentPhotoPath != null) {
                Uri imageUri = Uri.parse(mCurrentPhotoPath);
                mItemImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(this).load(imageUri).placeholder(R.drawable.ic_image_black_24dp).centerCrop().into(mItemImage);
            } else {
                mItemImage.setScaleType(ImageView.ScaleType.CENTER);
                Glide.with(this).load(R.drawable.ic_image_black_24dp).into(mItemImage);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (TAKE_PICTURE_REQUEST_CODE == requestCode && Activity.RESULT_OK == resultCode) {
            Uri imageUri = Uri.parse(mCurrentPhotoPath);
            mItemImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(this).load(imageUri).centerCrop().into(mItemImage);
        }
    }

    @OnClick(R.id.edit_stuff_item_button_take_picture)
    public void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            File file = null;
            try {
                file = createImageFile();
            } catch (IOException e) {
                Log.e(getClass().getSimpleName(), "Error while taking picture", e);
                Toast.makeText(this, "Error while taking picture", Toast.LENGTH_LONG).show();
            }
            if (file != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(intent, TAKE_PICTURE_REQUEST_CODE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    @OnClick(R.id.edit_stuff_button_save)
    public void saveItem() {
        modifyThings((things) -> {
            Thing thingToEdit = getIntent().getParcelableExtra(EXTRA_THING);
            String description = String.valueOf(mItemDescriptionEdit.getText());
            if (thingToEdit != null) {
                int position = things.indexOf(thingToEdit);
                thingToEdit.setDescription(description);
                thingToEdit.setImagePath(mCurrentPhotoPath);
                things.set(position, thingToEdit);
            } else {
                things.add(new Thing(description, mCurrentPhotoPath));
            }
        });
        finish();
    }

    private void modifyThings(Consumer<List<Thing>> modification) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String thingsJson = defaultSharedPreferences.getString("THINGS", "[]");
        Type typeOfThingList = new TypeToken<List<Thing>>() {
        }.getType();
        List<Thing> things = mGson.fromJson(thingsJson, typeOfThingList);
        modification.accept(things);
        String updatedThingsJson = mGson.toJson(things);
        defaultSharedPreferences.edit().putString("THINGS", updatedThingsJson).apply();
    }
}
