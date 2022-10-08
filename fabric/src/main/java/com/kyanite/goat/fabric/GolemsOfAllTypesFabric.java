package com.kyanite.goat.fabric;

import com.kyanite.goat.GolemsOfAllTypes;
import net.fabricmc.api.ModInitializer;

public class GolemsOfAllTypesFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        GolemsOfAllTypes.init();
    }
}