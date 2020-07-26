package eu.minemania.itemsortermod.event;

import eu.minemania.itemsortermod.config.Hotkeys;
import eu.minemania.itemsortermod.data.DataManager;
import eu.minemania.itemsortermod.gui.GuiConfigs;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import net.minecraft.client.MinecraftClient;
import net.minecraft.screen.AnvilScreenHandler;

public class KeyCallbacks
{
    public static void init(MinecraftClient mc)
    {
        IHotkeyCallback callbackHotkeys = new KeyCallbackHotkeys(mc);

        Hotkeys.GRAB.getKeybind().setCallback(callbackHotkeys);
        Hotkeys.GRAB_INV.getKeybind().setCallback(callbackHotkeys);
        Hotkeys.DUMP_INV_ALL.getKeybind().setCallback(callbackHotkeys);
        Hotkeys.DUMP_INV_HOT.getKeybind().setCallback(callbackHotkeys);
        Hotkeys.DUMP_INV_INV.getKeybind().setCallback(callbackHotkeys);
        Hotkeys.MOVE_STACK_TO_CONT_ALL.getKeybind().setCallback(callbackHotkeys);
        Hotkeys.MOVE_STACK_TO_CONT_HOT.getKeybind().setCallback(callbackHotkeys);
        Hotkeys.MOVE_STACK_TO_CONT_INV.getKeybind().setCallback(callbackHotkeys);
        Hotkeys.OPEN_GUI_SETTINGS.getKeybind().setCallback(callbackHotkeys);
    }

    private static class KeyCallbackHotkeys implements IHotkeyCallback
    {
        private final MinecraftClient mc;

        public KeyCallbackHotkeys(MinecraftClient mc)
        {
            this.mc = mc;
        }

        @Override
        public boolean onKeyAction(KeyAction action, IKeybind key)
        {
            if (this.mc.player == null || this.mc.world == null)
            {
                return false;
            }
            if (key == Hotkeys.OPEN_GUI_SETTINGS.getKeybind())
            {
                GuiBase.openGui(new GuiConfigs());
                return true;
            }
            else if (mc.player.currentScreenHandler != null && !mc.player.currentScreenHandler.equals(mc.player.playerScreenHandler) && !(mc.player.currentScreenHandler instanceof AnvilScreenHandler))
            {
                if (DataManager.grabCooldown < 5)
                {
                    DataManager.grabCooldown++;
                }
                if (key == Hotkeys.GRAB.getKeybind() && DataManager.grabCooldown == 5)
                {
                    DataManager.getChestSorter().grab(mc.player.currentScreenHandler);
                    DataManager.grabCooldown = 0;
                }
                else if (key == Hotkeys.DUMP_INV_ALL.getKeybind() && DataManager.grabCooldown == 5)
                {
                    if (key == Hotkeys.DUMP_INV_INV.getKeybind())
                    {
                        DataManager.getChestSorter().dumpInventory(mc.player.currentScreenHandler, false, true);
                    }
                    else if (key == Hotkeys.DUMP_INV_HOT.getKeybind())
                    {
                        DataManager.getChestSorter().dumpInventory(mc.player.currentScreenHandler, true, false);
                    }
                    else
                    {
                        DataManager.getChestSorter().dumpInventory(mc.player.currentScreenHandler, true, true);
                    }
                    DataManager.grabCooldown = 0;
                }
                else if (key == Hotkeys.GRAB_INV.getKeybind() && DataManager.grabCooldown == 5)
                {
                    DataManager.getChestSorter().grabInventory(mc.player.currentScreenHandler);
                    DataManager.grabCooldown = 0;
                }
                else if (key == Hotkeys.MOVE_STACK_TO_CONT_ALL.getKeybind() && DataManager.grabCooldown == 5)
                {
                    if (key == Hotkeys.MOVE_STACK_TO_CONT_INV.getKeybind())
                    {
                        DataManager.getChestSorter().quickStackToContainer(mc.player.currentScreenHandler, false, true);
                    }
                    else if (key == Hotkeys.MOVE_STACK_TO_CONT_HOT.getKeybind())
                    {
                        DataManager.getChestSorter().quickStackToContainer(mc.player.currentScreenHandler, true, false);
                    }
                    else
                    {
                        DataManager.getChestSorter().quickStackToContainer(mc.player.currentScreenHandler, true, true);
                    }
                    DataManager.grabCooldown = 0;
                }
                else
                {
                    return false;
                }
                return true;
            }
            return false;
        }
    }
}