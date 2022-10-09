package com.kyanite.goat.fabric;

import com.kyanite.goat.GolemsOfAllTypes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.HashMap;
import java.util.Map;

public class GolemsOfAllTypesFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        GolemsOfAllTypes.init();

        Map<EntityType<? extends LivingEntity>, AttributeSupplier.Builder> attributes = new HashMap<>();
        GolemsOfAllTypes.attributes(attributes);
        attributes.forEach(FabricDefaultAttributeRegistry::register);
    }
}