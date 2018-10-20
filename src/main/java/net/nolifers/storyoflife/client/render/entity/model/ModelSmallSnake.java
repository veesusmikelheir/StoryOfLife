package net.nolifers.storyoflife.client.render.entity.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.nolifers.storyoflife.entity.EntitySmallSnake;

public class ModelSmallSnake extends ModelSegmented {
    public ModelRenderer Head;
    public ModelRenderer Tounge;
    public ModelRenderer Body;
    public ModelRenderer Segment1;
    public ModelRenderer Segment2;
    public ModelRenderer Segment3;
    public ModelRenderer Segment4;
    public ModelRenderer Segment5;
    public ModelRenderer Segment1_full;
    public ModelRenderer Segment2_full;
    public ModelRenderer Segment3_full;
    public ModelRenderer Segment4_full;
    public ModelRenderer Segment5_full;
    public ModelRenderer Tail_full;

    public ModelRenderer Tail;
    public ModelRenderer[] segments;
    public ModelRenderer[] segments_full;
    boolean shouldTongue;
    boolean headUp;
    boolean isFull;

    public ModelSmallSnake() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Segment3 = new ModelRenderer(this, 0, 7);
        this.Segment3.setRotationPoint(0.0F, 0.0F, 4.0F);
        this.Segment3.addBox(-1.5F, 0F, 0.0F, 3, 2, 4, 0.0F);

        this.Tail = new ModelRenderer(this, 0, 14);
        this.Tail.setRotationPoint(0.0F, 0.0F, 4.0F);
        this.Tail.addBox(-1.0F, 0, 0.0F, 2, 2, 4, 0.0F);
        this.Segment4 = new ModelRenderer(this, 0, 7);
        this.Segment4.setRotationPoint(0.0F, 0.0F, 4.0F);
        this.Segment4.addBox(-1.5F, 0, 0.0F, 3, 2, 4, 0.0F);
        this.Segment1 = new ModelRenderer(this, 0, 7);
        this.Segment1.setRotationPoint(0.0F, 0.0F, 2.0F);
        this.Segment1.addBox(-1.5F, 0, 0.0F, 3, 2, 4, 0.0F);
        this.Tounge = new ModelRenderer(this, 9, 1);
        this.Tounge.setRotationPoint(0.0F, 0.0F, -4.0F);
        this.Tounge.addBox(-1.5F, 0.0F, -3.0F, 3, 0, 3, 0.0F);
        this.Segment5 = new ModelRenderer(this, 0, 7);
        this.Segment5.setRotationPoint(0.0F, 0.0F, 4.0F);
        this.Segment5.addBox(-1.5F, 0, 0.0F, 3, 2, 4, 0.0F);
        this.Body = new ModelRenderer(this, 0, 7);
        this.Body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Body.addBox(-1.5F, 0, -2.0F, 3, 2, 4, 0.0F);

        this.Segment2 = new ModelRenderer(this, 0, 7);
        this.Segment2.setRotationPoint(0.0F, 0.0F, 4.0F);
        this.Segment2.addBox(-1.5F, 0, 0.0F, 3, 2, 4, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 23.0F, -1.5F);
        this.Head.addBox(-2.0F, 0, -4.0F, 4, 2, 4, 0.0F);
        //Head.offsetY=1f;
        //Segment1.offsetY=-1f;
        this.Segment2.addChild(this.Segment3);
        this.Segment5.addChild(this.Tail);
        this.Segment3.addChild(this.Segment4);
        //this.Body.addChild(this.Segment1);
        this.Head.addChild(this.Tounge);
        this.Segment4.addChild(this.Segment5);
        //this.Head.addChild(this.Body);
        this.Segment1.addChild(this.Segment2);
        this.unParent(Head,Segment1);
        //this.unParent(Body,Segment1);
        this.unParent(Head,Body);
        segments = new ModelRenderer[]{
                Segment1,
                Segment2,
                Segment3,
                Segment4,
                Segment5
        };
        this.Tail_full = new ModelRenderer(this, 0, 14);
        this.Tail_full.setRotationPoint(0.0F, 0.0F, 4.0F);
        this.Tail_full.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 4, 0.0F);
        this.Segment4_full = new ModelRenderer(this, 14, 7);
        this.Segment4_full.setRotationPoint(0.0F, 0.0F, 4.0F);
        this.Segment4_full.addBox(-2.0F, -2.0F, 0.0F, 4, 3, 4, 0.0F);
        this.Segment1_full = new ModelRenderer(this, 14, 7);
        this.Segment1_full.setRotationPoint(0.0F, 0.0F, 2.0F);
        this.Segment1_full.addBox(-2.0F, -2.0F, 0.0F, 4, 3, 4, 0.0F);
        this.Segment2_full = new ModelRenderer(this, 14, 7);
        this.Segment2_full.setRotationPoint(0.0F, 0.0F, 4.0F);
        this.Segment2_full.addBox(-2.0F, -2.0F, 0.0F, 4, 3, 4, 0.0F);
        this.Segment5_full = new ModelRenderer(this, 0, 7);
        this.Segment5_full.setRotationPoint(0.0F, 0.0F, 4.0F);
        this.Segment5_full.addBox(-1.5F, -1.0F, 0.0F, 3, 2, 4, 0.0F);
        this.Segment3_full = new ModelRenderer(this, 14, 7);
        this.Segment3_full.setRotationPoint(0.0F, 0.0F, 4.0F);
        this.Segment3_full.addBox(-2.0F, -2.0F, 0.0F, 4, 3, 4, 0.0F);
        this.Segment2_full.addChild(this.Segment3_full);
        this.Segment5_full.addChild(this.Tail_full);
        this.Segment3_full.addChild(this.Segment4_full);
        this.Segment4_full.addChild(this.Segment5_full);
        this.Segment1_full.addChild(this.Segment2_full);



        this.unParent(Head,Segment1_full);
        segments_full= new ModelRenderer[]{
                Segment1_full,
                Segment2_full,
                Segment3_full,
                Segment4_full,
                Segment5_full
        };
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        GlStateManager.pushMatrix();

        GlStateManager.scale(EntitySmallSnake.globalScale,EntitySmallSnake.globalScale,EntitySmallSnake.globalScale);
        GlStateManager.translate(0,(-64*EntitySmallSnake.globalScale+55)*f5,0);

        (isFull?this.Segment1_full:this.Segment1).render(f5);
        this.Head.render(f5);
        this.Body.render(f5);
        GlStateManager.popMatrix();
        Tounge.showModel=shouldTongue;
    }

    void unParent(ModelRenderer parent, ModelRenderer renderer){
            renderer.offsetY+=parent.offsetY;
            renderer.offsetZ+=parent.offsetZ;
            renderer.offsetX+=parent.offsetY;
            renderer.rotateAngleX+=parent.rotateAngleX;
            renderer.rotateAngleY+=parent.rotateAngleY;
            renderer.rotateAngleZ+=parent.rotateAngleZ;
            renderer.rotationPointX+=parent.rotationPointX;
            renderer.rotationPointY+=parent.rotationPointY;
            renderer.rotationPointZ+=parent.rotationPointZ;

    }


    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        limbSwing*=4;
        animateSegments(limbSwing);
        Head.offsetX=(float)(.05*Math.sin(getSlitherSpeed() *limbSwing));
        Body.offsetX=(float)(.05*Math.sin(getSlitherSpeed() *limbSwing));

        Head.rotateAngleY = (float)(netHeadYaw*Math.PI/180f);
        Head.rotateAngleX = (float)(headPitch*Math.PI/180f);

        //Body.rotateAngleY=-Head.rotateAngleY;
        if(headUp){
            Head.offsetY=-.2f;
            Body.rotateAngleX=-(float)Math.PI/4;
            Body.offsetY=-.08f;
            Body.offsetZ=.1f;
            Head.offsetZ=.035f;
            Body.rotateAngleY=(float)(getSlitherCurveSize() *Math.sin(-getSlitherCurveSize() /2)*Math.sin(limbSwing* getSlitherSpeed()))/4;
        }
        else{
            Body.rotateAngleY=(float)(getSlitherCurveSize() *Math.sin(-getSlitherCurveSize() /2)*Math.sin(limbSwing* getSlitherSpeed()))/3;
            Head.offsetY=0;
            Head.offsetZ=-.11f;
            Body.rotateAngleX=0;
            Body.offsetY=0;
            Body.offsetZ=0;
        }

        Tounge.rotateAngleX=(float)(.3*Math.cos(ageInTicks*1.3));
    }

    @Override
    protected ModelRenderer[] getSegments() {
        return isFull?segments_full:segments;
    }

    @Override
    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
        EntitySmallSnake entitylivingbaseIn1 = (EntitySmallSnake) entitylivingbaseIn;
        shouldTongue = entitylivingbaseIn1.shouldTongueFlick();
        headUp=entitylivingbaseIn1.getHeadUp();
        isFull=entitylivingbaseIn1.getState()== EntitySmallSnake.State.FULL;
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