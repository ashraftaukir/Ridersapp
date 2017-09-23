package com.webxzen.ridersapp.model;


public class AdvertisementModel {

    private String subheader,tittle,content;

    public String getSubheader() {
        return subheader;
    }

    public void setSubheader(String subheader) {
        this.subheader = subheader;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public AdvertisementModel(String subheader, String tittle, String content) {
        this.subheader = subheader;
        this.tittle = tittle;
        this.content = content;
    }
}
