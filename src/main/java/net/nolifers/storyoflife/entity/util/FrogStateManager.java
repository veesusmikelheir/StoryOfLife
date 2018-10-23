package net.nolifers.storyoflife.entity.util;

import net.minecraft.util.math.Vec3d;
import net.nolifers.storyoflife.entity.EntityFrog;

public class FrogStateManager {
    EntityFrog frog;
    float frogPitch;
    float prevFrogPitch;
    float frogSwimFactor;
    float prevFrogSwimFactor;
    EntityFrog.FrogState cachedState;
    Vec3d trueMotion;
    public FrogStateManager(EntityFrog frog){
        this.frog=frog;
    }

    public void notifyStateChange(EntityFrog.FrogState newState){
        cachedState=newState;
    }

    public void onUpdate(){
        updateFrogState();
        updateTrueMotion();
        processFrogState(this.getFrogState());
    }

    public void updateTrueMotion(){
        trueMotion = new Vec3d(frog.posX-frog.prevPosX,frog.posY-frog.prevPosY,frog.posZ-frog.prevPosZ);
    }

    public void updateFrogState(){
        if(shouldSwim())
            setFrogState(EntityFrog.FrogState.SWIMMING);
        else if(shouldStand())
            setFrogState(EntityFrog.FrogState.STANDING);
        else if(shouldJump())
            setFrogState(EntityFrog.FrogState.JUMPING);


    }
    public boolean shouldSwim(){
        return frog.isInWater();
    }

    public boolean shouldStand(){
        return frog.onGround;
    }
    public boolean shouldJump(){
        return frog.getIsJumping();
    }


    public void processFrogState(EntityFrog.FrogState state){
        switch(state){
            case STANDING:
                onStanding();
                break;
            case JUMPING:
                onJumping();
                break;
            case SWIMMING:
                onSwimming();
                break;
        }
    }

    public void onSwimming(){
        setFrogPitch(0);
        double motionXZ = Math.sqrt(trueMotion.x*trueMotion.x+trueMotion.z*trueMotion.z);
        setFrogSwimFactor((float)motionXZ/frog.getSwimSpeed());
    }

    public void onStanding(){

        setFrogPitch(0);
    }

    public void onJumping(){
        double motionXZ = Math.sqrt(trueMotion.x*trueMotion.x+trueMotion.z*trueMotion.z);



        double angle = (180/Math.PI)*Math.atan2(trueMotion.y,motionXZ);

        setFrogPitch((float)angle);



    }
    public float getFrogSwimFactor(){
        return frogSwimFactor;
    }
    public void setFrogSwimFactor(float factor){
        prevFrogSwimFactor=frogSwimFactor;
        frogSwimFactor=factor;
    }

    public float getInterpolatedFrogSwimFactor(float partialTicks){
        return this.prevFrogSwimFactor+(frogSwimFactor-prevFrogSwimFactor)*partialTicks;
    }

    public float getFrogPitch(){
        return frogPitch;
    }
    void setFrogPitch(float pitch){
        prevFrogPitch=frogPitch;
        this.frogPitch=pitch;
    }

    public float getInterpolatedFrogPitch(float partialTicks){
        return this.prevFrogPitch+(frogPitch-prevFrogPitch)*partialTicks;
    }

    public EntityFrog.FrogState getFrogState(){
        if(cachedState==null) cachedState = frog.getState();
        return cachedState;
    }

    public void setFrogState(EntityFrog.FrogState state){
        frog.setState(state);
    }


}
