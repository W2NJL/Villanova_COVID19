package com.w2njl.VillanovaCovid19.models;


public class RowItem {
    private String description;
    private int imageId;

    public int getTheme() {
        return theme;
    }

    public void setTheme(int theme) {
        this.theme = theme;
    }

    private int theme;

    public int getIcon() {
        return icon;
    }


    private int icon;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    private int color;
    private String title;

    public RowItem(int i, String str, String str2, int theme, int icon, int color) {
        this.imageId = i;
        this.title = str;
        this.description = str2;
        this.theme = theme;
        this.icon = icon;
        this.color = color;
    }

    public int getImageId() {
        return this.imageId;
    }


    public String getDesc() {
        return this.description;
    }


    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

}
