package com.emettely.cookingtime.service;

import com.emettely.cookingtime.pojo.RecipeDTO;
import com.emettely.cookingtime.pojo.RecipeResult;
import com.emettely.cookingtime.spring.data.mongodb.model.Recipe;
import com.google.common.collect.ImmutableList;
import com.google.schemaorg.SchemaOrgType;
import com.google.schemaorg.core.HowToStep;
import com.google.schemaorg.core.Person;
import com.google.schemaorg.core.datatype.Text;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.util.Span;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Service
public class RecipeMapperService {

    private final Predicate<SchemaOrgType> isTextClass = schemaOrgType -> schemaOrgType.getClass().equals(Text.class);
    private final Predicate<SchemaOrgType> isHowToStepClass = schemaOrgType -> HowToStep.class.isAssignableFrom(schemaOrgType.getClass());
    private final Predicate<SchemaOrgType> isPersonClass = schemaOrgType -> Person.class.isAssignableFrom(schemaOrgType.getClass());

    // https://www.baeldung.com/apache-open-nlp
    BigInteger parseMinutes(String s) throws IOException {
        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        String[] tokens = tokenizer.tokenize(s);
        InputStream inputStreamTimeFinder = getClass().getResourceAsStream("/models/en-ner-time.bin");
        TokenNameFinderModel model = new TokenNameFinderModel(inputStreamTimeFinder);
        NameFinderME nameFinderME = new NameFinderME(model);
        List<Span> spans = Arrays.asList(nameFinderME.find(tokens));
        return null;
    }

    public RecipeResult toRecipeResult(com.google.schemaorg.core.Recipe recipe) {
        RecipeResult result = new RecipeResult();

        if (recipe == null) {
            return result;
        }

        String recipeName = orgTypeToString(recipe.getNameList());
        String thumbnailUrl = orgTypeToString(recipe.getThumbnailUrlList());
        String totalTime = orgTypeToString(recipe.getTotalTimeList());
        String cookTime = orgTypeToString(recipe.getCookTimeList());
        String recipeYield = orgTypeToString(recipe.getRecipeYieldList());
        String url = orgTypeToString(recipe.getUrlList());
        String author = orgTypeToString(recipe.getAuthorList());
        String description = orgTypeToString(recipe.getDescriptionList());

        List<String> prepTimeList = orgTypesToStrings(recipe.getPrepTimeList());
        List<String> ingredientsList = orgTypesToStrings(recipe.getRecipeIngredientList());
        List<String> recipeCuisine = orgTypesToStrings(recipe.getRecipeCuisineList());
        List<String> instructionsList = orgTypesToStrings(recipe.getRecipeInstructionsList());

        List<String> imagesList;
        if (thumbnailUrl == null || thumbnailUrl.isEmpty()) {
            imagesList = orgTypesToStrings(recipe.getImageList());
        } else {
            imagesList = List.of(thumbnailUrl);
        }

        result.setAuthor(author);
        result.setTitle(recipeName);
        result.setImagesList(imagesList);
        result.setIngredientsList(ingredientsList);
        result.setPrepTimeList(prepTimeList);
        result.setTotalTime(totalTime);
        result.setRecipeCuisine(recipeCuisine);
        result.setInstructionsList(instructionsList);
        result.setCookTime(cookTime);
        result.setRecipeYield(recipeYield);
        result.setUrl(url);
        result.setDescription(description);

        return result;
    }

    private List<String> orgTypesToStrings(ImmutableList<SchemaOrgType> immutableList) {
        if (immutableList.isEmpty()) {
            return null;
        }
        List<String> stringList = new ArrayList<>();
        for (SchemaOrgType orgType : immutableList) {
            if (isTextClass.test(orgType)) {
                String value = ((Text) orgType).getValue();
                stringList.add(value);
            } else if (isHowToStepClass.test(orgType)) {
                String value = orgTypeToString(((HowToStep) orgType).getTextList());
                stringList.add(value);
            } else {
                throw new IllegalArgumentException(String.format("Unexpected class type of orgType: %s", orgType));
            }
        }
        return stringList;
    }

    private String orgTypeToString(ImmutableList<SchemaOrgType> immutableList) {
        if (immutableList.isEmpty()) {
            return null;
        }
        SchemaOrgType orgType = immutableList.get(0);
        if (isTextClass.test(orgType)) {
            return ((Text) orgType).getValue();
        } else if (isPersonClass.test(orgType)) {
            return orgTypeToString(((Person) orgType).getNameList());
        } else {
            throw new IllegalArgumentException(String.format("Unexpected class type of orgType: %s", orgType));
        }
    }

    public RecipeDTO toRecipeDTO(RecipeResult recipeResult) throws IOException {
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setId(null);
        recipeDTO.setCategory("");

        String cookTime = recipeResult.getCookTime();
        BigInteger cookTimeInMinutes = parseMinutes(cookTime);

        recipeDTO.setCookTimeInMinutes(cookTimeInMinutes);
        recipeDTO.setCuisine("");
        recipeDTO.setDaysBeforeExpiry(null);
        recipeDTO.setDescription(recipeResult.getDescription());
        recipeDTO.setImageUrl("");
        recipeDTO.setIngredients(List.of());
        recipeDTO.setInstructions(List.of());
        recipeDTO.setKcals(null);
        recipeDTO.setPublished(true);
        recipeDTO.setRaw("");
        recipeDTO.setServings(null);
        recipeDTO.setTitle(recipeResult.getTitle());
        recipeDTO.setTotalTimeInMinutes(null);
        recipeDTO.setUrl(recipeResult.getUrl());
        recipeDTO.setAuthor(null);
        return recipeDTO;
    }

    public RecipeDTO toRecipeDTO(Recipe recipe) {
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setId(recipe.getId());
        recipeDTO.setCategory("");
        recipeDTO.setCookTimeInMinutes(null);
        recipeDTO.setCuisine("");
        recipeDTO.setDaysBeforeExpiry(null);
        recipeDTO.setDescription(recipe.getDescription());
        recipeDTO.setImageUrl("");
        recipeDTO.setIngredients(List.of());
        recipeDTO.setInstructions(List.of());
        recipeDTO.setKcals(null);
        recipeDTO.setPublished(true);
        recipeDTO.setRaw("");
        recipeDTO.setServings(null);
        recipeDTO.setTitle(recipe.getTitle());
        recipeDTO.setTotalTimeInMinutes(null);
        recipeDTO.setUrl(recipe.getUrl());
        recipeDTO.setAuthor(null);
        return recipeDTO;
    }
}
