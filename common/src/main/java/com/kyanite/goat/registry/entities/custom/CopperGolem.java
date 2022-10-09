package com.kyanite.goat.registry.entities.custom;

import com.kyanite.goat.GolemsOfAllTypes;
import com.kyanite.goat.registry.blocks.GTBlocks;
import com.kyanite.goat.registry.blocks.custom.GTButtonBlock;
import com.kyanite.goat.registry.sounds.GTSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
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
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

// Credit to https://github.com/Sollace/CopperGolem for some code related to Oxidization.
public class CopperGolem extends PathfinderMob implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    private static final EntityDataAccessor<Integer> SPIN_TIME = SynchedEntityData.defineId(CopperGolem.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> OXIDATION = SynchedEntityData.defineId(CopperGolem.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Boolean> WAXED = SynchedEntityData.defineId(CopperGolem.class, EntityDataSerializers.BOOLEAN);

    public CopperGolem(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder attributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 2.0D).add(Attributes.MOVEMENT_SPEED, 0.15f).add(Attributes.ATTACK_DAMAGE, 1.0D);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 3, this::predicate));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1f));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new FindAndPressCopperButtonGoal(this, 2f, 45));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 10));
    }

    @Override
    protected float nextStep() {
        return super.nextStep() - 0.35f;
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if(getDegradationLevel().equals(WeatheringCopper.WeatherState.OXIDIZED) && !isNoAi()) {
            this.setNoAi(true);

            for (Goal goal : this.goalSelector.getRunningGoals().toList()) {
                goal.stop();
            }

            this.getNavigation().setSpeedModifier(0);
            this.getNavigation().stop();

            this.jumping = false;
            this.walkDistO = 0;
            this.walkDist = 0;
            this.xxa = 0;
            this.yya = 0;
            this.setSpeed(0.0F);
            this.setHealth(1);
            this.setDeltaMovement(Vec3.ZERO);
            this.hasImpulse = false;
            return;
        }

        if (random.nextFloat() < 0.05688889F && this.entityData.get(WAXED) == false) {
            setOxidation(getOxidation() + 1);
            GolemsOfAllTypes.LOGGER.info(String.valueOf(getOxidation()));
            GolemsOfAllTypes.LOGGER.info(getDegradationLevel().name());
        }

        int headSpinTime = getHeadSpin();
        if (headSpinTime > 0) {
            if (headSpinTime == 10) {
                playSound(GTSounds.COPPER_GOLEM_SPIN.get(), getSoundVolume(), getVoicePitch());
            }
            entityData.set(SPIN_TIME, headSpinTime - 1);
        }
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {

    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.COPPER_HIT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.COPPER_BREAK;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SPIN_TIME, 0);
        this.entityData.define(OXIDATION, 0);
        this.entityData.define(WAXED, false);
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if(stack.getItem() instanceof AxeItem) {
            if (this.entityData.get(WAXED) == false && this.getDegradationLevel() == WeatheringCopper.WeatherState.UNAFFECTED) return InteractionResult.FAIL;
            if(this.entityData.get(WAXED) == true) {
                this.entityData.set(WAXED, false);
                for (int i = 0; i < 7; i++) {
                    level.addParticle(ParticleTypes.WAX_OFF ,
                            getRandomX(1.0D), getRandomY() + 0.5D,  getRandomZ(1.0D),
                            1d, this.getRandom().nextGaussian() * 0.02D, this.getRandom().nextGaussian() * 0.02D);
                }
                this.playSound(SoundEvents.AXE_WAX_OFF, 1.0F, 1.0F);
            }else if(this.getDegradationLevel() != WeatheringCopper.WeatherState.UNAFFECTED) {
                if (!this.getLevel().isClientSide()) {
                    int increasedOxidationLevelOrdinal = getDegradationLevel().ordinal() - 1;
                    WeatheringCopper.WeatherState[] OxidationLevels = WeatheringCopper.WeatherState.values();
                    this.setDegradationLevel(OxidationLevels[increasedOxidationLevelOrdinal]);
                }

                for (int i = 0; i < 7; i++) {
                    level.addParticle(ParticleTypes.SCRAPE,
                            getRandomX(1.0D), getRandomY() + 0.5D,  getRandomZ(1.0D),
                            1d, this.getRandom().nextGaussian() * 0.02D, this.getRandom().nextGaussian() * 0.02D);
                }
                this.playSound(SoundEvents.AXE_SCRAPE, 1.0F, 1.0F);
            }
        }
        else if(stack.getItem().equals(Items.HONEYCOMB)) {
            if(this.entityData.get(WAXED) == true || this.getDegradationLevel() == WeatheringCopper.WeatherState.OXIDIZED) return InteractionResult.FAIL;
            this.entityData.set(WAXED, true);
            if(!player.isCreative()) stack.shrink(1);
            for (int i = 0; i < 7; i++) {
                level.addParticle(ParticleTypes.WAX_ON ,
                        getRandomX(1.0D), getRandomY() + 0.5D,  getRandomZ(1.0D),
                        1d, this.getRandom().nextGaussian() * 0.02D, this.getRandom().nextGaussian() * 0.02D);
            }
            this.playSound(SoundEvents.HONEYCOMB_WAX_ON, 1.0F, 1.0F);
        }
        return super.mobInteract(player, hand);
    }

    public void setOxidation(int oxidation) {
        this.entityData.set(OXIDATION, Mth.clamp(oxidation, 0, (WeatheringCopper.WeatherState.values().length - 1) * 70));
    }

    public void setDegradationLevel(WeatheringCopper.WeatherState level) {
        setOxidation(level.ordinal() * 70);
    }
    public int getOxidation() {
        return this.entityData.get(OXIDATION);
    }

    public WeatheringCopper.WeatherState getDegradationLevel() {
        int oxidation = (int)Math.floor((float)getOxidation() / 70f);

        var levels = WeatheringCopper.WeatherState.values();

        return levels[Mth.clamp(oxidation, 0, levels.length - 1)];
    }


    @Override
    public void tick() {
        super.tick();
        if(getDegradationLevel().equals(WeatheringCopper.WeatherState.OXIDIZED)) return;

        if(random.nextInt(0, 300) == 0 && getHeadSpin() == 0) {
            entityData.set(SPIN_TIME, 10);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Oxidization", getOxidation());
        compoundTag.putInt("SpinTime", getHeadSpin());
        compoundTag.putBoolean("Waxed", entityData.get(WAXED));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        setOxidation(compoundTag.getInt("Oxidization"));
        entityData.set(SPIN_TIME, compoundTag.getInt("SpinTime"));
        entityData.set(WAXED, compoundTag.getBoolean("Waxed"));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.copper_golem.walk", true));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.copper_golem.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new GroundPathNavigation(this, level);
    }
    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    public int getHeadSpin() {
        return this.entityData.get(SPIN_TIME);
    }

    public class FindAndPressCopperButtonGoal extends MoveToBlockGoal {

        public FindAndPressCopperButtonGoal(PathfinderMob pathfinderMob, double d, int i) {
            super(pathfinderMob, d, i);
        }


        @Override
        public void tick() {
            super.tick();
            if(mob.distanceToSqr(blockPos.getX(), blockPos.getY(), blockPos.getZ()) < 5 && random.nextInt(0, 50) == 0) {
                GTButtonBlock buttonBlock = (GTButtonBlock) level.getBlockState(blockPos).getBlock();
                if(level.getBlockState(blockPos).getValue(ButtonBlock.POWERED) == false) {
                    ((ButtonBlock)level.getBlockState(blockPos).getBlock()).press(level.getBlockState(blockPos), mob.getLevel(), blockPos);
                    level.playSound(null, blockPos, buttonBlock.getSound(true), SoundSource.BLOCKS, 0.3F, 0.6F);
                }
            }
        }

        @Override
        protected boolean isValidTarget(LevelReader levelReader, BlockPos blockPos) {
            return levelReader.getBlockState(blockPos).is(GTBlocks.COPPER_BUTTON.get());
        }
    }
}