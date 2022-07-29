package com.emettely.cookingtime.service;

import com.emettely.cookingtime.pojo.RecipeResult;
import com.emettely.cookingtime.web.IRecipeGatewayService;
import com.google.gson.JsonIOException;
import com.google.schemaorg.JsonLdSerializer;
import com.google.schemaorg.JsonLdSyntaxException;
import com.google.schemaorg.core.Recipe;
import com.google.schemaorg.core.Thing;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class RecipeJsonLdService implements IRecipeGatewayService {
    @Autowired
    RecipeMapperService recipeMapperService;

    private String getJsonLd(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            String cssQuery = "script[type=\"application/ld+json\"]";
            Element recipeEl = doc.select(cssQuery).first();
            if (recipeEl != null && recipeEl.childNodeSize() > 0) {
                return recipeEl.childNode(0).toString();
            } else {
                System.out.println("No children nodes found for JSON-LD in HTML");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while getting JSON-LD: " + e);
        }

        return "";
    }

    public RecipeResult getRecipeFromUrl(String url) {
        RecipeResult recipeResult;
        String jsonLd = getJsonLd(url);
        if (jsonLd.isEmpty()) {
            recipeResult = new RecipeResult();
            recipeResult.setException(new IllegalArgumentException("Could not get JSON-LD data"));
            recipeResult.setRaw(jsonLd);
            return recipeResult;
        }
        try {
            Recipe recipe = parseRecipePage(jsonLd);
            recipeResult = recipeMapperService.toRecipeResult(recipe);
            recipeResult.setRaw(jsonLd);
        } catch (JsonLdSyntaxException | JsonIOException exception) {
            recipeResult = new RecipeResult();
            recipeResult.setException(exception);
            recipeResult.setRaw(jsonLd);
        }

        return recipeResult;
    }

    // https://www.baeldung.com/json-linked-data
    private Recipe parseRecipePage(String jsonLd) throws JsonLdSyntaxException, JsonIOException {
        JsonLdSerializer serializer = new JsonLdSerializer(true /* setPrettyPrinting */);
        try {
            List<Thing> actual = serializer.deserialize(jsonLd);
            if ("http://schema.org/Recipe".equals(actual.get(0).getFullTypeName())) {
                return (Recipe) actual.get(0);
            }
        } catch (JsonLdSyntaxException | JsonIOException e) {
            // Errors related to JSON-LD format and schema.org terms in JSON-LD
            throw e;
        }
        return null;
    }

}

