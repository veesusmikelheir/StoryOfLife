package net.nolifers.storyoflife.init.proxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface IProxy {
    public void preinit(FMLPreInitializationEvent event);
    public void init(FMLInitializationEvent event);
    public void postinit(FMLPostInitializationEvent event);
}
