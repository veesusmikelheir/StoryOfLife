package net.nolifers.storyoflife.client.render.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.nolifers.storyoflife.entity.EntityJellyfish;

/**
 * Jellyfish - Anomalocaris101
 * Created using Tabula 7.0.0
 */
public class ModelJellyfish extends ModelBase {
    public ModelRenderer Bell1;
    public ModelRenderer Rim;
    public ModelRenderer TentacaleL1;
    public ModelRenderer TentacaleL1_1;
    public ModelRenderer TentacaleF2;
    public ModelRenderer TentacaleR2;
    public ModelRenderer TentacaleR1;
    public ModelRenderer TentacaleB1;
    public ModelRenderer TentacaleF1;
    public ModelRenderer TentacaleB2;
    public ModelRenderer Bell2;
    public ModelRenderer Netthingy;
    public ModelRenderer Netthingy_1;
    public ModelRenderer Netthingy_2;
    public ModelRenderer[] tentacles;


    public ModelJellyfish() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.TentacaleL1_1 = new ModelRenderer(this, 0, 13);
        this.TentacaleL1_1.setRotationPoint(4.0F, 2.0F, 2.0F);
        this.TentacaleL1_1.addBox(0.0F, 0.0F, -0.5F, 0, 7, 1, 0.0F);
        this.TentacaleF2 = new ModelRenderer(this, 0, 14);
        this.TentacaleF2.setRotationPoint(2.0F, 2.0F, -4.0F);
        this.TentacaleF2.addBox(-0.5F, 0.0F, 0.0F, 1, 7, 0, 0.0F);
        this.TentacaleL1 = new ModelRenderer(this, 0, 13);
        this.TentacaleL1.setRotationPoint(4.0F, 2.0F, -2.0F);
        this.TentacaleL1.addBox(0.0F, 0.0F, -0.5F, 0, 7, 1, 0.0F);
        this.Netthingy = new ModelRenderer(this, 36, 7);
        this.Netthingy.setRotationPoint(0.5F, 1.5F, -0.2F);
        this.Netthingy.addBox(-0.5F, 0.0F, -0.5F, 1, 12, 1, 0.0F);
        this.Bell1 = new ModelRenderer(this, 0, 0);
        this.Bell1.setRotationPoint(0.0F, 21.5F, 0.0F);
        this.Bell1.addBox(-4.5F, -2.5F, -4.5F, 9, 5, 9, 0.0F);
        this.TentacaleB2 = new ModelRenderer(this, 0, 14);
        this.TentacaleB2.setRotationPoint(2.0F, 2.0F, 4.0F);
        this.TentacaleB2.addBox(-0.5F, 0.0F, 0.0F, 1, 7, 0, 0.0F);
        this.Netthingy_1 = new ModelRenderer(this, 40, 7);
        this.Netthingy_1.setRotationPoint(0.0F, 1.5F, 0.5F);
        this.Netthingy_1.addBox(-0.5F, 0.0F, -0.5F, 1, 9, 1, 0.0F);
        this.TentacaleR1 = new ModelRenderer(this, 0, 13);
        this.TentacaleR1.setRotationPoint(-4.0F, 2.0F, -2.0F);
        this.TentacaleR1.addBox(0.0F, 0.0F, -0.5F, 0, 7, 1, 0.0F);
        this.Bell2 = new ModelRenderer(this, 27, 0);
        this.Bell2.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.Bell2.addBox(-2.5F, 0.0F, -2.5F, 5, 2, 5, 0.0F);
        this.TentacaleR2 = new ModelRenderer(this, 0, 13);
        this.TentacaleR2.setRotationPoint(-4.0F, 2.0F, 2.0F);
        this.TentacaleR2.addBox(0.0F, 0.0F, -0.5F, 0, 7, 1, 0.0F);
        this.TentacaleB1 = new ModelRenderer(this, 0, 14);
        this.TentacaleB1.setRotationPoint(-2.0F, 2.0F, 4.0F);
        this.TentacaleB1.addBox(-0.5F, 0.0F, 0.0F, 1, 7, 0, 0.0F);
        this.Rim = new ModelRenderer(this, 0, 14);
        this.Rim.setRotationPoint(0.0F, 2.5F, 0.0F);
        this.Rim.addBox(-4.5F, 0.0F, -4.5F, 9, 1, 9, 0.0F);
        this.Netthingy_2 = new ModelRenderer(this, 44, 7);
        this.Netthingy_2.setRotationPoint(-0.3F, 1.5F, -0.3F);
        this.Netthingy_2.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1, 0.0F);
        this.TentacaleF1 = new ModelRenderer(this, 0, 14);
        this.TentacaleF1.setRotationPoint(-2.0F, 2.0F, -4.0F);
        this.TentacaleF1.addBox(-0.5F, 0.0F, 0.0F, 1, 7, 0, 0.0F);
        this.Bell1.addChild(this.TentacaleL1_1);
        this.Bell1.addChild(this.TentacaleF2);
        this.Bell1.addChild(this.TentacaleL1);
        this.Bell2.addChild(this.Netthingy);
        this.Bell1.addChild(this.TentacaleB2);
        this.Bell2.addChild(this.Netthingy_1);
        this.Bell1.addChild(this.TentacaleR1);
        this.Bell1.addChild(this.Bell2);
        this.Bell1.addChild(this.TentacaleR2);
        this.Bell1.addChild(this.TentacaleB1);
        this.Bell1.addChild(this.Rim);
        this.Bell2.addChild(this.Netthingy_2);
        this.Bell1.addChild(this.TentacaleF1);
        tentacles = new ModelRenderer[]{
            TentacaleB1,
            TentacaleB2,
            TentacaleF1,
            TentacaleF2,
            TentacaleL1,
            TentacaleL1_1,
            TentacaleR1,
            TentacaleR2

        };
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.Bell1.render(f5);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        EntityJellyfish fish = ((EntityJellyfish)entityIn);
        float speedFactor = fish.speedFactor*.9f+.1f;
        for(int i =0;i<tentacles.length;i++){
            tentacles[i].rotateAngleX=.1f*(float)Math.sin(i+ageInTicks*.3)*speedFactor;
        }

        float pitch = (fish.jellyPitch)*.4f*(float)(Math.PI/180f);
        float f = (float)Math.sin(ageInTicks*.15f)*speedFactor;
        f*=f*.1;
        f-=pitch;
        Netthingy.rotateAngleX=f;
        Netthingy_1.rotateAngleX=f;
        Netthingy_2.rotateAngleX=f;
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
