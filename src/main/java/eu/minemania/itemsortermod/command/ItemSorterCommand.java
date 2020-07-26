package eu.minemania.itemsortermod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.CommandNode;
import eu.minemania.itemsortermod.Reference;
import eu.minemania.itemsortermod.data.DataManager;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

import java.util.Map;

import static com.mojang.brigadier.arguments.StringArgumentType.*;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ItemSorterCommand extends ItemSorterCommandBase
{
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        ClientCommandManager.addClientSideCommand("grab");
        LiteralArgumentBuilder<ServerCommandSource> counter = literal("grab").executes(ItemSorterCommand::info)
                .then(argument("<item[,item2]>", greedyString()).executes(ItemSorterCommand::grab))
                .then(literal("help").executes(ItemSorterCommand::help))
                .then(literal("clear").executes(ItemSorterCommand::clear))
                .then(literal("held").executes(ItemSorterCommand::held))
                .then(literal("presets").executes(ItemSorterCommand::presets));
        dispatcher.register(counter);
    }

    private static int info(CommandContext<ServerCommandSource> context)
    {
        localOutput(context.getSource(), Reference.MOD_NAME + " [" + Reference.MOD_VERSION + "]");
        localOutputT(context.getSource(), "itemsortermod.message.command.info");
        presets(context);
        return 1;
    }

    private static int clear(CommandContext<ServerCommandSource> context)
    {
        DataManager.getChestSorter().getItems().clear();
        localOutput(context.getSource(), "Cleared list of items to grab");
        return 1;
    }

    private static int held(CommandContext<ServerCommandSource> context)
    {
        if (MinecraftClient.getInstance().player.getMainHandStack() == null || MinecraftClient.getInstance().player.getMainHandStack().getItem() == null)
        {
            localError(context.getSource(), "Stop trying to grab air");
            return 0;
        }
        ItemStack heldItem = MinecraftClient.getInstance().player.getMainHandStack();
        DataManager.getChestSorter().getItems().clear();
        DataManager.getChestSorter().getItems().addFirst(heldItem.getItem());
        localOutput(context.getSource(), "Grabbing " + StringUtils.translate(heldItem.getTranslationKey()));
        return 1;
    }

    private static int presets(CommandContext<ServerCommandSource> context)
    {
        StringBuilder result = new StringBuilder("Available presets:");
        for (String preset : DataManager.getPresets().keySet())
        {
            result.append(" ").append(preset).append(",");
        }
        localOutput(context.getSource(), result.substring(0, result.length() - 1) + ".");
        return 1;
    }

    private static int grab(CommandContext<ServerCommandSource> context)
    {
        String grabMessage;
        try
        {
            grabMessage = getString(context, "<item[,item2]>");
            if (DataManager.getPresets().containsKey(grabMessage.toLowerCase()))
            {
                DataManager.getChestSorter().getItems().clear();
                String result = DataManager.getChestSorter().setItems(DataManager.getPresets().get(grabMessage), true);
                localOutput(context.getSource(), "Now grabbing preset " + grabMessage + " (" + result + ")");
            }
            else
            {
                DataManager.getChestSorter().getItems().clear();
                DataManager.getChestSorter().setItems(grabMessage, false);
            }
        }
        catch (Exception e)
        {
            localError(context.getSource(), "/grab <item[,item2]>");
        }
        return 1;
    }

    private static int help(CommandContext<ServerCommandSource> context)
    {
        localOutputT(context.getSource(), "itemsortermod.message.command.help", Reference.MOD_NAME, Reference.MOD_VERSION);
        int cmdCount = 0;
        CommandDispatcher<ServerCommandSource> dispatcher = Command.commandDispatcher;
        for (CommandNode<ServerCommandSource> command : dispatcher.getRoot().getChildren())
        {
            String cmdName = command.getName();
            if (ClientCommandManager.isClientSideCommand(cmdName))
            {
                Map<CommandNode<ServerCommandSource>, String> usage = dispatcher.getSmartUsage(command, context.getSource());
                for (String u : usage.values())
                {
                    ClientCommandManager.sendFeedback(new LiteralText("/" + cmdName + " " + u));
                }
                cmdCount += usage.size();
                if (usage.size() == 0)
                {
                    ClientCommandManager.sendFeedback(new LiteralText("/" + cmdName));
                    cmdCount++;
                }
            }
        }
        return cmdCount;
    }
}