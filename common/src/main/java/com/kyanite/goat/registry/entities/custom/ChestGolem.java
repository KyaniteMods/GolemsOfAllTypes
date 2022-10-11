package com.kyanite.goat.registry.entities.custom;

import com.kyanite.goat.misc.GTUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.OptionalInt;

public class ChestGolem extends TamableAnimal implements IAnimatable, Container {
    private final AnimationFactory factory = new AnimationFactory(this);
    protected static final EntityDataAccessor<Boolean> OPEN = SynchedEntityData.defineId(TuffGolem.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> SITTING = SynchedEntityData.defineId(TuffGolem.class, EntityDataSerializers.BOOLEAN);
    private NonNullList<ItemStack> items;

    public ChestGolem(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
        this.items = NonNullList.withSize(27, ItemStack.EMPTY);
        this.setTame(false);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new GroundPathNavigation(this, level);
    }

    public static AttributeSupplier.Builder attributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 5.5D).add(Attributes.MOVEMENT_SPEED, 0.15f).add(Attributes.ATTACK_DAMAGE, 1.0D);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        ItemStack itemstack = player.getItemInHand(interactionHand);
        if (isFood(itemstack) && !this.isTame()) {
            this.usePlayerItem(player, interactionHand, itemstack);
            if (!this.level.isClientSide()) {
                this.tame(player);
                this.navigation.stop();
                this.setTarget((LivingEntity)null);
                this.level.broadcastEntityEvent(this, (byte)7);
            }
            return InteractionResult.SUCCESS;
        }

        if(player.isCrouching() && isTame() && player.getUUID() == getOwnerUUID()) {
            setSitting(!isSitting());
            return InteractionResult.SUCCESS;
        }

        if(!player.hasContainerOpen()) {
            player.openMenu(new SimpleMenuProvider((ix, inventory, playerx) -> {
                return createMenu(27, player.getInventory());
            }, getDisplayName()));
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, interactionHand);
    }

    public boolean isSitting() {
        return this.entityData.get(SITTING);
    }

    public void setSitting(boolean sitting) {
        this.entityData.set(SITTING, sitting);
        if(isSitting()) {
            this.goalSelector.removeAllGoals();

            this.getNavigation().stop();

            this.setSpeed(0.0F);

        }else{
            registerGoals();
            this.setSpeed(0.15f);
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.3f));
        this.goalSelector.addGoal(2, new FollowOwnerGoal(this, 1.1f, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1f));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    @Override
    public void handleEntityEvent(byte pId) {
        if (pId == 60) {
            GTUtils.spawnHeartParticles(this, this.getRandom());
        } else {
            super.handleEntityEvent(pId);
        }
    }


    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(Items.EMERALD);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(OPEN, false);
        this.entityData.define(SITTING, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        setSitting(compoundTag.getBoolean("Sitting"));
        this.entityData.set(OPEN, compoundTag.getBoolean("Open"));
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compoundTag, this.items);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Open", this.entityData.get(OPEN));
        compoundTag.putBoolean("Sitting", isSitting());
        ContainerHelper.saveAllItems(compoundTag, this.items);
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "movement_controller", 4, this::movementAnimation));
        animationData.addAnimationController(new AnimationController(this, "lid_controller", 5, this::lidAnimation));
    }

    private <E extends IAnimatable> PlayState movementAnimation(AnimationEvent<E> event) {
        if(isSitting()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.chest.sitting", true));
            return PlayState.CONTINUE;
        }
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.chest.walk", true));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.chest.idle", true));
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState lidAnimation(AnimationEvent<E> event) {
        if (this.entityData.get(OPEN) == true) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.chest.opened", true));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.chest.closed", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void startOpen(Player player) {
        this.entityData.set(OPEN, true);
        playSound(level, blockPosition(), SoundEvents.CHEST_OPEN);
    }

    @Override
    public void stopOpen(Player player) {
        this.entityData.set(OPEN, false);
        playSound(level, blockPosition(), SoundEvents.CHEST_CLOSE);
    }

    private void playSound(Level level, BlockPos blockPos, SoundEvent soundEvent) {
        double d = (double) blockPos.getX() + 0.5;
        double e = (double) blockPos.getY() + 0.5;
        double f = (double) blockPos.getZ() + 0.5;
        level.playSound((Player) null, d, e, f, soundEvent, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    public AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return ChestMenu.threeRows(i, inventory, this);
    }

    @Override
    public int getContainerSize() {
        return 27;
    }

    @Override
    public boolean isEmpty() {
        return this.getItems().stream().allMatch(ItemStack::isEmpty);
    }

    public NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    public ItemStack getItem(int i) {
        return (ItemStack)this.getItems().get(i);
    }

    @Override
    public ItemStack removeItem(int i, int j) {
        ItemStack itemStack = ContainerHelper.removeItem(this.getItems(), i, j);
        if (!itemStack.isEmpty()) {
            this.setChanged();
        }

        return itemStack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int i) {
        return ContainerHelper.takeItem(this.getItems(), i);
    }

    @Override
    public void setItem(int i, ItemStack itemStack) {
        this.getItems().set(i, itemStack);
        if (itemStack.getCount() > this.getMaxStackSize()) {
            itemStack.setCount(this.getMaxStackSize());
        }

        this.setChanged();
    }

    public void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    public void setChanged() {

    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        this.getItems().clear();
    }
}
