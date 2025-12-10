package com.example.alfappcomp710;

import android.graphics.Bitmap;
import android.provider.MediaStore;

public class Image2DB {

    public String imageId;
    public String path;


    public Image2DB() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Image2DB(String imageId, String path) {
        this.imageId = imageId;
        this.path = path;

    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
