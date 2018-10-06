package net.nolifers.storyoflife.client.render.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.nolifers.storyoflife.StoryofLife;
import net.nolifers.storyoflife.client.render.entity.model.ModelWildebeast;
import net.nolifers.storyoflife.entity.EntityWildebeast;

import javax.annotation.Nullable;

public class RenderWildebeast extends RenderLiving<EntityWildebeast> {

    RenderWildebeast(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn) {
        super(rendermanagerIn, modelbaseIn, shadowsizeIn);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityWildebeast entity) {
        return new ResourceLocation(StoryofLife.MOD_ID,"textures/entity/wildebeast.png");
    }

    @Override
    protected void applyRotations(EntityWildebeast entityLiving, float ageInTicks, float rotationYaw, float partialTicks)
    {
        super.applyRotations(entityLiving,ageInTicks,rotationYaw,partialTicks);
        GlStateManager.rotate(90f,1f,0,0);
        GlStateManager.translate(0, -1.1,-entityLiving.getEyeHeight()/2-.5);
    }


    public static class Factory implements IRenderFactory<EntityWildebeast>{

        @SuppressWarnings("unchecked")
        @Override
        public Render createRenderFor(RenderManager manager) {
            return new RenderWildebeast(manager,new ModelWildebeast(),.7f);
        }
    }

}
