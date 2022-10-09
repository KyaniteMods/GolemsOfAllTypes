package com.kyanite.goat.client.rendering;

import com.kyanite.goat.GolemsOfAllTypes;
import com.kyanite.goat.registry.entities.custom.CopperGolem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class CopperGolemModel extends AnimatedGeoModel<CopperGolem> {
    @Override
    public ResourceLocation getModelResource(CopperGolem object) {
        return new ResourceLocation(GolemsOfAllTypes.MOD_ID, "geo/copper_golem.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CopperGolem object) {
        return new ResourceLocation(GolemsOfAllTypes.MOD_ID, "textures/entity/copper_golem_unaffected.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CopperGolem animatable) {
        return new ResourceLocation(GolemsOfAllTypes.MOD_ID, "animations/copper_golem.animation.json");
    }

    @Override
    public void setLivingAnimations(CopperGolem entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        IBone head = getBone("Head");
        float headSpinTime = (float)entity.getHeadSpin() / 10;
        float maxRotation = 2 * (float)Math.PI;

        if(entity.getHeadSpin() > 0) {
            head.setRotationY(head.getRotationY() * 0.017453292F + Mth.lerp(headSpinTime, 0, maxRotation));
            head.setRotationX(headSpinTime > 0 ? 0 : -head.getRotationX() * 0.017453292F);
        }else{
            head.setRotationY(extraData.netHeadYaw * ((float)Math.PI / 180F));
        }
    }
}