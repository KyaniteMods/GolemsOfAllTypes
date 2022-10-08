package com.kyanite.goat.forge.datagen.recipes;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class SmeltingRecipesProvider extends RecipeProvider implements IConditionBuilder {
    public SmeltingRecipesProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {

    }

    @NotNull
    @Override
    public String getName() {
        return "Smelting Recipes";
    }
}