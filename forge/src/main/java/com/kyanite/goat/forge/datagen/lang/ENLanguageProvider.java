package com.kyanite.goat.forge.datagen.lang;

import com.kyanite.goat.GolemsOfAllTypes;
import com.kyanite.goat.platform.forge.RegistryHelperImpl;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;

public class ENLanguageProvider extends LanguageProvider {
    private static final String NORMAL_CHARS = " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_,;.?!/\\'";
    private static final String UPSIDE_DOWN_CHARS = " ɐqɔpǝɟbɥıظʞןɯuodbɹsʇnʌʍxʎzⱯᗺƆᗡƎℲ⅁HIſʞꞀWNOԀὉᴚS⟘∩ΛMXʎZ0ƖᄅƐㄣϛ9ㄥ86‾'؛˙¿¡/\\,";
    private final boolean upsideDown;

    public ENLanguageProvider(DataGenerator pGenerator, String locale, boolean upsideDown) {
        super(pGenerator, GolemsOfAllTypes.MOD_ID, locale);
        this.upsideDown = upsideDown;
    }

    @Override
    protected void addTranslations() {
        RegistryHelperImpl.BLOCKS.getEntries().forEach(this::addBlock);
        RegistryHelperImpl.MOB_EFFECTS.getEntries().forEach(this::addEffect);
        RegistryHelperImpl.ENTITY_TYPES.getEntries().forEach(this::addEntity);
        RegistryHelperImpl.BIOMES.getEntries().forEach(this::addBiome);
        RegistryHelperImpl.ITEMS.getEntries().stream().filter(item -> !(item.get() instanceof BlockItem)).forEach(this::addItem);
        RegistryHelperImpl.ENCHANTMENTS.getEntries().forEach(this::addEnchantment);

        add("itemGroup.goat.goat", "Golems of All Types");
        add("itemGroup.goat", "Golems of All Types");

        add("subtitles.entity.copper_golem.spin", "Copper Golem head spins");
    }

    @Override
    public void add(String key, String value) {
        if(upsideDown) super.add(key, toUpsideDown(value));
        else super.add(key, value);
    }

    private void addBlock(RegistryObject<Block> block) {
        String key = block.getId().getPath();
        super.add("block.goat." + key, convertToName(key));
    }

    private void addEffect(RegistryObject<MobEffect> effect) {
        String key = effect.getId().getPath();
        super.add("effect.goat." + key, convertToName(key));
    }

    private void addEntity(RegistryObject<EntityType<?>> item) {
        String key = item.getId().getPath();
        super.add("entity.goat." + key, convertToName(key));
    }

    private void addBiome(RegistryObject<Biome> item) {
        String key = item.getId().getPath();
        super.add("biome.goat." + key, convertToName(key));
    }

    private void addItem(RegistryObject<Item> item) {
        String key = item.getId().getPath();
        super.add("item.goat." + key, convertToName(key));
    }

    private void addEnchantment(RegistryObject<Enchantment> item) {
        String key = item.getId().getPath();
        super.add("enchantment.goat." + key, convertToName(key));
    }

    private String convertToName(String key) {
        StringBuilder builder = new StringBuilder(key.substring(0, 1).toUpperCase() + key.substring(1));
        for(int i = 1; i < builder.length(); i++) {
            if(builder.charAt(i) == '_') {
                builder.deleteCharAt(i);
                builder.replace(i, i + 1, " " + Character.toUpperCase(builder.charAt(i)));
            }
        }

        String name = builder.toString();

        return upsideDown ? toUpsideDown(name) : name;
    }

    private static String toUpsideDown(String name) {
        StringBuilder builder = new StringBuilder();

        for(int i = name.length() - 1; i >= 0; i--) {
            if(i > 2 && name.substring(i - 3, i + 1).equals("%1$s")) {
                builder.append(name, i - 3, i + 1);
                i -= 4;
                continue;
            }

            char upsideDown = UPSIDE_DOWN_CHARS.charAt(NORMAL_CHARS.indexOf(name.charAt(i)));
            builder.append(upsideDown);
        }

        return builder.toString();
    }
}