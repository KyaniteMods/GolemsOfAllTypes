package com.kyanite.goat.registry.entities;

import com.kyanite.goat.platform.RegistryHelper;
import com.kyanite.goat.registry.entities.custom.CopperGolem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Supplier;

public class GTEntities {
    public static final Supplier<EntityType<CopperGolem>> COPPER_GOLEM = RegistryHelper.registerEntity(
            "copper_golem",
            CopperGolem::new, MobCategory.MISC, 0.6f, 0.6f, 20
    );

    public static void register() {}
}
