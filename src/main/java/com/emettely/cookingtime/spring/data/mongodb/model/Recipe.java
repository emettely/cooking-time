package com.emettely.cookingtime.spring.data.mongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "recipes")
public class Recipe {
    @Id
    private String id;
    private String title;
    private String description;
    private boolean published;

    public Recipe() {
    }

    public Recipe(String title, String description, boolean published) {
        this.title = title;
        this.description = description;
        this.published = published;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean isPublished) {
        this.published = isPublished;
    }

    @Override
    public String toString() {
        return "Recipe [id=" + id + ", title=" + title + ", desc=" + description + ", published=" + published + "]";
    }
}