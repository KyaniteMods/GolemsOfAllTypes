package com.kyanite.goat.forge.datagen.loot;

import com.kyanite.goat.platform.forge.RegistryHelperImpl;
import com.kyanite.goat.registry.entities.GTEntities;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class GTEntityLoot extends EntityLoot {
    @Override
    protected void addTables() {
        add(GTEntities.COPPER_GOLEM.get(), LootTable.lootTable().withPool(
                LootPool.lootPool().add(LootItem.lootTableItem(Items.COPPER_INGOT).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 10))))));
        add(GTEntities.TUFF_GOLEM.get(), LootTable.lootTable().withPool(
                LootPool.lootPool().add(LootItem.lootTableItem(Blocks.TUFF).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))));
        add(GTEntities.CHEST_GOLEM.get(), LootTable.lootTable());
    }

    @Override
    protected Iterable<EntityType<?>> getKnownEntities() {
        Stream<? extends EntityType<?>> stream = RegistryHelperImpl.ENTITY_TYPES.getEntries().stream().map(RegistryObject::get);
        return () -> (Iterator<EntityType<?>>) stream.iterator();
    }
}
