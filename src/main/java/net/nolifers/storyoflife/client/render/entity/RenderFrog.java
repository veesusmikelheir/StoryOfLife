package net.nolifers.storyoflife.client.render.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.nolifers.storyoflife.StoryofLife;
import net.nolifers.storyoflife.client.render.entity.model.ModelFrog;
import net.nolifers.storyoflife.entity.EntityFrog;

import javax.annotation.Nullable;

public class RenderFrog extends RenderLiving<EntityFrog> {
    public RenderFrog(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
        super(renderManagerIn, modelBaseIn, shadowSizeIn);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityFrog entity) {
        return new ResourceLocation(StoryofLife.MOD_ID,"textures/entity/frog/frog_"+entity.getVariant()+".png");
    }

    @Override
    protected void applyRotations(EntityFrog entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
        GlStateManager.rotate(entityLiving.getStateManager().getInterpolatedFrogPitch(partialTicks)/2f  ,1,0,0);
    }

    public static class Factory implements IRenderFactory<EntityFrog> {

        @Override
        public Render<? super EntityFrog> createRenderFor(RenderManager manager) {
            return new RenderFrog(manager,new ModelFrog(),.2f);
        }
    }
}
