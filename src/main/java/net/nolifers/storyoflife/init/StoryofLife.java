package net.nolifers.storyoflife.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.nolifers.storyoflife.init.proxy.IProxy;


@Mod(
        modid = StoryofLife.MOD_ID,
        name = StoryofLife.MOD_NAME,
        version = StoryofLife.VERSION
)
public class StoryofLife {

    public static final String MOD_ID = "storyoflife";
    public static final String MOD_NAME = "Storyoflife";
    public static final String VERSION = "1.0-SNAPSHOT";

    @Mod.Instance(MOD_ID)
    public static StoryofLife INSTANCE;

    @SidedProxy(clientSide = "net.nolifers.storyoflife.init.proxy.ClientProxy",serverSide = "net.nolifers.storyoflife.init.proxy.ServerProxy")
    public static IProxy proxy;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        proxy.preinit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {
        proxy.postinit(event);
    }

}
