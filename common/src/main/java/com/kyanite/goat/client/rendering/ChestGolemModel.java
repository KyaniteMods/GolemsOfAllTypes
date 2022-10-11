package com.kyanite.goat.client.rendering;

import com.kyanite.goat.GolemsOfAllTypes;
import com.kyanite.goat.registry.entities.custom.ChestGolem;
import com.kyanite.goat.registry.entities.custom.CopperGolem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class ChestGolemModel extends AnimatedGeoModel<ChestGolem> {
    @Override
    public ResourceLocation getModelResource(ChestGolem object) {
        return new ResourceLocation(GolemsOfAllTypes.MOD_ID, "geo/chest_golem.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ChestGolem object) {
        return new ResourceLocation(GolemsOfAllTypes.MOD_ID, "textures/entity/chest_golem.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ChestGolem animatable) {
        return new ResourceLocation(GolemsOfAllTypes.MOD_ID, "animations/chest_golem.animation.json");
    }

    @Override
    public void setLivingAnimations(ChestGolem entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        IBone head = getBone("Body");
        head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));

        getBone("LeftArm").setHidden(entity.isSitting());
        getBone("RightArm").setHidden(entity.isSitting());
        getBone("LeftLeg").setHidden(entity.isSitting());
        getBone("RightLeg").setHidden(entity.isSitting());
    }
}