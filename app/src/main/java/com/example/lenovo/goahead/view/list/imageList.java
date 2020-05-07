package com.example.lenovo.goahead.view.list;

import android.content.ClipData;
import android.net.Uri;

public class imageList {
    Uri image;

    public imageList(Uri image) {
        this.image = image;
    }

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }
}
