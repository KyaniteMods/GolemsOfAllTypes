package com.kyanite.goat.forge.datagen.tags;

import com.kyanite.goat.GolemsOfAllTypes;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

public class GTItemTagsProvider extends ItemTagsProvider {
    public GTItemTagsProvider(DataGenerator pGenerator, BlockTagsProvider pBlockTagsProvider, ExistingFileHelper existingFileHelper) {
        super(pGenerator, pBlockTagsProvider, GolemsOfAllTypes.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {

    }

    @NotNull
    @Override
    public String getName() {
        return "Item Tags";
    }
}