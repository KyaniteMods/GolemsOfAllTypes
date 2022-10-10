package com.kyanite.goat.client.rendering;

import com.kyanite.goat.GolemsOfAllTypes;
import com.kyanite.goat.registry.entities.custom.CopperGolem;
import com.kyanite.goat.registry.entities.custom.TuffGolem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class TuffGolemModel extends AnimatedGeoModel<TuffGolem> {
    @Override
    public ResourceLocation getModelResource(TuffGolem object) {
        return new ResourceLocation(GolemsOfAllTypes.MOD_ID, "geo/tuff_golem.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TuffGolem object) {
        return new ResourceLocation(GolemsOfAllTypes.MOD_ID, "textures/entity/tuff_golem/tuff_golem_red.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TuffGolem animatable) {
        return new ResourceLocation(GolemsOfAllTypes.MOD_ID, "animations/tuff_golem.animation.json");
    }

    @Override
    public void setLivingAnimations(TuffGolem entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
      // EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
       // IBone head = getBone("Body");
       // head.setRotationY(extraData.netHeadYaw * ((float)Math.PI / 180F));
    }
}