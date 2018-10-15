package net.nolifers.storyoflife.client.render;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.nolifers.storyoflife.client.render.entity.RenderJellyfish;
import net.nolifers.storyoflife.client.render.entity.RenderWildebeast;
import net.nolifers.storyoflife.entity.EntityJellyfish;
import net.nolifers.storyoflife.entity.EntityWildebeast;

public class ModRenders {
    public static void registerRenders(){
        RenderingRegistry.registerEntityRenderingHandler(EntityWildebeast.class, new RenderWildebeast.Factory());
        RenderingRegistry.registerEntityRenderingHandler(EntityJellyfish.class, new RenderJellyfish.Factory());
    }
}
