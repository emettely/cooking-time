package com.emettely.cookingtime.pojo;

import java.util.List;

public class RecipeResult {
    private String title;
    private String author;
    private String description;
    private List<String> ingredientsList;
    private List<String> imagesList;
    private List<String> prepTimeList;
    private String totalTime;
    private List<String> recipeCuisine;
    private List<String> instructionsList;
    private String cookTime;
    private String recipeYield;
    private String url;

    private String raw;
    private Exception exception = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String authorList) {
        this.author = authorList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(List<String> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public List<String> getImagesList() {
        return imagesList;
    }

    public void setImagesList(List<String> imagesList) {
        this.imagesList = imagesList;
    }

    public List<String> getPrepTimeList() {
        return prepTimeList;
    }

    public void setPrepTimeList(List<String> prepTimeList) {
        this.prepTimeList = prepTimeList;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public List<String> getRecipeCuisine() {
        return recipeCuisine;
    }

    public void setRecipeCuisine(List<String> recipeCuisine) {
        this.recipeCuisine = recipeCuisine;
    }

    public List<String> getInstructionsList() {
        return instructionsList;
    }

    public void setInstructionsList(List<String> instructionsList) {
        this.instructionsList = instructionsList;
    }

    public String getCookTime() {
        return cookTime;
    }

    public void setCookTime(String cookTime) {
        this.cookTime = cookTime;
    }

    public String getRecipeYield() {
        return recipeYield;
    }

    public void setRecipeYield(String recipeYield) {
        this.recipeYield = recipeYield;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public boolean hasErrors() {
        return exception != null;
    }

    public Exception getException() {
        return exception;
    }

}
