package eu.minemania.itemsortermod.config;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import eu.minemania.itemsortermod.Reference;
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
        public static final ConfigStringList PRESETS = new ConfigStringList("PRESETS", ImmutableList.of(), "Presets list");

        public static final ImmutableList<IConfigBase> OPTIONS = ImmutableList.of(
                PRESETS
                );
    }

    private static ImmutableList<String> setDefaultPresets()
    {
        ImmutableList.Builder<String> builder = ImmutableList.builder();
        builder.add("trash=bow,wooden_sword,wooden_shovel,wooden_pickaxe,rotten_flesh,string,arrow,wooden_axe,wooden_hoe,wheat_seeds,spider_eye,poisonous_potato,filled_map");
        builder.add("junk=snow_layer,lever,snow_pressure_plate,wooden_pressure_plate,stone_button,trapdoor,fence_gate,ladder,wooden_button,light_weighted_pressure_plate,heavy_weighted_pressure_plate,bowl,oak_stairs,stone_stairs,stone_brick_stairs,wooden_slab,sandstone_stairs,spruce_stairs,birch_stairs,jungle_stairs,cobblestone_wall,acacia_stairs,dark_oak_stairs,glass_pane,stained_glass_pane,carpet,cobblestone_slab,stone_brick_slab");
        builder.add("nostackjunk=bed,wooden_door,boat");
        builder.add("tooljunk=carrot_on_a_stick,stone_shovel,stone_pickaxe,stone_axe,stone_hoe,golden_shovel,golden_pickaxe,golden_axe,golden_hoe,fishing_rod,diamond_hoe,stone_sword");
        builder.add("ores=redstone_block,redstone,gold_ore,iron_ore,coal_ore,lapis_ore,lapis_block,coal_block,gold_block,iron_block,diamond_ore,diamond_block,redstone_ore,emerald_ore,emerald_block,diamond,coal,iron_ingot,gold_ingot,emerald,gold_nugget,lapis_lazuli");
        builder.add("food=apple,mushroom_stew,bread,porkchop,cooked_porkchop,golden_apple,fish,cooked_fish,cake,cookie,melon,beef,cooked_beef,chicken,cooked_chicken,carrot,potato,baked_potato,pumpkin_pie,wheat,pumpkin_seeds,melon_seeds,pumpkin,melon_block");
        builder.add("plants=sapling,leaves,tallgrass,deadbush,yellow_flower,red_flower,");
        return builder.build();
    }

    /**
     * Loads configurations from configuration file.
     */
    public static void loadFromFile()
    {
        File configFile = new File(FileUtils.getConfigDirectory(), CONFIG_FILE_NAME);

        if(configFile.exists() && configFile.isFile() && configFile.canRead())
        {
            JsonElement element = JsonUtils.parseJsonFile(configFile);

            if(element != null && element.isJsonObject())
            {
                JsonObject root = element.getAsJsonObject();

                ConfigUtils.readConfigBase(root, "Generic", Generic.OPTIONS);
            }
        }
    }

    /**
     * Saves configurations to configuration file.
     */
    public static void saveToFile()
    {
        File dir = FileUtils.getConfigDirectory();

        if((dir.exists() && dir.isDirectory()) || dir.mkdirs())
        {
            JsonObject root = new JsonObject();

            ConfigUtils.writeConfigBase(root, "Generic", Generic.OPTIONS);

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