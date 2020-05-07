package com.example.lenovo.goahead.view.list;

public class sellerList {
    String id,name,image,category_id;

    public sellerList(String id, String name,String image,String category_id) {
        this.id = id;
        this.name = name;
        this.image=image;
        this.category_id=category_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }
}
