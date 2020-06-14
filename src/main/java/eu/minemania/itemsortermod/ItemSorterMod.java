package eu.minemania.itemsortermod;

import fi.dy.masa.malilib.MaLiLibReference;
import fi.dy.masa.malilib.event.InitializationHandler;
import fi.dy.masa.malilib.util.StringUtils;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ItemSorterMod implements ModInitializer
{
    public static final Logger logger = LogManager.getLogger(Reference.MOD_ID);

    @Override
    public void onInitialize()
    {
        try
        {
            Class.forName("fi.dy.masa.malilib.event.InitializationHandler");
            new MalilibInit().run();
        }
        catch (ClassNotFoundException e)
        {
            throw new IllegalStateException("Malilib not found. Requires Malilib for ItemSorterMod", e);
        }
        catch (LinkageError e)
        {
            throw new IllegalStateException("Incompatible Malilib version (" + StringUtils.getModVersionString(MaLiLibReference.MOD_ID) + ")" , e);
        }
    }

    // separate class to avoid loading malilib classes outside the try-catch
    private static class MalilibInit implements Runnable
    {
        @Override
        public void run()
        {
            InitializationHandler.getInstance().registerInitializationHandler(new InitHandler());
        }
    }
}
