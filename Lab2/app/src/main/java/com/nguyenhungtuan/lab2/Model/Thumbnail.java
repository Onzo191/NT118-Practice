package com.nguyenhungtuan.lab2.Model;

import com.nguyenhungtuan.lab2.R;

public enum Thumbnail {
    L1("Thumbnail 1", R.drawable.thumbnail1),
    L2("Thumbnail 2", R.drawable.thumbnail2),
    L3("Thumbnail 3", R.drawable.thumbnail3),
    L4("Thumbnail 4", R.drawable.thumbnail4);

    private String name;
    private int img;

    Thumbnail(String name, int img) {
        this.name = name;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public int getImg() {
        return img;
    }
}