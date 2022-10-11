package com.kyanite.goat.client.rendering;

import com.kyanite.goat.GolemsOfAllTypes;
import com.kyanite.goat.client.rendering.layer.TuffGolemItemRenderer;
import com.kyanite.goat.registry.entities.custom.ChestGolem;
import com.kyanite.goat.registry.entities.custom.TuffGolem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class ChestGolemRenderer extends GeoEntityRenderer<ChestGolem> {
    public ChestGolemRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ChestGolemModel());
    }

    public ResourceLocation getTextureLocation(ChestGolem entity) {
        return getGeoModelProvider().getTextureResource(entity);
    }

    @Override
    public RenderType getRenderType(ChestGolem animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        stack.scale(1f, 1f, 1f);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
