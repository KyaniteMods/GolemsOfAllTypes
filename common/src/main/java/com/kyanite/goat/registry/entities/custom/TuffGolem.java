package com.kyanite.goat.registry.entities.custom;

import com.kyanite.goat.GolemsOfAllTypes;
import com.kyanite.goat.registry.blocks.GTBlocks;
import com.kyanite.goat.registry.blocks.custom.GTButtonBlock;
import com.kyanite.goat.registry.sounds.GTSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.EnumSet;
import java.util.List;

public class TuffGolem extends AbstractGolem implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);

    protected static final EntityDataAccessor<Integer> TIME = SynchedEntityData.defineId(TuffGolem.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Boolean> SLEEPING = SynchedEntityData.defineId(TuffGolem.class, EntityDataSerializers.BOOLEAN);
    public TuffGolem(EntityType<? extends AbstractGolem> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setCanPickUpLoot(true);
    }

    public static AttributeSupplier.Builder attributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 5.5D).add(Attributes.MOVEMENT_SPEED, 0.15f).add(Attributes.ATTACK_DAMAGE, 1.0D);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 3, this::predicate));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(3, new FloatGoal(this));
        this.goalSelector.addGoal(4, new PanicGoal(this, 1.8f));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.1f));
        this.goalSelector.addGoal(7, new FindAndTakeItemGoal(this));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 10));
    }


    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.TUFF_HIT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.TUFF_BREAK;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TIME, random.nextInt(400, 1100));
        this.entityData.define(SLEEPING, false);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.entityData.get(TIME) > 1) {
            this.entityData.set(TIME, this.entityData.get(TIME) -1);
        }else{
            setSleeping(!isSleeping());
            this.entityData.set(TIME, random.nextInt(400, 1100));
        }
    }

    public void setSleeping(boolean value) {
        this.entityData.set(SLEEPING, value);
        if(isSleeping()) {
            this.setNoAi(true);
            this.setSpeed(0);

            for (Goal goal : this.goalSelector.getRunningGoals().toList()) {
                goal.stop();
            }
        }else{
            this.setNoAi(false);
            this.setSpeed(0.15f);

            for (Goal goal : this.goalSelector.getRunningGoals().toList()) {
                goal.start();
            }
        }
    }
    public boolean isSleeping() {
        return this.entityData.get(SLEEPING);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Time", this.entityData.get(TIME));
        compoundTag.putBoolean("Sleeping", this.entityData.get(SLEEPING));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.entityData.set(TIME, compoundTag.getInt("Time"));
        this.entityData.set(SLEEPING, compoundTag.getBoolean("Sleeping"));
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        ItemStack stack = player.getItemInHand(interactionHand);
        if(hasItem()) {
            player.addItem(getMainHandItem());
            this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
            return InteractionResult.SUCCESS;
        }

        if(canHoldItem(stack)) {
            if(stack.getCount() > 1) {
                this.dropItemStack(stack.split(stack.getCount() - 1));
            }

            this.setItemSlot(EquipmentSlot.MAINHAND, stack.split(1));
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, interactionHand);
    }

    public boolean hasItem() {
        ItemStack itemStack = getMainHandItem();
        return itemStack.isEmpty() ? false : true;
    }

    public String getIdleAnimId() {
        if(isSleeping()) {
            return hasItem() ? "animation.tuff.holdIdleSit" : "animation.tuff.sitidle";
        }else{
            return hasItem() ? "animation.tuff.holdidle" : "animation.tuff.idle";
        }
    }
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving() && !isSleeping()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(hasItem() ? "animation.tuff.holdingwalk" : "animation.tuff.walk", true));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation(getIdleAnimId(), true));
        return PlayState.CONTINUE;
    }

    @Override
    public boolean canHoldItem(ItemStack pStack) {
        ItemStack itemstack = this.getItemBySlot(EquipmentSlot.MAINHAND);
        return itemstack.isEmpty() && !isSleeping();
    }

    @Override
    protected void pickUpItem(ItemEntity itemEntity) {
        ItemStack stack = itemEntity.getItem();
        if(this.canHoldItem(stack)) {
            if(stack.getCount() > 1) {
                this.dropItemStack(stack.split(stack.getCount() - 1));
            }

            this.onItemPickup(itemEntity);
            this.setItemSlot(EquipmentSlot.MAINHAND, stack.split(1));
            this.take(itemEntity, stack.getCount());
            itemEntity.discard();
        }
    }

    private void dropItemStack(ItemStack pStack) {
        ItemEntity itementity = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ() + 1.5f, pStack);
        this.level.addFreshEntity(itementity);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new GroundPathNavigation(this, level);
    }
    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    public class FindAndTakeItemGoal extends Goal {
        private final TuffGolem golem;

        public FindAndTakeItemGoal(TuffGolem golem) {
            this.setFlags(EnumSet.of(Flag.MOVE));
            this.golem = golem;
        }

        @Override
        public boolean canUse() {
            if (!golem.hasItem()) {
                return false;
            } else {
                List<ItemEntity> list = golem.level.getEntitiesOfClass(ItemEntity.class, golem.getBoundingBox().inflate(20,20,20));
                return !list.isEmpty() && !golem.hasItem();
            }
        }

        @Override
        public void tick() {
            List<ItemEntity> list = golem.level.getEntitiesOfClass(ItemEntity.class, golem.getBoundingBox().inflate(20,20,20));
            if(!list.isEmpty() && !golem.hasItem()) golem.getNavigation().moveTo(list.get(0), 1.3F);

            if(golem.getNavigation().isDone() && !golem.hasItem()) {
                golem.pickUpItem(list.get(0));
            }
        }

        @Override
        public void start() {
            List<ItemEntity> list = golem.level.getEntitiesOfClass(ItemEntity.class, golem.getBoundingBox().inflate(8.0D, 8.0D, 8.0D));
            if(!list.isEmpty()) golem.getNavigation().moveTo(list.get(0), 1.3F);
        }
    }
}