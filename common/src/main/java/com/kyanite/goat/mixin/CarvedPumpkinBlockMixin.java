package com.kyanite.goat.mixin;

import com.kyanite.goat.registry.entities.GTEntities;
import com.kyanite.goat.registry.entities.custom.CopperGolem;
import com.kyanite.goat.registry.entities.custom.TuffGolem;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CarvedPumpkinBlock.class)
public class CarvedPumpkinBlockMixin {
    @Inject(method = "onPlace", at = @At("TAIL"), cancellable = true)
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl, CallbackInfo ci) {
        if(level.getBlockState(blockPos.below()).is(BlockTags.WOOL) && level.getBlockState(blockPos.below(2)).is(Blocks.TUFF)) {
            level.destroyBlock(blockPos.below(), false);
            level.destroyBlock(blockPos.below(2), false);
            level.destroyBlock(blockPos, false);
            TuffGolem tuffGolem = GTEntities.TUFF_GOLEM.get().create(level);
            tuffGolem.moveTo(blockPos.below(), 0, 0);
            level.addFreshEntity(tuffGolem);
        }
    }
}
