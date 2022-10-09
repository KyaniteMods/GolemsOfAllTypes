package com.kyanite.goat.client.rendering;

import com.kyanite.goat.GolemsOfAllTypes;
import com.kyanite.goat.registry.entities.custom.CopperGolem;
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

public class CopperGolemRenderer extends GeoEntityRenderer<CopperGolem> {
    private final Map<WeatheringCopper.WeatherState, ResourceLocation> oxidization_textures = new HashMap<>();
    public CopperGolemRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CopperGolemModel());
    }

    @Override
    public ResourceLocation getTextureLocation(CopperGolem entity) {
        return oxidization_textures.computeIfAbsent(entity.getDegradationLevel(), l -> new ResourceLocation(GolemsOfAllTypes.MOD_ID, "textures/entity/copper_golem_" + l.name().toLowerCase() + ".png"));
    }

    @Override
    public RenderType getRenderType(CopperGolem animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        stack.scale(1f, 1f, 1f);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
