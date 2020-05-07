package com.example.lenovo.goahead.view.list;

public class otherProductList {
    String id,Img,Title,Descripition;

    public otherProductList(String id, String img, String title, String descripition) {
        this.id = id;
        Img = img;
        Title = title;
        Descripition = descripition;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescripition() {
        return Descripition;
    }

    public void setDescripition(String descripition) {
        Descripition = descripition;
    }
}
