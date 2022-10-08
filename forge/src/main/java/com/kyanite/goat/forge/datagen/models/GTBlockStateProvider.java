package com.kyanite.goat.forge.datagen.models;

import com.kyanite.goat.GolemsOfAllTypes;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public class GTBlockStateProvider extends BlockStateProvider {
    public GTBlockStateProvider(DataGenerator pGenerator, ExistingFileHelper pExistingFileHelper) {
        super(pGenerator, GolemsOfAllTypes.MOD_ID, pExistingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

    }

    public void fenceBlock(Supplier<FenceBlock> block, ResourceLocation texture) {
        super.fenceBlock(block.get(), texture);
        models().fenceInventory(getName(block) + "_inventory", texture);
    }

    public void buttonBlock(Supplier<ButtonBlock> block, ResourceLocation texture) {
        super.buttonBlock(block.get(), texture);
        models().buttonInventory(getName(block) + "_inventory", texture);
    }

    public void wallBlock(Supplier<WallBlock> block, ResourceLocation texture) {
        super.wallBlock(block.get(), texture);
        models().wallInventory(getName(block) + "_inventory", texture);
    }

    public String getName(Supplier<? extends Block> block) {
        return block.get().builtInRegistryHolder().key().location().getPath();
    }

    public ResourceLocation blockLoc(Supplier<? extends Block> block) {
        return new ResourceLocation(GolemsOfAllTypes.MOD_ID, "block/" + getName(block));
    }


    public ResourceLocation blockLoc(Supplier<? extends Block> block, String suffix) {
        return new ResourceLocation(GolemsOfAllTypes.MOD_ID, "block/" + getName(block) + "_" + suffix);
    }
}
