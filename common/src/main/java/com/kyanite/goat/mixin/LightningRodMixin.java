package com.kyanite.goat.mixin;

import com.kyanite.goat.registry.entities.GTEntities;
import com.kyanite.goat.registry.entities.custom.CopperGolem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightningRodBlock.class)
public class LightningRodMixin {
    @Inject(method = "onPlace", at = @At("TAIL"), cancellable = true)
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl, CallbackInfo ci) {
        if(level.getBlockState(blockPos.below()).is(Blocks.COPPER_BLOCK)) {
            level.destroyBlock(blockPos.below(), false);
            level.destroyBlock(blockPos, false);
            CopperGolem copperGolem = GTEntities.COPPER_GOLEM.get().create(level);
            copperGolem.moveTo(blockPos.below(), 0, 0);
            level.addFreshEntity(copperGolem);
        }
    }
}
