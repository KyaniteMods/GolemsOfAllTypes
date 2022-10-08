package com.kyanite.goat.registry.blocks.custom;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.ButtonBlock;

public class MetalButtonBlock extends ButtonBlock {
    public MetalButtonBlock(Properties properties) {
        super(true, properties);
    }

    @Override
    protected SoundEvent getSound(boolean bl) {
        return bl ? SoundEvents.STONE_BUTTON_CLICK_ON : SoundEvents.STONE_BUTTON_CLICK_OFF;
    }
}
