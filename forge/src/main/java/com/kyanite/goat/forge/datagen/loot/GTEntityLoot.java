package com.kyanite.goat.forge.datagen.loot;

import net.minecraft.data.loot.EntityLoot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class GTEntityLoot extends EntityLoot {
    @Override
    protected void addTables() {
        super.addTables();
    }
}
