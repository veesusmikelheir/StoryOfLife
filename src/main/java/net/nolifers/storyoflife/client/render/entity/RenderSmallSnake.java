package net.nolifers.storyoflife.client.render.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.nolifers.storyoflife.StoryofLife;
import net.nolifers.storyoflife.client.render.entity.model.ModelSmallSnake;
import net.nolifers.storyoflife.entity.EntitySmallSnake;

import javax.annotation.Nullable;

public class RenderSmallSnake extends RenderLiving<EntitySmallSnake> {
    public RenderSmallSnake(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn) {
        super(rendermanagerIn, modelbaseIn, shadowsizeIn);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntitySmallSnake entity) {
        return new ResourceLocation(StoryofLife.MOD_ID,"textures/entity/smallsnake/smallsnake_"+entity.getVariant()+".png");
    }


    public static class Factory implements IRenderFactory<EntitySmallSnake>{
        @Override
        public Render<? super EntitySmallSnake> createRenderFor(RenderManager manager) {
            return new RenderSmallSnake(manager,new ModelSmallSnake(),.08f);
        }
    }
}

