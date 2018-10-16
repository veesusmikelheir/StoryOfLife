package net.nolifers.storyoflife.client.render.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.nolifers.storyoflife.StoryofLife;
import net.nolifers.storyoflife.client.render.entity.model.ModelJellyfish;
import net.nolifers.storyoflife.entity.EntityJellyfish;

import javax.annotation.Nullable;
import javax.swing.text.html.parser.Entity;

public class RenderJellyfish extends RenderLiving<EntityJellyfish> {
    public RenderJellyfish(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn) {
        super(rendermanagerIn, modelbaseIn, shadowsizeIn);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityJellyfish entity) {
        return new ResourceLocation(StoryofLife.MOD_ID,"textures/entity/jellyfish.png");
    }

    @Override
    public void doRender(EntityJellyfish entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        GlStateManager.disableBlend();

    }

    @Override
    protected void applyRotations(EntityJellyfish entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
        GlStateManager.translate(0,.15,0);
        GlStateManager.rotate(entityLiving.prevJellyPitch+(entityLiving.jellyPitch-entityLiving.prevJellyPitch)*partialTicks,1,0,0);
        //GlStateManager.translate(0,-.35-.45f,0);
        if(entityLiving.isInWater()) return;
        GlStateManager.scale(1.4,.6,1.4);

    }

    public static class Factory implements IRenderFactory<EntityJellyfish> {

        @Override
        public Render<? super EntityJellyfish> createRenderFor(RenderManager manager) {
            return new RenderJellyfish(manager,new ModelJellyfish(),.5f);
        }
    }
}
