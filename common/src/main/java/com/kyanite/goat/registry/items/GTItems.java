package com.kyanite.goat.registry.items;

import com.kyanite.goat.GolemsOfAllTypes;
import com.kyanite.goat.registry.entities.GTEntities;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Supplier;

import static com.kyanite.goat.platform.RegistryHelper.registerItem;

public class GTItems {
    public static final Supplier<Item> COPPER_GOLEM_SPAWN_EGG = registerItem("copper_golem_spawn_egg", () -> new SpawnEggItem(GTEntities.COPPER_GOLEM.get(), 0xfba887, 0xa35741, new Item.Properties().tab(GolemsOfAllTypes.TAB)));
    public static final Supplier<Item> TUFF_GOLEM_SPAWN_EGG = registerItem("tuff_golem_spawn_egg", () -> new SpawnEggItem(GTEntities.TUFF_GOLEM.get(), 0xa76e1f, 0xa5a5a5, new Item.Properties().tab(GolemsOfAllTypes.TAB)));
    public static final Supplier<Item> CHEST_GOLEM_SPAWN_EGG = registerItem("chest_golem_spawn_egg", () -> new SpawnEggItem(GTEntities.CHEST_GOLEM.get(), 0x85837b, 0x4d5046, new Item.Properties().tab(GolemsOfAllTypes.TAB)));

    public static void register() {}
}
