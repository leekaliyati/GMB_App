package com.example.int_systems.gmb_app.GMB_services;

public class Mynotifications {
    public Mynotifications(String title, String description) {
        Title = title;
        this.description = description;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    String Title;
    String description;




}
