package net.nolifers.storyoflife.client.render;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.nolifers.storyoflife.client.render.entity.*;
import net.nolifers.storyoflife.entity.*;

public class ModRenders {
    public static void registerRenders(){
        RenderingRegistry.registerEntityRenderingHandler(EntityWildebeest.class, new RenderWildebeest.Factory());
        RenderingRegistry.registerEntityRenderingHandler(EntityJellyfish.class, new RenderJellyfish.Factory());
        RenderingRegistry.registerEntityRenderingHandler(EntitySmallSnake.class,new RenderSmallSnake.Factory());
        RenderingRegistry.registerEntityRenderingHandler(EntityFrog.class,new RenderFrog.Factory());
        RenderingRegistry.registerEntityRenderingHandler(EntityThornyLizard.class,new RenderThornyLizard.Factory());
    }
}
