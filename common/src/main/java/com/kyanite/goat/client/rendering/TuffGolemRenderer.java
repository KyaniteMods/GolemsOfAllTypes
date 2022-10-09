package com.kyanite.goat.client.rendering;

import com.kyanite.goat.GolemsOfAllTypes;
import com.kyanite.goat.client.rendering.layer.TuffGolemItemRenderer;
import com.kyanite.goat.registry.entities.custom.CopperGolem;
import com.kyanite.goat.registry.entities.custom.TuffGolem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.WeatheringCopper;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class TuffGolemRenderer extends GeoEntityRenderer<TuffGolem> {
    public TuffGolemRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TuffGolemModel());
        this.addLayer(new TuffGolemItemRenderer(this, renderManager.getItemInHandRenderer()));
    }

    public ResourceLocation getTextureLocation(TuffGolem entity) {
        return getGeoModelProvider().getTextureResource(entity);
    }

    @Override
    public RenderType getRenderType(TuffGolem animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        stack.scale(1f, 1f, 1f);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
