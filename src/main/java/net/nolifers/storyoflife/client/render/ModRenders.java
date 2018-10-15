package net.nolifers.storyoflife.client.render;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.nolifers.storyoflife.client.render.entity.RenderJellyfish;
import net.nolifers.storyoflife.client.render.entity.RenderWildebeest;
import net.nolifers.storyoflife.entity.EntityJellyfish;
import net.nolifers.storyoflife.entity.EntityWildebeest;

public class ModRenders {
    public static void registerRenders(){
        RenderingRegistry.registerEntityRenderingHandler(EntityWildebeest.class, new RenderWildebeest.Factory());
        RenderingRegistry.registerEntityRenderingHandler(EntityJellyfish.class, new RenderJellyfish.Factory());
    }
}
