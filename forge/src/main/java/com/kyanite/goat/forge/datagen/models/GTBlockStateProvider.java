package com.kyanite.goat.forge.datagen.models;

import com.kyanite.goat.GolemsOfAllTypes;
import com.kyanite.goat.registry.blocks.GTBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
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
        buttonBlock(GTBlocks.COPPER_BUTTON, blockLocVanilla(Blocks.COPPER_BLOCK));
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

    public String getName(Block block) {
        return block.builtInRegistryHolder().key().location().getPath();
    }

    public ResourceLocation blockLoc(Supplier<? extends Block> block) {
        return new ResourceLocation(GolemsOfAllTypes.MOD_ID, "block/" + getName(block));
    }

    public ResourceLocation blockLocVanilla(Block block) {
        return new ResourceLocation("minecraft", "block/" + getName(block));
    }


    public ResourceLocation blockLoc(Supplier<? extends Block> block, String suffix) {
        return new ResourceLocation(GolemsOfAllTypes.MOD_ID, "block/" + getName(block) + "_" + suffix);
    }
}
