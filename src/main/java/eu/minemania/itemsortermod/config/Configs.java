package eu.minemania.itemsortermod.config;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import eu.minemania.itemsortermod.Reference;
import eu.minemania.itemsortermod.data.DataManager;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.config.options.ConfigStringList;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.JsonUtils;

import java.io.File;

public class Configs implements IConfigHandler
{
    /**
     * Config file for mod.
     */
    private static final String CONFIG_FILE_NAME = Reference.MOD_ID + ".json";

    /**
     * Default Generic configuration.
     */
    public static class Generic
    {
        public static final ConfigStringList PRESETS = new ConfigStringList("PRESETS", setDefaultPresets(), "Presets list");

        public static final ImmutableList<IConfigBase> OPTIONS = ImmutableList.of(
                PRESETS
        );
    }

    private static ImmutableList<String> setDefaultPresets()
    {
        ImmutableList.Builder<String> builder = ImmutableList.builder();
        builder.add("trash=bow,wooden_sword,wooden_shovel,wooden_pickaxe,rotten_flesh,string,arrow,wooden_axe,wooden_hoe,wheat_seeds,spider_eye,poisonous_potato,filled_map");
        builder.add("junk=snow,lever,stone_pressure_plate,wooden_pressure_plate,stone_button,oak_trapdoor,oak_fence,ladder,oak_button,light_weighted_pressure_plate,heavy_weighted_pressure_plate,bowl,oak_stairs,cobblestone_stairs,stone_brick_stairs,oak_slab,sandstone_stairs,spruce_stairs,birch_stairs,jungle_stairs,cobblestone_wall,acacia_stairs,dark_oak_stairs,glass_pane,stained_glass_pane,white_carpet,stone_brick_slab,cobblestone_slab");
        builder.add("nostackjunk=white_bed,oak_door,oak_boat");
        builder.add("tooljunk=carrot_on_a_stick,stone_shovel,stone_pickaxe,stone_axe,stone_hoe,golden_shovel,golden_pickaxe,golden_axe,golden_hoe,fishing_rod,diamond_hoe,stone_sword");
        builder.add("ores=redstone_block,redstone,gold_ore,iron_ore,coal_ore,lapis_ore,lapis_block,coal_block,gold_block,iron_block,diamond_ore,diamond_block,redstone_ore,emerald_ore,emerald_block,diamond,coal,iron_ingot,gold_ingot,emerald,gold_nugget,lapis_lazuli");
        builder.add("food=apple,mushroom_stew,bread,porkchop,cooked_porkchop,golden_apple,raw_cod,cooked_cod,cake,cookie,melon_slice,beef,cooked_beef,chicken,cooked_chicken,carrot,potato,baked_potato,pumpkin_pie,wheat,pumpkin_seeds,melon_seeds,pumpkin,melon");
        builder.add("plants=oak_sapling,oak_leaves,tall_grass,dead_bush,dandelion,poppy,brown_mushroom,red_mushroom,cactus,vine,lily_pad,acacia_leaves,sunflower");
        builder.add("discs=music_disc_13,music_disc_cat,music_disc_blocks,music_disc_chirp,music_disc_far,music_disc_mall,music_disc_mellohi,music_disc_stal,music_disc_strad,music_disc_ward,music_disc_11,music_disc_wait");
        builder.add("armor=leather_helmet,leather_chestplate,leather_leggings,leather_boots,chainmail_helmet,chainmail_chestplate,chainmail_leggings,chainmail_boots,iron_helmet,iron_chestplate,iron_leggings,iron_boots,diamond_helmet,diamond_chestplate,diamond_leggings,diamond_boots,golden_helmet,golden_chestplate,golden_leggings,golden_boots");
        builder.add("tools=iron_shovel,iron_pickaxe,iron_axe,diamond_shovel,diamond_pickaxe,diamond_axe,iron_hoe,iron_sword,diamond_sword,golden_sword");
        builder.add("wood=oak_planks,spruce_planks,jungle_planks,dark_oak_planks,birch_planks,acacia_planks,acacia_log,birch_log,dark_oak_log,jungle_log,oak_log,spruce_log,stripped_acacia_log,stripped_birch_log,stripped_dark_oak_log,stripped_jungle_log,stripped_oak_log,stripped_spruce_log");
        builder.add("woodjunk=stick,oak_sign,torch,oak_fence");
        builder.add("bonez=bone,bone_meal");
        builder.add("brewing=golden_carrot,ghast_tear,fermented_spider_eye,blaze_powder,magma_cream,glistering_melon_slice,blaze_rod,glowstone_dust,sugar,nether_wart");
        builder.add("redstonestuff=dispenser,note_block,sticky_piston,piston,redstone_torch,redstone_lamp,tripwire_hook,dropper,repeater,comparator,compass,clock");
        builder.add("ironstuff=flint_and_steel,shears,hopper,anvil,bucket,cauldron,iron_door,iron_trapdoor,powered_rail,rail,minecart,lava_bucket");
        builder.add("containers=jukebox,ender_chest,enchanting_table,brewing_stand,furnace,crafting_table,chest,trapped_chest");
        builder.add("nether=netherrack,soul_sand,glowstone,nether_bricks,nether_brick_fence,nether_brick_slab,nether_brick_stairs,nether_brick,obsidian");
        builder.add("sglass=glass,white_stained_glass,yellow_stained_glass,red_stained_glass,purple_stained_glass,pink_stained_glass,orange_stained_glass,magenta_stained_glass,lime_stained_glass,light_gray_stained_glass,light_blue_stained_glass,green_stained_glass,gray_stained_glass,cyan_stained_glass,brown_stained_glass,blue_stained_glass,black_stained_glass");
        builder.add("clays=clay,terracotta,black_terracotta,blue_terracotta,brown_terracotta,cyan_terracotta,gray_terracotta,green_terracotta,light_blue_terracotta,light_gray_terracotta,lime_terracotta,magenta_terracotta,orange_terracotta,pink_terracotta,purple_terracotta,red_terracotta,white_terracotta,yellow_terracotta,clay_ball,brick,bricks,brick_stairs,brick_slab");
        builder.add("mobloot=slime_ball,ender_pearl,ender_eye,feather,leather,gunpowder,ink_sac");
        return builder.build();
    }

    /**
     * Loads configurations from configuration file.
     */
    public static void loadFromFile()
    {
        File configFile = new File(FileUtils.getConfigDirectory(), CONFIG_FILE_NAME);

        if (configFile.exists() && configFile.isFile() && configFile.canRead())
        {
            JsonElement element = JsonUtils.parseJsonFile(configFile);

            if (element != null && element.isJsonObject())
            {
                JsonObject root = element.getAsJsonObject();

                ConfigUtils.readConfigBase(root, "Generic", Generic.OPTIONS);
                ConfigUtils.readConfigBase(root, "Hotkeys", Hotkeys.HOTKEY_LIST);
            }
        }

        DataManager.setPresets(Generic.PRESETS.getStrings());
    }

    /**
     * Saves configurations to configuration file.
     */
    public static void saveToFile()
    {
        File dir = FileUtils.getConfigDirectory();

        if ((dir.exists() && dir.isDirectory()) || dir.mkdirs())
        {
            JsonObject root = new JsonObject();

            ConfigUtils.writeConfigBase(root, "Generic", Generic.OPTIONS);
            ConfigUtils.writeConfigBase(root, "Hotkeys", Hotkeys.HOTKEY_LIST);

            JsonUtils.writeJsonToFile(root, new File(dir, CONFIG_FILE_NAME));
        }
    }

    @Override
    public void load()
    {
        loadFromFile();
    }

    @Override
    public void save()
    {
        saveToFile();
    }
}