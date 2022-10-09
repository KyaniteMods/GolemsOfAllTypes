package com.kyanite.goat.registry.sounds;

import com.kyanite.goat.GolemsOfAllTypes;
import com.kyanite.goat.platform.RegistryHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public class GTSounds {
    public static final Supplier<SoundEvent> COPPER_GOLEM_SPIN = register("entity.copper_golem.spin");

    private static Supplier<SoundEvent> register(String name) {
        return RegistryHelper.registerSound(name, () -> new SoundEvent(new ResourceLocation(GolemsOfAllTypes.MOD_ID, name)));
    }

    public static void register() {}
}
