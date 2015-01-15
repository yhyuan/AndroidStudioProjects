package com.example.yuanje.photogallery;

/**
 * Created by yuanje on 01/14/2015.
 */
public class GalleryItem {
    private String mCaption;
    private String mId;
    private String mUrl;


    public String getCaption() {
        return mCaption;
    }

    public void setCaption(String caption) {
        this.mCaption = caption;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public String toString() {
        return mCaption;
    }
}
