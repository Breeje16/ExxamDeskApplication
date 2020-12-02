package com.breejemodi.exxxamdesk;

public class CategoryModel {

    private String imageUrl,title;

    public CategoryModel(String imageUrl,String title) {
        this.title = title;
        this.imageUrl=imageUrl;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

}
