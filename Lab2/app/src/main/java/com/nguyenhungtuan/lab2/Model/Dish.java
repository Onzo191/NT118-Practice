package com.nguyenhungtuan.lab2.Model;

public class Dish {

    private String name;
    private int imageResId; // Resource ID of the thumbnail image
    private boolean isPromotion;

    public Dish(String name, int imageResId, boolean isPromotion) {
        this.name = name;
        this.imageResId = imageResId;
        this.isPromotion = isPromotion;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public boolean isPromotion() {
        return isPromotion;
    }

    public void setPromotion(boolean promotion) {
        isPromotion = promotion;
    }
}
