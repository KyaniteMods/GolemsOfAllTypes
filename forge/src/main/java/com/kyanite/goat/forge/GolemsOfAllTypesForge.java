package com.kyanite.goat.forge;

import com.kyanite.goat.GolemsOfAllTypes;
import com.kyanite.goat.platform.forge.RegistryHelperImpl;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(GolemsOfAllTypes.MOD_ID)
public class GolemsOfAllTypesForge {
    public GolemsOfAllTypesForge() {
        GolemsOfAllTypes.init();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        RegistryHelperImpl.SOUND_EVENTS.register(bus);
        RegistryHelperImpl.ITEMS.register(bus);
        RegistryHelperImpl.BLOCKS.register(bus);
        RegistryHelperImpl.ENCHANTMENTS.register(bus);
        RegistryHelperImpl.MOB_EFFECTS.register(bus);
        RegistryHelperImpl.POTIONS.register(bus);
        RegistryHelperImpl.ENTITY_TYPES.register(bus);
        RegistryHelperImpl.FEATURES.register(bus);
        RegistryHelperImpl.CONFIGURED_FEATURES.register(bus);
        RegistryHelperImpl.PLACED_FEATURES.register(bus);
        RegistryHelperImpl.BIOMES.register(bus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventBusSubscriber(modid = GolemsOfAllTypes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class GolemsOfAllTypesCommon {
        @SubscribeEvent
        public static void commonSetup(final FMLCommonSetupEvent event) {
            event.enqueueWork(() -> {
            });

        }
    }

    @Mod.EventBusSubscriber(modid = GolemsOfAllTypes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class GolemsOfAllTypesClient {
        @SubscribeEvent
        public static void clientSetup(final FMLClientSetupEvent event) {
            // Entity renderer registry
        }
    }
}