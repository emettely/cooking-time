package com.emettely.cookingtime.web;

import com.emettely.cookingtime.pojo.RecipeResult;

import java.io.IOException;

/* Using gateways to abstract integration,for when
* a particular service to retrieve recipe data needs to be used for
* different websites*/
public interface IRecipeGatewayService {
    public RecipeResult getRecipeFromUrl(String url) throws IOException;
}
