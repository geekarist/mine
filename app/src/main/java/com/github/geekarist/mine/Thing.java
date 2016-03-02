package com.github.geekarist.mine;

public class Thing {
    private final String mDescription;
    private final String mCurrentPhotoPath;

    public Thing(String description, String currentPhotoPath) {
        mDescription = description;
        mCurrentPhotoPath = currentPhotoPath;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getCurrentPhotoPath() {
        return mCurrentPhotoPath;
    }
}
