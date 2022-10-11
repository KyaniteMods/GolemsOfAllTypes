package com.kyanite.goat;

import com.kyanite.goat.platform.RegistryHelper;
import com.kyanite.goat.registry.blocks.GTBlocks;
import com.kyanite.goat.registry.entities.GTEntities;
import com.kyanite.goat.registry.entities.custom.ChestGolem;
import com.kyanite.goat.registry.entities.custom.CopperGolem;
import com.kyanite.goat.registry.entities.custom.TuffGolem;
import com.kyanite.goat.registry.items.GTItems;
import com.kyanite.goat.registry.sounds.GTSounds;
import com.mojang.logging.LogUtils;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;

import java.util.Map;

public class GolemsOfAllTypes {
    public static final String MOD_ID = "goat";
    public static final CreativeModeTab TAB = RegistryHelper.registerCreativeModeTab(() -> new ItemStack(Items.COPPER_INGOT));
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        GeckoLib.initialize();

        GTSounds.register();
        GTEntities.register();
        GTBlocks.register();
        GTItems.register();
    }

    public static void attributes(Map<EntityType<? extends LivingEntity>, AttributeSupplier.Builder> attributes) {
        attributes.put(GTEntities.COPPER_GOLEM.get(), CopperGolem.attributes());
        attributes.put(GTEntities.TUFF_GOLEM.get(), TuffGolem.attributes());
        attributes.put(GTEntities.CHEST_GOLEM.get(), ChestGolem.attributes());
    }
}