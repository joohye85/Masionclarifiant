package com.example.masionclarifiant;

public class Pair {
    String ingredient;
    Integer rate;

    public Pair(String ingredient, Integer rate){
        this.ingredient = ingredient;
        this.rate = rate;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }
}
