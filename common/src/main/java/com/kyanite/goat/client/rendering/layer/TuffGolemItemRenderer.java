package com.kyanite.goat.client.rendering.layer;

import com.kyanite.goat.client.rendering.TuffGolemModel;
import com.kyanite.goat.registry.entities.custom.TuffGolem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import software.bernie.geckolib3.renderers.geo.layer.AbstractLayerGeo;

import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class TuffGolemItemRenderer extends GeoLayerRenderer<TuffGolem> {
    private final ItemInHandRenderer itemInHandRenderer;

    public TuffGolemItemRenderer(IGeoRenderer entityRendererIn, ItemInHandRenderer itemInHandRenderer) {
        super(entityRendererIn);
        this.itemInHandRenderer = itemInHandRenderer;
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource bufferIn, int packedLightIn, TuffGolem livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        matrixStack.pushPose();
        if(livingEntity.isSleeping()) {
            matrixStack.translate(0.1f, 0.35f, -0.27f);
        }else{
            matrixStack.translate(0.1f, 0.4f, -0.27f);
        }
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(90f));
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(135f));
        matrixStack.scale(0.8f, 0.8f, 0.8f);
        ItemStack itemStack = livingEntity.getItemBySlot(EquipmentSlot.MAINHAND);
        this.itemInHandRenderer.renderItem(livingEntity, itemStack, ItemTransforms.TransformType.GROUND, false, matrixStack, bufferIn, packedLightIn);
        matrixStack.popPose();
    }
}