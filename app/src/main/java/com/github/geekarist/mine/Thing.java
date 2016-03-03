package com.github.geekarist.mine;

public class Thing {
    private final String mDescription;
    private final String mImagePath;

    public Thing(String description, String imagePath) {
        mDescription = description;
        mImagePath = imagePath;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getImagePath() {
        return mImagePath;
    }
}
