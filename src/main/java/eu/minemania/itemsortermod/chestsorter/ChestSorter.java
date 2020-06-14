package eu.minemania.itemsortermod.chestsorter;

import eu.minemania.itemsortermod.data.DataManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.container.Container;
import net.minecraft.container.Slot;
import net.minecraft.container.SlotActionType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;

import java.util.LinkedList;
import java.util.List;

public class ChestSorter 
{
	private Container container;
	private LinkedList<Integer> items;

	public ChestSorter()
	{
		this.setItems(new LinkedList<Integer>());
	}

	/**
	 * Grab specified items
	 * @param container
	 */
	public void grab(Container container)
	{
		if (this.getItems().size() < 1)
			return;
		this.container = container;
		ClientPlayerEntity player = MinecraftClient.getInstance().player;
		List<Slot> slots = this.container.slots;
		for (int i = 0; i < slots.size() - 36; i++)
		{
			if (slots.get(i) != null && slots.get(i).getStack() != null)
			{
				int currID = Item.getRawId(slots.get(i).getStack().getItem());
				for (int j = 0; j < this.getItems().size(); j++)
				{
					int id = this.getItems().get(j);
					if (currID == id)
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
	 * @param list
	 * @param isPreset
	 * @return
	 */
	public String setItems(String list, boolean isPreset)
	{
		String[] parsedList = list.split(",");
		StringBuilder result = new StringBuilder();
		for (String item: parsedList)
		{
			String[] parts = item.split(":");
			if (parts[0].matches("[0-9]+") && Registry.ITEM.get(Integer.parseInt(parts[0])) != Items.AIR)
			{
				this.items.addFirst(Integer.parseInt(parts[0]));
			}
			else if (Item.byRawId(Integer.parseInt(parts[0])) != null)
			{
				this.items.addFirst(Item.getRawId(Item.byRawId(Integer.parseInt(parts[0]))));
			}
			else
			{
				DataManager.logError("No such item ID/name/preset as \"" + item + "\".");
				return "";
			}
			result.append(" ").append((new ItemStack(Item.byRawId(this.items.get(0)))).getName().getString()).append(",");
		}
		if (isPreset)
			return result.substring(1, result.length() - 1);
		DataManager.logMessage("Now grabbing items:" + result.substring(0, result.length() - 1) + ".");
		return "";
	}

	/**
	 * Dump everything into the container
	 * @param container
	 */
	public void dumpInventory(Container container, boolean hotbar, boolean inventory)
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
	 * @param container
	 */
	public void quickStackToContainer(Container container, boolean hotbar, boolean inventory)
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
					if (lookFor != slots.get(j).getStack())
					{
						continue; // Additional check for metadata
					}
					MinecraftClient.getInstance().interactionManager.clickSlot(container.syncId,
							i, 0, SlotActionType.QUICK_MOVE, MinecraftClient.getInstance().player);
					break;
				}
			}
		}
	}

	/**
	 * Grab everything in the container
	 * @param container
	 */
	public void grabInventory(Container container)
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
	public LinkedList<Integer> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(LinkedList<Integer> items) {
		this.items = items;
	}
}
