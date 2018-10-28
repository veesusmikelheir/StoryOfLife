package net.nolifers.storyoflife.client.render.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

/**
 * ModelThornyLizard - Anomalocaris101
 * Created using Tabula 7.0.0
 */
public class ModelThornyLizard extends ModelBase {
    public double[] modelScale = new double[] { 2.2D, 2.2D, 2.2D };
    public ModelRenderer Body;
    public ModelRenderer LeftLeg1;
    public ModelRenderer Tail;
    public ModelRenderer head;
    public ModelRenderer RightLeg1;
    public ModelRenderer LeftLeg2;
    public ModelRenderer RightLeg2;
    public ModelRenderer Sidespikes;
    public ModelRenderer Sidespikes_1;
    public ModelRenderer Tail2;
    public ModelRenderer Sidespikes_2;
    public ModelRenderer Sidespikes_3;
    public ModelRenderer Headspikes;

    public ModelThornyLizard() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Headspikes = new ModelRenderer(this, -4, 23);
        this.Headspikes.setRotationPoint(0.0F, -0.2F, -2.1F);
        this.Headspikes.addBox(-3.0F, 0.0F, 0.0F, 6, 0, 4, 0.0F);
        this.setRotateAngle(Headspikes, 0.6981317007977318F, 0.0F, 0.0F);
        this.RightLeg2 = new ModelRenderer(this, 38, 0);
        this.RightLeg2.setRotationPoint(-2.5F, 1.0F, 3.5F);
        this.RightLeg2.addBox(-1.0F, 0.0F, -1.0F, 2, 5, 2, 0.0F);
        this.setRotateAngle(RightLeg2, 0.2617993877991494F, 0.0F, 0.6981317007977318F);
        this.LeftLeg1 = new ModelRenderer(this, 38, 0);
        this.LeftLeg1.setRotationPoint(2.5F, 1.0F, -3.5F);
        this.LeftLeg1.addBox(-1.0F, 0.0F, -1.0F, 2, 5, 2, 0.0F);
        this.setRotateAngle(LeftLeg1, -0.2617993877991494F, 0.0F, -0.6981317007977318F);
        this.RightLeg1 = new ModelRenderer(this, 38, 0);
        this.RightLeg1.setRotationPoint(-2.5F, 1.0F, -3.5F);
        this.RightLeg1.addBox(-1.0F, 0.0F, -1.0F, 2, 5, 2, 0.0F);
        this.setRotateAngle(RightLeg1, -0.2617993877991494F, 0.0F, 0.6981317007977318F);
        this.Sidespikes_1 = new ModelRenderer(this, -3, 0);
        this.Sidespikes_1.setRotationPoint(-3.0F, 0.5F, 0.0F);
        this.Sidespikes_1.addBox(-2.0F, 0.0F, -4.5F, 2, 0, 9, 0.0F);
        this.Sidespikes_3 = new ModelRenderer(this, -1, 0);
        this.Sidespikes_3.setRotationPoint(-1.6F, 0.5F, 1.5F);
        this.Sidespikes_3.addBox(-2.0F, 0.0F, -1.5F, 2, 0, 3, 0.0F);
        this.setRotateAngle(Sidespikes_3, 0.0F, 0.2617993877991494F, 0.0F);
        this.head = new ModelRenderer(this, 0, 15);
        this.head.setRotationPoint(0.0F, -1.0F, -4.5F);
        this.head.addBox(-2.0F, -2.0F, -4.0F, 4, 4, 4, 0.0F);
        this.Tail2 = new ModelRenderer(this, 24, 0);
        this.Tail2.setRotationPoint(0.0F, -0.2F, 2.5F);
        this.Tail2.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 5, 0.0F);
        this.setRotateAngle(Tail2, 0.03490658503988659F, 0.0F, 0.0F);
        this.Sidespikes = new ModelRenderer(this, -3, 0);
        this.Sidespikes.setRotationPoint(3.0F, 0.5F, 0.0F);
        this.Sidespikes.addBox(0.0F, 0.0F, -4.5F, 2, 0, 9, 0.0F);
        this.Sidespikes_2 = new ModelRenderer(this, -1, 0);
        this.Sidespikes_2.setRotationPoint(1.6F, 0.5F, 1.5F);
        this.Sidespikes_2.addBox(0.0F, 0.0F, -1.5F, 2, 0, 3, 0.0F);
        this.setRotateAngle(Sidespikes_2, 0.0F, -0.2617993877991494F, 0.0F);
        this.LeftLeg2 = new ModelRenderer(this, 38, 0);
        this.LeftLeg2.setRotationPoint(2.5F, 1.0F, 3.5F);
        this.LeftLeg2.addBox(-1.0F, 0.0F, -1.0F, 2, 5, 2, 0.0F);
        this.setRotateAngle(LeftLeg2, 0.2617993877991494F, 0.0F, -0.6981317007977318F);
        this.Tail = new ModelRenderer(this, 18, 15);
        this.Tail.setRotationPoint(0.0F, -0.5F, 4.5F);
        this.Tail.addBox(-2.0F, -1.5F, 0.0F, 4, 3, 3, 0.0F);
        this.setRotateAngle(Tail, 0.03490658503988659F, 0.0F, 0.0F);
        this.Body = new ModelRenderer(this, 0, 0);
        this.Body.setRotationPoint(0.0F, 48.5F, 0.0F);
        this.Body.addBox(-3.5F, -2.5F, -5.0F, 7, 5, 10, 0.0F);
        this.setRotateAngle(Body, -0.03490658503988659F, 0.0F, 0.0F);
        this.head.addChild(this.Headspikes);
        this.Body.addChild(this.RightLeg2);
        this.Body.addChild(this.LeftLeg1);
        this.Body.addChild(this.RightLeg1);
        this.Body.addChild(this.Sidespikes_1);
        this.Tail.addChild(this.Sidespikes_3);
        this.Body.addChild(this.head);
        this.Tail.addChild(this.Tail2);
        this.Body.addChild(this.Sidespikes);
        this.Tail.addChild(this.Sidespikes_2);
        this.Body.addChild(this.LeftLeg2);
        this.Body.addChild(this.Tail);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(1D / modelScale[0], 1D / modelScale[1], 1D / modelScale[2]);
        this.Body.render(f5);
        GlStateManager.popMatrix();
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        float DEGTORAD = (float)Math.PI/180f;
        this.head.rotateAngleY=netHeadYaw*DEGTORAD;
        this.head.rotateAngleX=headPitch*DEGTORAD;
        if(limbSwingAmount>1.3) limbSwingAmount=1.3f;
        LeftLeg1.rotateAngleX=computeLimbAnimation(limbSwing,limbSwingAmount);
        LeftLeg2.rotateAngleX=-computeLimbAnimation(limbSwing,limbSwingAmount);
        RightLeg1.rotateAngleX=computeLimbAnimation(limbSwing,limbSwingAmount);
        RightLeg2.rotateAngleX=-computeLimbAnimation(limbSwing,limbSwingAmount);

        Tail.rotateAngleY=computeLimbAnimation(limbSwing,limbSwingAmount,.75f*1.3f,.15f);
        Tail2.rotateAngleY=computeLimbAnimation(limbSwing,limbSwingAmount,.75f*1.3f,.25f);

    }


    public float computeLimbAnimation(float limbSwing, float limbSwingAmount, float speed, float multiplier){
        return MathHelper.cos(limbSwing*speed)*limbSwingAmount*multiplier;
    }

    public float computeLimbAnimation(float limbSwing,float limbSwingAmount){
        return computeLimbAnimation(limbSwing,limbSwingAmount,1.3f,1.1f);
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