package net.nolifers.storyoflife.client.render.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
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
        return new ResourceLocation(StoryofLife.MOD_ID,"textures/entity/smallsnake/smallsnake_"+(entity.getState()== EntitySmallSnake.State.FULL?"full_":"")+entity.getVariant()+".png");
    }

    @Override
    protected void applyRotations(EntitySmallSnake entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {

        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
        GlStateManager.translate(0,0,-.3);
    }

    @Override
    public void doRender(EntitySmallSnake entity, double x, double y, double z, float entityYaw, float partialTicks) {

        super.doRender(entity, x, y, z, entityYaw, partialTicks);

    }

    public static class Factory implements IRenderFactory<EntitySmallSnake>{
        @Override
        public Render<? super EntitySmallSnake> createRenderFor(RenderManager manager) {
            return new RenderSmallSnake(manager,new ModelSmallSnake(),.0f);
        }
    }
}

