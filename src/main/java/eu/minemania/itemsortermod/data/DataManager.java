package eu.minemania.itemsortermod.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import eu.minemania.itemsortermod.Reference;
import eu.minemania.itemsortermod.ItemSorterMod;
import eu.minemania.itemsortermod.chestsorter.ChestSorter;
import eu.minemania.itemsortermod.gui.GuiConfigs.ConfigGuiTab;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.JsonUtils;
import fi.dy.masa.malilib.util.StringUtils;
import fi.dy.masa.malilib.util.WorldUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class DataManager
{
    private static final DataManager INSTANCE = new DataManager();
    private static final ChestSorter chestSorter = new ChestSorter();
    private static HashMap<String, String> presets;
    private static boolean canSave;

    public static DataManager getInstance()
    {
        return INSTANCE;
    }

    /**
     * @return the chestSorter
     */
    public static ChestSorter getChestSorter() {
        return chestSorter;
    }

    /**
     * Logs the message to the user
     * @param message The message to log
     */
    public static void logMessage(String message)
    {
        LiteralText displayMessage = new LiteralText(message);
        displayMessage.formatted(Formatting.GREEN);
        MinecraftClient.getInstance().player.sendMessage(displayMessage);
    }

    /**
     * Logs the error message to the user
     * @param message The error message to log
     */
    public static void logError(String message)
    {
        LiteralText displayMessage = new LiteralText(message);
        displayMessage.formatted(Formatting.RED);
        MinecraftClient.getInstance().player.sendMessage(displayMessage);
    }

    private static ConfigGuiTab configGuiTab = ConfigGuiTab.GENERIC;

    public static ConfigGuiTab getConfigGuiTab()
    {
        return configGuiTab;
    }

    public static void setConfigGuiTab(ConfigGuiTab tab)
    {
        configGuiTab = tab;
    }

    public static void load()
    {
        File file = getCurrentStorageFile(true);

        JsonElement element = JsonUtils.parseJsonFile(file);

        if(element != null && element.isJsonObject())
        {

            JsonObject root = element.getAsJsonObject();

            if (JsonUtils.hasString(root, "config_gui_tab"))
            {
                try
                {
                    configGuiTab = ConfigGuiTab.valueOf(root.get("config_gui_tab").getAsString());
                }
                catch (Exception e) {}

                if (configGuiTab == null)
                {
                    configGuiTab = ConfigGuiTab.GENERIC;
                }
            }
        }

        canSave = true;
    }

    public static void save()
    {
        save(false);
    }

    public static void save(boolean forceSave)
    {
        if(!canSave && !forceSave)
        {
            return;
        }

        JsonObject root = new JsonObject();

        root.add("config_gui_tab", new JsonPrimitive(configGuiTab.name()));

        File file = getCurrentStorageFile(true);
        JsonUtils.writeJsonToFile(root, file);

        canSave = false;
    }

    private static File getCurrentStorageFile(boolean globalData)
    {
        File dir = getCurrentConfigDirectory();

        if(!dir.exists() && !dir.mkdirs())
        {
            ItemSorterMod.logger.warn("Failed to create the config directory '{}'", dir.getAbsolutePath());
        }

        return new File(dir, getStorageFileName(globalData));
    }

    private static String getStorageFileName(boolean globalData)
    {
        MinecraftClient mc = MinecraftClient.getInstance();
        String name = StringUtils.getWorldOrServerName();

        if(name != null)
        {
            if(globalData)
            {
                return Reference.MOD_ID + "_" + name + ".json";
            }
            else
            {
                return Reference.MOD_ID + "_" + name + "_dim" + WorldUtils.getDimensionId(mc.world) + ".json";
            }
        }

        return Reference.MOD_ID + "_default.json";
    }

    public static File getCurrentConfigDirectory()
    {
        return new File(FileUtils.getConfigDirectory(), Reference.MOD_ID);
    }

    public static HashMap<String, String> getPresets()
    {
        return presets;
    }

    public static void setPresets(List<String> list)
    {
        presets.clear();
        for(String listItem : list)
        {
            listItem = listItem.replaceAll(" ", "");
            String[] itemPart = listItem.split("=");
            if (itemPart.length != 2)
            {
                ItemSorterMod.logger.warn("Invalid format: " + listItem);
            }
            else
            {
                presets.put(itemPart[0], itemPart[1]);
            }
        }
    }
}