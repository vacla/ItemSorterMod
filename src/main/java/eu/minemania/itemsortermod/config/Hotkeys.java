package eu.minemania.itemsortermod.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;

import java.util.List;

public class Hotkeys
{
    public static final ConfigHotkey GRAB   = new ConfigHotkey("grab", "TAB", KeybindSettings.GUI, "Modifier key to move ALL items to the other\ninventory when scrolling over a stack");
    public static final ConfigHotkey GRAB_INV        = new ConfigHotkey("grabInv",     "D", KeybindSettings.GUI, "Modifier key to move the entire stack to the other\ninventory when scrolling over it");
    public static final ConfigHotkey DUMP_INV_ALL        = new ConfigHotkey("dumpInvAll",      "S", KeybindSettings.GUI, "Modifier key to move the entire stack to the other\ninventory when scrolling over it");
    public static final ConfigHotkey DUMP_INV_HOT        = new ConfigHotkey("dumpInvHot",     "LEFT_CONTROL", KeybindSettings.MODIFIER_GUI, "Modifier key to move the entire stack to the other\ninventory when scrolling over it");
    public static final ConfigHotkey DUMP_INV_INV     = new ConfigHotkey("dumpInvInv",   "LEFT_SHIFT", KeybindSettings.MODIFIER_GUI, "Modifier key to move all matching items to the other\ninventory when scrolling over a stack");
    public static final ConfigHotkey MOVE_STACK_TO_CONT_ALL        = new ConfigHotkey("moveStackToContAll", "A", KeybindSettings.GUI,  "Modifier key to move the entire stack to the other\ninventory when scrolling over it");
    public static final ConfigHotkey MOVE_STACK_TO_CONT_HOT        = new ConfigHotkey("moveStackToContHot",      "LEFT_CONTROL", KeybindSettings.MODIFIER_GUI, "Modifier key to move the entire stack to the other\ninventory when scrolling over it");
    public static final ConfigHotkey MOVE_STACK_TO_CONT_INV     = new ConfigHotkey("moveStackToContInv",   "LEFT_SHIFT", KeybindSettings.MODIFIER_GUI, "Modifier key to move all matching items to the other\ninventory when scrolling over a stack");
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
