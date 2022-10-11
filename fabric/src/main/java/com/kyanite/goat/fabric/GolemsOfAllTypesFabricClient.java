package com.kyanite.goat.fabric;

import com.kyanite.goat.client.rendering.ChestGolemRenderer;
import com.kyanite.goat.client.rendering.CopperGolemRenderer;
import com.kyanite.goat.client.rendering.TuffGolemRenderer;
import com.kyanite.goat.registry.entities.GTEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class GolemsOfAllTypesFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(GTEntities.COPPER_GOLEM.get(), CopperGolemRenderer::new);
        EntityRendererRegistry.register(GTEntities.TUFF_GOLEM.get(), TuffGolemRenderer::new);
        EntityRendererRegistry.register(GTEntities.CHEST_GOLEM.get(), ChestGolemRenderer::new);
    }
}
