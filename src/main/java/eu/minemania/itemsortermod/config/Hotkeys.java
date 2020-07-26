package eu.minemania.itemsortermod.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;

import java.util.List;

public class Hotkeys
{
    public static final ConfigHotkey GRAB = new ConfigHotkey("grab", "TAB", KeybindSettings.GUI, "Grabs specified items.");
    public static final ConfigHotkey GRAB_INV = new ConfigHotkey("grabInv", "D", KeybindSettings.GUI, "Grab everything in the container.");
    public static final ConfigHotkey DUMP_INV_ALL = new ConfigHotkey("dumpInvAll", "S", KeybindSettings.GUI, "Dump everything into the container from inventory and hotbar.");
    public static final ConfigHotkey DUMP_INV_HOT = new ConfigHotkey("dumpInvHot", "LEFT_CONTROL", KeybindSettings.MODIFIER_GUI, "Modifier key to dump only from hotbar.\nHas the same behaviour as dumpInvAll.");
    public static final ConfigHotkey DUMP_INV_INV = new ConfigHotkey("dumpInvInv", "LEFT_SHIFT", KeybindSettings.MODIFIER_GUI, "Modifier key to dump only from inventory\nHas the same behaviour as dumpInvAll.");
    public static final ConfigHotkey MOVE_STACK_TO_CONT_ALL = new ConfigHotkey("moveStackToContAll", "A", KeybindSettings.GUI, "Dump only things that are already in the container from inventory and hotbar");
    public static final ConfigHotkey MOVE_STACK_TO_CONT_HOT = new ConfigHotkey("moveStackToContHot", "LEFT_CONTROL", KeybindSettings.MODIFIER_GUI, "Modifier key to dump in container from hotbar\nHas the same behaviour as moveStackContAll");
    public static final ConfigHotkey MOVE_STACK_TO_CONT_INV = new ConfigHotkey("moveStackToContInv", "LEFT_SHIFT", KeybindSettings.MODIFIER_GUI, "Modifier key to dump in container from inventory\nHas the same behaviour as moveStackContAll");
    public static final ConfigHotkey OPEN_GUI_SETTINGS = new ConfigHotkey("openGuiSettings", "S,C", "Opens Config Gui");

    public static final List<ConfigHotkey> HOTKEY_LIST = ImmutableList.of(
            GRAB,
            GRAB_INV,
            DUMP_INV_ALL,
            DUMP_INV_HOT,
            DUMP_INV_INV,
            MOVE_STACK_TO_CONT_ALL,
            MOVE_STACK_TO_CONT_HOT,
            MOVE_STACK_TO_CONT_INV,
            OPEN_GUI_SETTINGS
    );
}
