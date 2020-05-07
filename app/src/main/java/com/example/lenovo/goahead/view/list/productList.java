package com.example.lenovo.goahead.view.list;

public class productList {
    String id,Title,Descripition,Image,price;

    public productList(String id, String title, String descripition, String image, String price) {
        this.id = id;
        Title = title;
        Descripition = descripition;
        Image = image;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
