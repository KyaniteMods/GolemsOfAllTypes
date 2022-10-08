package com.kyanite.goat.registry.blocks;

import com.kyanite.goat.GolemsOfAllTypes;
import com.kyanite.goat.platform.RegistryHelper;
import com.kyanite.goat.registry.blocks.custom.GTButtonBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class GTBlocks {
    public static final Supplier<ButtonBlock> COPPER_BUTTON = registerBlock("copper_button", true, () -> new GTButtonBlock(false, BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON)));

    public static <T extends Block> Supplier<T> registerBlock(String name, boolean createItem, Supplier<T> block) {
        Supplier<T> toReturn = RegistryHelper.registerBlock(name, block);
        if (createItem) RegistryHelper.registerItem(name, () -> new BlockItem(toReturn.get(), new Item.Properties().tab(GolemsOfAllTypes.TAB)));
        return toReturn;
    }

    public static void register() {}
}
