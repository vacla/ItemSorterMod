package eu.minemania.itemsortermod.chestsorter;

import eu.minemania.itemsortermod.ItemSorterMod;
import eu.minemania.itemsortermod.data.DataManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedList;
import java.util.List;

public class ChestSorter
{
    private ScreenHandler container;
    private LinkedList<Item> items;

    public ChestSorter()
    {
        this.setItems(new LinkedList<Item>());
    }

    /**
     * Grab specified items
     *
     * @param container
     */
    public void grab(ScreenHandler container)
    {
        if (this.getItems().size() < 1)
        {
            return;
        }

        this.container = container;
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        List<Slot> slots = this.container.slots;
        for (int i = 0; i < slots.size() - 36; i++)
        {
            if (slots.get(i) != null && slots.get(i).getStack() != null)
            {
                Item currentItem = slots.get(i).getStack().getItem();
                for (int j = 0; j < this.getItems().size(); j++)
                {
                    Item item = this.getItems().get(j);
                    if (currentItem == item)
                    {
                        MinecraftClient.getInstance().interactionManager.clickSlot(container.syncId, i, 0, SlotActionType.QUICK_MOVE, player);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Set the items to grab
     *
     * @param list
     * @param isPreset
     * @return
     */
    public String setItems(String list, boolean isPreset)
    {
        String[] parsedList = list.split(",");
        StringBuilder result = new StringBuilder();
        for (String itemName : parsedList)
        {
            Item item = null;
            try
            {
                item = Registry.ITEM.get(new Identifier(itemName));
            }
            catch (Exception e)
            {
                ItemSorterMod.logger.warn("item not found: " + itemName);
            }
            if (item != Items.AIR)
            {
                this.items.addFirst(item);
            }
            else if (Item.getRawId(item) != 0)
            {
                this.items.addFirst(item);
            }
            else
            {
                DataManager.logError("No such name/preset as \"" + item + "\".");
                return "";
            }
            result.append(" ").append((new ItemStack(this.items.get(0))).getName().getString()).append(",");
        }
        if (isPreset)
        {
            return result.substring(1, result.length() - 1);
        }
        DataManager.logMessage("Now grabbing items:" + result.substring(0, result.length() - 1) + ".");
        return "";
    }

    /**
     * Dump everything into the container
     *
     * @param container
     */
    public void dumpInventory(ScreenHandler container, boolean hotbar, boolean inventory)
    {
        this.container = container;
        List<Slot> slots = this.container.slots;

        if (inventory)
        {
            for (int i = slots.size() - 36; i < slots.size() - 9; i++)
            {
                MinecraftClient.getInstance().interactionManager.clickSlot(container.syncId,
                        i, 0, SlotActionType.QUICK_MOVE, MinecraftClient.getInstance().player);
            }
        }

        if (hotbar)
        {
            for (int i = slots.size() - 9; i < slots.size(); i++)
            {
                MinecraftClient.getInstance().interactionManager.clickSlot(container.syncId,
                        i, 0, SlotActionType.QUICK_MOVE, MinecraftClient.getInstance().player);
            }
        }
    }

    /**
     * Dump only things that are already in the container
     *
     * @param container
     */
    public void quickStackToContainer(ScreenHandler container, boolean hotbar, boolean inventory)
    {
        this.container = container;
        List<Slot> slots = this.container.slots;
        // Loop through player inventory portion
        int start = slots.size() - 36;
        if (!inventory)
        {
            start += 27;
        }
        int end = slots.size();
        if (!hotbar)
        {
            end -= 9;
        }
        for (int i = start; i < end; i++)
        {
            if (!slots.get(i).hasStack())
            {
                continue;
            }
            ItemStack lookFor = slots.get(i).getStack();
            for (int j = slots.size() - 37; j >= 0; j--)
            {
                if (!slots.get(j).hasStack())
                {
                    continue;
                }
                if (lookFor.getItem().equals(slots.get(j).getStack().getItem()))
                {
                    MinecraftClient.getInstance().interactionManager.clickSlot(container.syncId,
                            i, 0, SlotActionType.QUICK_MOVE, MinecraftClient.getInstance().player);
                    break;
                }
            }
        }
    }

    /**
     * Grab everything in the container
     *
     * @param container
     */
    public void grabInventory(ScreenHandler container)
    {
        this.container = container;
        List<Slot> slots = this.container.slots;
        for (int i = 0; i < slots.size() - 36; i++)
        {
            MinecraftClient.getInstance().interactionManager.clickSlot(container.syncId, i, 0, SlotActionType.QUICK_MOVE, MinecraftClient.getInstance().player);
        }
    }

    /**
     * @return the items
     */
    public LinkedList<Item> getItems()
    {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(LinkedList<Item> items)
    {
        this.items = items;
    }
}
