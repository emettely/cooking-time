package com.emettely.cookingtime.service;

import com.emettely.cookingtime.pojo.RecipeDTO;
import com.emettely.cookingtime.pojo.RecipeResult;
import com.emettely.cookingtime.web.IRecipeGatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/* Staying away from the Active Record pattern as this does not work with
Repository Pattern. Although our data is not yet complex, using the AR pattern
means that there's tight coupling (isomorphic) relationship between the DB
and the data model. We don't want this, as this becomes hard to implement, as
the data model grows. We are using a Repository model instead.
*/
@Service
public class RecipeWebService {
    @Autowired
    @Qualifier("recipeJsonLdService")
    IRecipeGatewayService recipeGatewayService = new RecipeJsonLdService();

    @Autowired
    RecipeMapperService recipeMapperService;

    public RecipeDTO getRecipeFromUrl(String url) {
        RecipeDTO recipe = new RecipeDTO();
        try {
            RecipeResult recipeResult = recipeGatewayService.getRecipeFromUrl(url);
            recipe = recipeMapperService.toRecipeDTO(recipeResult);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e);
            // need to write something else to try or ignore it
        }
        return recipe;
    }
}
