package com.kyanite.goat.forge.datagen.tags;

import com.kyanite.goat.GolemsOfAllTypes;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

public class GTBlockTagsProvider extends BlockTagsProvider {
    public GTBlockTagsProvider(DataGenerator pGenerator, ExistingFileHelper pExistingFileHelper) {
        super(pGenerator, GolemsOfAllTypes.MOD_ID, pExistingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags() {
        super.addTags();
    }

    @NotNull
    @Override
    public String getName() {
        return "Block Tags";
    }
}