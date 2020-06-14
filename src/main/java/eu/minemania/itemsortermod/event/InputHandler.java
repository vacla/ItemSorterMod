package eu.minemania.itemsortermod.event;

import eu.minemania.itemsortermod.data.DataManager;
import fi.dy.masa.malilib.hotkeys.*;
import fi.dy.masa.malilib.util.KeyCodes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.container.AnvilContainer;

public class InputHandler implements IKeyboardInputHandler, IMouseInputHandler
{
    private int grabCooldown;
    private static final InputHandler INSTANCE = new InputHandler();

    private InputHandler()
    {
    }

    public static InputHandler getInstance()
    {
        return INSTANCE;
    }

    @Override
    public boolean onKeyInput(int keyCode, int scanCode, int modifiers, boolean eventKeyState)
    {
        if(eventKeyState)
        {
            this.grabCooldown = 5;
            MinecraftClient mc = MinecraftClient.getInstance();
            if (mc.player.container != null && !mc.player.container.equals(mc.player.playerContainer) && !(mc.player.container instanceof AnvilContainer))
            {
                if(this.grabCooldown < 5)
                {
                    this.grabCooldown++;
                }
                if(keyCode == KeyCodes.KEY_TAB && this.grabCooldown == 5)
                {
                    DataManager.getChestSorter().grab(mc.player.container);
                    this.grabCooldown = 0;
                }
                else if(mc.options.keyBack.isPressed() && this.grabCooldown == 5)
                {
                    if(keyCode == KeyCodes.KEY_LEFT_SHIFT)
                    {
                        DataManager.getChestSorter().dumpInventory(mc.player.container, false, true);
                    }
                    else if(keyCode == KeyCodes.KEY_LEFT_CONTROL)
                    {
                        DataManager.getChestSorter().dumpInventory(mc.player.container, true, false);
                    }
                    else
                    {
                        DataManager.getChestSorter().dumpInventory(mc.player.container, true, true);
                    }
                    this.grabCooldown = 0;
                }
                else if(mc.options.keyRight.isPressed() && this.grabCooldown == 5)
                {
                    DataManager.getChestSorter().grabInventory(mc.player.container);
                    this.grabCooldown = 0;
                }
                else if(mc.options.keyForward.isPressed() && this.grabCooldown == 5)
                {
                    if(keyCode == KeyCodes.KEY_LEFT_SHIFT)
                    {
                        DataManager.getChestSorter().quickStackToContainer(mc.player.container,false, true);
                    }
                    else if(keyCode == KeyCodes.KEY_LEFT_CONTROL)
                    {
                        DataManager.getChestSorter().quickStackToContainer(mc.player.container,true, false);
                    }
                    else
                    {
                        DataManager.getChestSorter().quickStackToContainer(mc.player.container, true, true);
                    }
                    this.grabCooldown = 0;
                }
                else if(mc.options.keyLeft.isPressed() && this.grabCooldown == 5)
                {
                    if(keyCode == KeyCodes.KEY_LEFT_SHIFT)
                    {
                        DataManager.getChestSorter().quickStackToContainer(mc.player.container, false, true);
                    }
                    else if(keyCode == KeyCodes.KEY_LEFT_CONTROL)
                    {
                        DataManager.getChestSorter().quickStackToContainer(mc.player.container,  true, false);
                    }
                    else
                    {
                        DataManager.getChestSorter().quickStackToContainer(mc.player.container, true, true);
                    }
                    this.grabCooldown = 0;
                }
                return true;
            }
        }
        return false;
    }
}