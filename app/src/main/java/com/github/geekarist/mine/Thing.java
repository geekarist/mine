package com.github.geekarist.mine;

import android.os.Parcel;
import android.os.Parcelable;

public class Thing implements Parcelable {

    private String mDescription;
    private String mImagePath;

    public Thing(String description, String imagePath) {
        mDescription = description;
        mImagePath = imagePath;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String imagePath) {
        mImagePath = imagePath;
    }

    // region Parcelable

    public static final Creator<Thing> CREATOR = new Creator<Thing>() {
        @Override
        public Thing createFromParcel(Parcel in) {
            return new Thing(in);
        }

        @Override
        public Thing[] newArray(int size) {
            return new Thing[size];
        }
    };

    protected Thing(Parcel in) {
        mDescription = in.readString();
        mImagePath = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mDescription);
        dest.writeString(mImagePath);
    }

    // endregion

    // region Object

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Thing thing = (Thing) o;

        if (mDescription != null ? !mDescription.equals(thing.mDescription) : thing.mDescription != null) return false;
        return !(mImagePath != null ? !mImagePath.equals(thing.mImagePath) : thing.mImagePath != null);

    }

    @Override
    public int hashCode() {
        int result = mDescription != null ? mDescription.hashCode() : 0;
        result = 31 * result + (mImagePath != null ? mImagePath.hashCode() : 0);
        return result;
    }

    // endregion

}
