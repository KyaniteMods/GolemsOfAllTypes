package com.kyanite.goat.forge.datagen.models;

import com.kyanite.goat.GolemsOfAllTypes;
import com.kyanite.goat.registry.blocks.GTBlocks;
import com.kyanite.goat.registry.items.GTItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public class GTItemModelProvider extends ItemModelProvider {
    private final ModelFile GENERATED = getExistingFile(mcLoc("item/generated"));
    private final ModelFile HANDHELD = getExistingFile(mcLoc("item/handheld"));

    public GTItemModelProvider(DataGenerator pGenerator, ExistingFileHelper pExistingFileHelper) {
        super(pGenerator, GolemsOfAllTypes.MOD_ID, pExistingFileHelper);
    }

    @Override
    protected void registerModels() {
        blockModel(GTBlocks.COPPER_BUTTON, "inventory");
        withExistingParent(getName(GTItems.COPPER_GOLEM_SPAWN_EGG), mcLoc("item/template_spawn_egg"));
    }

    public void blockModel(Supplier<? extends Block> block) {
        withExistingParent(getName(block.get()), modLoc("block/" + getName(block.get())));
    }

    public String getName(Block block) {
        return block.builtInRegistryHolder().key().location().getPath();
    }

    public String getName(Supplier<? extends Item> item) {
        return item.get().builtInRegistryHolder().key().location().getPath();
    }

    public void blockModel(Supplier<? extends Block> block, String suffix) {
        withExistingParent(getName(block.get()), modLoc("block/" + getName(block.get()) + "_" + suffix));
    }

    public void itemModel(Supplier<? extends Item> item, ModelFile modelFile) {
        getBuilder(getName(item)).parent(modelFile).texture("layer0", "item/" + getName(item));
    }
}