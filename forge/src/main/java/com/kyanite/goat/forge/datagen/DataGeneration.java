package com.kyanite.goat.forge.datagen;

import com.kyanite.goat.GolemsOfAllTypes;
import com.kyanite.goat.forge.datagen.advancements.GTAdvancementsProvider;
import com.kyanite.goat.forge.datagen.lang.ENLanguageProvider;
import com.kyanite.goat.forge.datagen.loot.GTLootTableProvider;
import com.kyanite.goat.forge.datagen.models.GTBlockStateProvider;
import com.kyanite.goat.forge.datagen.models.GTItemModelProvider;
import com.kyanite.goat.forge.datagen.recipes.CraftingRecipesProvider;
import com.kyanite.goat.forge.datagen.recipes.SmeltingRecipesProvider;
import com.kyanite.goat.forge.datagen.recipes.SmithingRecipesProvider;
import com.kyanite.goat.forge.datagen.recipes.StonecuttingRecipesProvider;
import com.kyanite.goat.forge.datagen.tags.GTBlockTagsProvider;
import com.kyanite.goat.forge.datagen.tags.GTItemTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GolemsOfAllTypes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGeneration {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeServer(), new GTAdvancementsProvider(generator, fileHelper));

        generator.addProvider(event.includeClient(), new ENLanguageProvider(generator, "en_us", false));
        generator.addProvider(event.includeClient(), new ENLanguageProvider(generator, "en_ud", true));

   //     generator.addProvider(event.includeServer(), new GTLootTableProvider(generator));

        generator.addProvider(event.includeClient(), new GTBlockStateProvider(generator, fileHelper));
        generator.addProvider(event.includeClient(), new GTItemModelProvider(generator, fileHelper));

        generator.addProvider(event.includeServer(), new CraftingRecipesProvider(generator));
        generator.addProvider(event.includeServer(), new SmeltingRecipesProvider(generator));
        generator.addProvider(event.includeServer(), new SmithingRecipesProvider(generator));
        generator.addProvider(event.includeServer(), new StonecuttingRecipesProvider(generator));

        GTBlockTagsProvider blockTags = new GTBlockTagsProvider(generator, fileHelper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new GTItemTagsProvider(generator, blockTags, fileHelper));
    }
}