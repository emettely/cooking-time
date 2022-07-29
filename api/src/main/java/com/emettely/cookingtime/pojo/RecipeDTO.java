package com.emettely.cookingtime.pojo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class RecipeDTO {
    private String id;
    private String title;
    private String description;
    private boolean published;
    private String url;
    private String imageUrl;

    private String author;
    private BigInteger totalTimeInMinutes;
    private BigInteger cookTimeInMinutes;

    private List<String> ingredients;

    private String cuisine;

    private String category;

    private List<String> instructions;

    private BigInteger servings;

    private BigInteger daysBeforeExpiry;

    private BigDecimal kcals;

    private String raw;

    public RecipeDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setPublished(boolean published) {
        this.published = published;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigInteger getTotalTimeInMinutes() {
        return totalTimeInMinutes;
    }

    public void setTotalTimeInMinutes(BigInteger totalTimeInMinutes) {
        this.totalTimeInMinutes = totalTimeInMinutes;
    }

    public BigInteger getCookTimeInMinutes() {
        return cookTimeInMinutes;
    }

    public void setCookTimeInMinutes(BigInteger cookTimeInMinutes) {
        this.cookTimeInMinutes = cookTimeInMinutes;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }

    public BigInteger getServings() {
        return servings;
    }

    public void setServings(BigInteger servings) {
        this.servings = servings;
    }

    public BigInteger getDaysBeforeExpiry() {
        return daysBeforeExpiry;
    }

    public void setDaysBeforeExpiry(BigInteger daysBeforeExpiry) {
        this.daysBeforeExpiry = daysBeforeExpiry;
    }

    public BigDecimal getKcals() {
        return kcals;
    }

    public void setKcals(BigDecimal kcals) {
        this.kcals = kcals;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
