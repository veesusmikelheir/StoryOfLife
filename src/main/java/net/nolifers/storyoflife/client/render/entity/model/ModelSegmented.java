package net.nolifers.storyoflife.client.render.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public abstract class ModelSegmented extends ModelBase {
    private float curveSize = 1.25f;
    private float slitherSpeed = .3f;

    public float getSlitherCurveSize() {
        return curveSize;
    }

    public void setSlitherCurveSize(float val){
        this.curveSize=val;
    }

    public float getSlitherSpeed() {
        return slitherSpeed;
    }

    public void setSlitherSpeed(float slitherSpeed) {
        this.slitherSpeed = slitherSpeed;
    }

    protected void animateSegments(float limbSwing) {
        ModelRenderer[] segmentsArray = getSegments();
        for(int i = 0;i<segmentsArray.length;i++){
            segmentsArray[i].rotateAngleY=(float)(curveSize*Math.sin(i*Math.PI/2-curveSize/2)*Math.sin(limbSwing*slitherSpeed+curveSize*i/Math.PI));

        }
    }

    protected abstract ModelRenderer[] getSegments();
}
