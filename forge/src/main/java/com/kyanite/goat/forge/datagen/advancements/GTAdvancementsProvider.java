package com.kyanite.goat.forge.datagen.advancements;

import com.kyanite.goat.GolemsOfAllTypes;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class GTAdvancementsProvider extends AdvancementProvider {
    public GTAdvancementsProvider(DataGenerator pGenerator, ExistingFileHelper pExistingFileHelper) {
        super(pGenerator, pExistingFileHelper);
    }

    @Override
    protected void registerAdvancements(@NotNull Consumer<Advancement> consumer, @NotNull ExistingFileHelper fileHelper) {
        String path = GolemsOfAllTypes.MOD_ID + ":main/";
        String id = "advancements.goat.";
    }
}