package com.example.lenovo.goahead.view.list;

public class catList {
    String logo;
    String url;
    boolean fav=false;
    int id,favNum;
    String name;
    public catList(boolean fav)
    {
        this.fav=fav;

    }
    public catList(String logo,String url,String name,int id,int favNum ) {
        this.logo = logo;
        this.url=url;
        this.name=name;
        this.id=id;
        this.favNum=favNum;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFavNum() {
        return favNum;
    }

    public void setFavNum(int favNum) {
        this.favNum = favNum;
    }
}
