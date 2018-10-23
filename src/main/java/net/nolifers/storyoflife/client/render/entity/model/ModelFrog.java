package net.nolifers.storyoflife.client.render.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.nolifers.storyoflife.entity.EntityFrog;
import org.lwjgl.opengl.GL11;

/**
 * Frog - Anomalocaris101
 * Created using Tabula 7.0.0
 */
public class ModelFrog extends ModelBase {
    public double[] modelScale = new double[] { 2.0D, 2.0D, 2.0D };
    public ModelRenderer Body;
    public ModelRenderer Head;
    public ModelRenderer LeftArm;
    public ModelRenderer RightArm;
    public ModelRenderer LeftLeg;
    public ModelRenderer RightLeg;
    public ModelRenderer Eye;
    public ModelRenderer Eye_1;
    public ModelRenderer Feet;
    public ModelRenderer Feet_1;
    private final float DEGTORAD = (float)Math.PI/180f;
    private float swimMultiplier;

    public ModelFrog() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.LeftLeg = new ModelRenderer(this, 22, 15);
        this.LeftLeg.setRotationPoint(3.5F, 0.5F, 2.5F);
        this.LeftLeg.addBox(-1.5F, 0.0F, -2.0F, 3, 4, 4, 0.0F);
        this.setRotateAngle(LeftLeg, 0.0F, -0.2617993877991494F, 0.0F);
        this.Feet_1 = new ModelRenderer(this, 8, 23);
        this.Feet_1.mirror = true;
        this.Feet_1.setRotationPoint(0.01F, 3.8F, 2.0F);
        this.Feet_1.addBox(-1.5F, 0.0F, -6.0F, 3, 1, 6, 0.0F);
        this.setRotateAngle(Feet_1, 0.08726646259971647F, 0.0F, 0.0F);
        this.Body = new ModelRenderer(this, 0, 0);
        this.Body.setRotationPoint(0.0F, 42.5F, 0.0F);
        this.Body.addBox(-4.0F, -3.0F, -4.5F, 8, 6, 9, 0.0F);
        this.setRotateAngle(Body, -0.08726646259971647F, 0.0F, 0.0F);
        this.RightLeg = new ModelRenderer(this, 22, 15);
        this.RightLeg.mirror = true;
        this.RightLeg.setRotationPoint(-3.5F, 0.5F, 2.5F);
        this.RightLeg.addBox(-1.5F, 0.0F, -2.0F, 3, 4, 4, 0.0F);
        this.setRotateAngle(RightLeg, 0.0F, 0.2617993877991494F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 15);
        this.Head.setRotationPoint(0.0F, -0.8F, -4.5F);
        this.Head.addBox(-3.5F, -2.0F, -4.0F, 7, 4, 4, 0.0F);
        this.RightArm = new ModelRenderer(this, 0, 23);
        this.RightArm.setRotationPoint(-3.01F, 1.0F, -3.49F);
        this.RightArm.addBox(-1.0F, 0.0F, -1.0F, 2, 5, 2, 0.0F);
        this.LeftArm = new ModelRenderer(this, 0, 23);
        this.LeftArm.mirror = true;
        this.LeftArm.setRotationPoint(3.01F, 1.0F, -3.49F);
        this.LeftArm.addBox(-1.0F, 0.0F, -1.0F, 2, 5, 2, 0.0F);
        this.Eye = new ModelRenderer(this, 0, 5);
        this.Eye.setRotationPoint(2.51F, -1.4F, -1.5F);
        this.Eye.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
        this.Eye_1 = new ModelRenderer(this, 0, 5);
        this.Eye_1.mirror = true;
        this.Eye_1.setRotationPoint(-2.51F, -1.4F, -1.5F);
        this.Eye_1.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
        this.Feet = new ModelRenderer(this, 8, 23);
        this.Feet.setRotationPoint(-0.01F, 3.8F, 2.0F);
        this.Feet.addBox(-1.5F, 0.0F, -6.0F, 3, 1, 6, 0.0F);
        this.setRotateAngle(Feet, 0.08726646259971647F, 0.0F, 0.0F);
        this.Body.addChild(this.LeftLeg);
        this.RightLeg.addChild(this.Feet_1);
        this.Body.addChild(this.RightLeg);
        this.Body.addChild(this.Head);
        this.Body.addChild(this.RightArm);
        this.Body.addChild(this.LeftArm);
        this.Head.addChild(this.Eye);
        this.Head.addChild(this.Eye_1);
        this.LeftLeg.addChild(this.Feet);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(1D / modelScale[0], 1D / modelScale[1], 1D / modelScale[2]);
        this.Body.render(f5);
        GlStateManager.popMatrix();
    }

    @Override
    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
        swimMultiplier = ((EntityFrog)entitylivingbaseIn).getStateManager().getInterpolatedFrogSwimFactor(partialTickTime);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        this.Head.rotateAngleY=netHeadYaw * DEGTORAD;
        this.Head.rotateAngleX=headPitch * DEGTORAD;

        EntityFrog frog = (EntityFrog)entityIn;
        EntityFrog.FrogState state = frog.getStateManager().getFrogState();
        float angle = calculateAnimation(limbSwing,limbSwingAmount,.5f);

        float swimAngle = swimMultiplier;
        swimAngle=(swimAngle*swimAngle*swimAngle*2-1)*.25f+1.5f;
        doSwim(state== EntityFrog.FrogState.SWIMMING);
        switch(state){
            case STANDING:

                setLegAndArmAnglesStanding(angle);
                break;
            case JUMPING:
                setLegAndArmAnglesAnimated((float)Math.PI/2.2f*Math.abs(.1f+.9f*frog.getStateManager().getFrogPitch()/90f),.5f);
                break;
            case SWIMMING:
                setLegAndArmAnglesSwimming(swimAngle);
        }

    }

    protected void doSwim(boolean isSwimming){
        if(isSwimming){
            this.RightLeg.rotateAngleZ=(float)Math.PI/2;
            this.LeftLeg.rotateAngleZ=-(float)Math.PI/2;
            this.LeftArm.rotateAngleZ=-(float)Math.PI/2;
            this.RightArm.rotateAngleZ=(float)Math.PI/2;
        }
        else{
            this.RightLeg.rotateAngleZ=0;
            this.LeftLeg.rotateAngleZ=0;
            this.LeftArm.rotateAngleZ=0;
            this.RightArm.rotateAngleZ=0;
        }
    }

    protected void setLegAndArmAnglesStanding(float angle) {
        this.RightLeg.rotateAngleX=angle;
        this.LeftLeg.rotateAngleX=angle;
        this.LeftArm.rotateAngleX=-angle;
        this.RightArm.rotateAngleX=-angle;
    }
    protected void setLegAndArmAnglesSwimming(float angle) {
        this.RightLeg.rotateAngleX=angle;
        this.LeftLeg.rotateAngleX=angle;
        this.LeftArm.rotateAngleX=angle*2-3;
        this.RightArm.rotateAngleX=angle*2-3;
    }
    protected void setLegAndArmAnglesAnimated(float angle,float speed) {
        this.RightLeg.rotateAngleX+=(angle-this.RightLeg.rotateAngleX)*speed;
        this.LeftLeg.rotateAngleX+=(angle-this.LeftLeg.rotateAngleX)*speed;
        this.LeftArm.rotateAngleX+=(-angle-this.LeftArm.rotateAngleX)*speed;
        this.RightArm.rotateAngleX+=(-angle-this.RightArm.rotateAngleX)*speed;
    }

    public float calculateAnimation(float limbSwing, float limbSwingAmount,float animSpeed){
        return MathHelper.cos(limbSwing*animSpeed)*limbSwingAmount;
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
