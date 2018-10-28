package net.nolifers.storyoflife.client.render.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.nolifers.storyoflife.StoryofLife;
import net.nolifers.storyoflife.client.render.entity.model.ModelThornyLizard;
import net.nolifers.storyoflife.entity.EntityThornyLizard;

import javax.annotation.Nullable;

public class RenderThornyLizard extends RenderLiving<EntityThornyLizard> {

    private static final ResourceLocation BASE_TEXTURE = new ResourceLocation(StoryofLife.MOD_ID,"textures/entity/thornylizard.png");

    public RenderThornyLizard(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn) {
        super(rendermanagerIn, modelbaseIn, shadowsizeIn);
    }


    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityThornyLizard entity) {
        return BASE_TEXTURE;
    }

    public static class Factory implements IRenderFactory<EntityThornyLizard>{

        @Override
        public Render<? super EntityThornyLizard> createRenderFor(RenderManager manager) {
            return new RenderThornyLizard(manager,new ModelThornyLizard(),.2f);
        }
    }
}
