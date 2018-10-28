package net.nolifers.storyoflife;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.nolifers.storyoflife.IProxy;
import net.nolifers.storyoflife.init.ModDataFixers;
import net.nolifers.storyoflife.init.ModMessages;
import org.apache.logging.log4j.Logger;


@Mod(
        modid = StoryofLife.MOD_ID,
        name = StoryofLife.MOD_NAME,
        version = StoryofLife.VERSION
)
public class StoryofLife {

    public static final String MOD_ID = "storyoflife";
    public static final String MOD_NAME = "Storyoflife";
    public static final String VERSION = "0.0.1";

    public static Logger logger;
    @Mod.Instance(MOD_ID)
    public static StoryofLife INSTANCE;

    @SidedProxy(clientSide = "net.nolifers.storyoflife.client.ClientProxy",serverSide = "net.nolifers.storyoflife.server.ServerProxy")
    public static IProxy proxy;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {

        logger = event.getModLog();

        ModDataFixers.registerFixes();
        proxy.preinit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        ModMessages.registerMessages();
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event)
    {
    }

}
