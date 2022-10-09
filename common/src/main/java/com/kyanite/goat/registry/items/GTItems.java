package com.kyanite.goat.registry.items;

import com.kyanite.goat.GolemsOfAllTypes;
import com.kyanite.goat.registry.entities.GTEntities;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Supplier;

import static com.kyanite.goat.platform.RegistryHelper.registerItem;

public class GTItems {
    public static final Supplier<Item> COPPER_GOLEM_SPAWN_EGG = registerItem("copper_golem_spawn_egg", () -> new SpawnEggItem(GTEntities.COPPER_GOLEM.get(), 0xfba887, 0xa35741, new Item.Properties().tab(GolemsOfAllTypes.TAB)));

    public static void register() {}
}
