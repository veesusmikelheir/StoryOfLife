package net.nolifers.storyoflife.client.render.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.nolifers.storyoflife.entity.EntityWildebeast;

/**
 * Wildebeest - Anomalocaris101
 * Created using Tabula 7.0.0
 */
public class ModelWildebeast extends ModelQuadruped {
    ModelRenderer Body;
    ModelRenderer Leg1;
    ModelRenderer Leg2;
    ModelRenderer Leg3;
    ModelRenderer Leg4;
    ModelRenderer Hump;
    ModelRenderer Neck;
    ModelRenderer Tail;
    ModelRenderer Head;
    ModelRenderer Mane;
    ModelRenderer Horn1;
    ModelRenderer Horn1_1;
    ModelRenderer Horn2;
    ModelRenderer Horn2_1;

    float headRotationAngleX;
    public ModelWildebeast() {
        super(12,0f);
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.Leg1 = new ModelRenderer(this, 43, 35);
        this.Leg1.setRotationPoint(3.4F, 5.5F, -8.1F);
        this.Leg1.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.Body = new ModelRenderer(this, 0, 30);
        this.Body.setRotationPoint(0.0F, 6.5F, 0.0F);
        this.Body.addBox(-5.5F, -6.5F, -10.5F, 11, 13, 21, 0.0F);
        this.Leg4 = new ModelRenderer(this, 43, 35);
        this.Leg4.mirror = true;
        this.Leg4.setRotationPoint(-3.4F, 5.5F, 8.3F);
        this.Leg4.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.Tail = new ModelRenderer(this, 26, 2);
        this.Tail.setRotationPoint(0.0F, -5.0F, 9.5F);
        this.Tail.addBox(-2.0F, 0.0F, -2.0F, 4, 14, 4, 0.0F);
        this.setRotateAngle(Tail, 0.3490658503988659F, 0.0F, 0.0F);
        this.Leg3 = new ModelRenderer(this, 43, 35);
        this.Leg3.setRotationPoint(3.4F, 5.5F, 8.3F);
        this.Leg3.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.Horn1_1 = new ModelRenderer(this, 0, 46);
        this.Horn1_1.setRotationPoint(-1.0F, -3.5F, -2.5F);
        this.Horn1_1.addBox(-4.0F, -1.5F, -1.0F, 4, 3, 2, 0.0F);
        this.Horn1 = new ModelRenderer(this, 0, 46);
        this.Horn1.setRotationPoint(1.0F, -3.5F, -2.5F);
        this.Horn1.addBox(0.0F, -1.5F, -1.0F, 4, 3, 2, 0.0F);
        this.Neck = new ModelRenderer(this, 64, 47);
        this.Neck.setRotationPoint(0.0F, -1.8F, -8.5F);
        this.Neck.addBox(-4.0F, -5.0F, -7.0F, 8, 10, 7, 0.0F);
        this.setRotateAngle(Neck,.24217304763960307F,0,0);
        this.Leg2 = new ModelRenderer(this, 43, 35);
        this.Leg2.mirror = true;
        this.Leg2.setRotationPoint(-3.4F, 5.5F, -8.1F);
        this.Leg2.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.Horn2_1 = new ModelRenderer(this, 12, 44);
        this.Horn2_1.mirror = true;
        this.Horn2_1.setRotationPoint(-4.0F, 1.5F, 0.0F);
        this.Horn2_1.addBox(-1.0F, -5.0F, -1.0F, 1, 5, 2, 0.0F);
        this.Head = new ModelRenderer(this, 0, 3);
        this.Head.mirror = true;
        this.Head.setRotationPoint(0.0F, -1.0F, -6.0F);
        this.Head.addBox(-4.01F, -4.0F, -5.0F, 8, 12, 5, 0.0F);
        this.setRotateAngle(Head, -0.12217304763960307F, 0.0F, 0.0F);
        this.Mane = new ModelRenderer(this, 43, 19);
        this.Mane.setRotationPoint(0.0F, -3.0F, -3.0F);
        this.Mane.addBox(0.0F, -5.0F, -5.5F, 0, 5, 11, 0.0F);
        this.setRotateAngle(Mane, -0.08726646259971647F, 0.0F, 0.0F);
        this.Horn2 = new ModelRenderer(this, 12, 44);
        this.Horn2.setRotationPoint(4.0F, 1.5F, 0.0F);
        this.Horn2.addBox(0.0F, -5.0F, -1.0F, 1, 5, 2, 0.0F);
        this.Hump = new ModelRenderer(this, 0, 20);
        this.Hump.setRotationPoint(0.0F, -6.5F, -7.0F);
        this.Hump.addBox(-5.5F, -1.0F, -3.5F, 11, 1, 9, 0.0F);
        this.Body.addChild(this.Leg1);
        this.Body.addChild(this.Leg4);
        this.Body.addChild(this.Tail);
        this.Body.addChild(this.Leg3);
        this.Head.addChild(this.Horn1_1);
        this.Head.addChild(this.Horn1);
        this.Body.addChild(this.Neck);
        this.Body.addChild(this.Leg2);
        this.Horn1_1.addChild(this.Horn2_1);
        this.Neck.addChild(this.Head);
        this.Neck.addChild(this.Mane);
        this.Horn1.addChild(this.Horn2);
        this.Body.addChild(this.Hump);
        this.leg1=this.Leg1;
        this.leg2=this.Leg2;
        this.leg3=this.Leg3;
        this.leg4=this.Leg4;
        this.body=this.Body;
        this.head=this.Head;
    }

    @Override
    public void render(Entity entity, float a, float f1, float f2, float f3, float f4, float scale) {
        if (this.isChild) {
            float f = 2.0F;
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            GlStateManager.translate(0.0F, .4, 8.5F * scale);
            this.body.render(scale);
            GlStateManager.popMatrix();

        }
        else{
            this.body.render(scale);
        }
    }

    @Override
    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime)
    {
        super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
        this.Head.rotationPointY = -1 + ((EntityWildebeast)entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 5.0F;
        this.headRotationAngleX = ((EntityWildebeast)entitylivingbaseIn).getHeadRotationAngleX(partialTickTime);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        this.Head.rotateAngleX = this.headRotationAngleX-0.12217304763960307F;

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