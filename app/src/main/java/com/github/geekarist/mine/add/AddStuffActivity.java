package com.github.geekarist.mine.add;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

public class AddStuffActivity extends Activity {

    private static final int TAKE_PICTURE_REQUEST_CODE = 1;

    @Bind(R.id.add_stuff_item_description)
    EditText mItemDescriptionEdit;
    @Bind(R.id.add_stuff_item_image_view)
    ImageView mItemImage;

    private Gson mGson;
    private String mCurrentPhotoPath;

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
            Uri imageUri = Uri.fromFile(new File(mCurrentPhotoPath));
            Glide.with(this).load(imageUri).centerCrop().into(mItemImage);
        }
    }

    @OnClick(R.id.add_stuff_item_button_take_picture)
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

    @OnClick(R.id.add_stuff_button_save)
    public void saveItem() {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String thingsJson = defaultSharedPreferences.getString("THINGS", "[]");
        Type typeOfThingList = new TypeToken<List<Thing>>() {
        }.getType();
        List<Thing> things = mGson.fromJson(thingsJson, typeOfThingList);
        String description = String.valueOf(mItemDescriptionEdit.getText());
        things.add(new Thing(description, mCurrentPhotoPath));
        String updatedThingsJson = mGson.toJson(things);
        defaultSharedPreferences.edit().putString("THINGS", updatedThingsJson).apply();
        finish();
    }
}
