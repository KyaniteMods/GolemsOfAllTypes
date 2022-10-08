package com.kyanite.goat.platform.forge;

import com.kyanite.goat.GolemsOfAllTypes;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class RegistryHelperImpl {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, GolemsOfAllTypes.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, GolemsOfAllTypes.MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, GolemsOfAllTypes.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, GolemsOfAllTypes.MOD_ID);
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, GolemsOfAllTypes.MOD_ID);
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, GolemsOfAllTypes.MOD_ID);
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, GolemsOfAllTypes.MOD_ID);
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, GolemsOfAllTypes.MOD_ID);
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(Registry.BIOME_REGISTRY, GolemsOfAllTypes.MOD_ID);
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, GolemsOfAllTypes.MOD_ID);
    public static final DeferredRegister<PoiType> POI = DeferredRegister.create(ForgeRegistries.POI_TYPES, GolemsOfAllTypes.MOD_ID);
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, GolemsOfAllTypes.MOD_ID);

    public static <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    public static <T extends Item> Supplier<T> registerItem(String name, Supplier<T> item) {
        return ITEMS.register(name, item);
    }

    public static <T extends Biome> Supplier<T> registerBiome(ResourceLocation biomeLocation, Supplier<T> biomeConsumer) {
        ResourceKey<Biome> biome = ResourceKey.create(Registry.BIOME_REGISTRY, biomeLocation);
        return BIOMES.register(biome.location().getPath(), biomeConsumer);
    }

    public static <T extends Entity> Supplier<EntityType<T>> registerEntity(String name, EntityType.EntityFactory<T> entityFactory, MobCategory category, float width, float height, int clientTrackingRange) {
        return ENTITY_TYPES.register(name, () -> EntityType.Builder.of(entityFactory, category).sized(width, height).clientTrackingRange(clientTrackingRange).build(name));
    }

    public static <T extends Potion> Supplier<T> registerPotion(String name, Supplier<T> potion) {
        return POTIONS.register(name, potion);
    }

    public static <T extends ConfiguredFeature<?, ?>> Supplier<T> registerConfiguredFeature(String name, Supplier<T> feature) {
        return CONFIGURED_FEATURES.register(name, feature);
    }

    public static <T extends Feature<?>> Supplier<T> registerFeature(String name, Supplier<T> feature) {
        return FEATURES.register(name, feature);
    }

    public static <T extends MobEffect> Supplier<T> registerEffect(String name, Supplier<T> effect) {
        return MOB_EFFECTS.register(name, effect);
    }

    public static <T extends PlacedFeature> Supplier<T> registerPlacedFeature(String name, Supplier<T> placedFeature) {
        return PLACED_FEATURES.register(name, placedFeature);
    }

    public static <T extends CreativeModeTab> T registerCreativeModeTab(Supplier<ItemStack> icon) {
        return (T) new CreativeModeTab(GolemsOfAllTypes.MOD_ID) {
            @Override
            public ItemStack makeIcon() {
                return icon.get();
            }
        };
    }

    public static <T extends Enchantment> Supplier<T> registerEnchant(String name, Supplier<T> enchantment) {
        return ENCHANTMENTS.register(name, enchantment);
    }

    public static <T extends SoundEvent> Supplier<T> registerSound(String name, Supplier<T> sound) {
        return SOUND_EVENTS.register(name, sound);
    }

    public static <T extends PoiType> Supplier<T> registerPOI(String name, Supplier<T> portal) {
        return POI.register(name, portal);
    }
}