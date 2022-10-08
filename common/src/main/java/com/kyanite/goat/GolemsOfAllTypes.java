package com.kyanite.goat;

import com.kyanite.goat.platform.RegistryHelper;
import com.kyanite.goat.registry.blocks.GTBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import software.bernie.geckolib3.GeckoLib;

public class GolemsOfAllTypes {
    public static final String MOD_ID = "goat";
    public static final CreativeModeTab TAB = RegistryHelper.registerCreativeModeTab(() -> new ItemStack(Items.COPPER_INGOT));

    public static void init() {
        GeckoLib.initialize();
        
        GTBlocks.register();
    }
}