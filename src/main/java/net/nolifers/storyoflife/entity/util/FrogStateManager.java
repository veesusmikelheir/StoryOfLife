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

    public float frogYaw;

    public FrogStateManager(EntityFrog frog) {
        this.frog = frog;
        this.frogYaw = frog.rotationYaw;
    }

    public void notifyStateChange(EntityFrog.FrogState newState) {
        cachedState = newState;
    }

    public void onUpdate() {
        updateFrogState();
        updateTrueMotion();
        processFrogState(this.getFrogState());
        frog.rotationYaw=frogYaw;
    }

    public void updateTrueMotion() {
        trueMotion = new Vec3d(frog.posX - frog.prevPosX, frog.posY - frog.prevPosY, frog.posZ - frog.prevPosZ);
    }

    public void updateFrogState() {
        if (shouldSwim())
            setFrogState(EntityFrog.FrogState.SWIMMING);
        else if (shouldStand())
            setFrogState(EntityFrog.FrogState.STANDING);
        else if (shouldJump())
            setFrogState(EntityFrog.FrogState.JUMPING);


    }

    public boolean shouldSwim() {
        return frog.isInWater();
    }

    public boolean shouldStand() {
        return frog.onGround;
    }

    public boolean shouldJump() {
        return !frog.onGround;
    }


    public void processFrogState(EntityFrog.FrogState state) {
        switch (state) {
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

    public void onSwimming() {

        double motionXZ = Math.sqrt(trueMotion.x * trueMotion.x + trueMotion.z * trueMotion.z)*4;
        setFrogSwimFactor(Math.min(1f,(float) motionXZ / frog.getSwimSpeed()));
        setFrogPitch(-10);
    }

    public void onStanding() {

        setFrogPitch(0);
    }

    public void onJumping() {
        double motionXZ = Math.sqrt(trueMotion.x * trueMotion.x + trueMotion.z * trueMotion.z);
        double angle = (180 / Math.PI) * Math.atan2(trueMotion.y, motionXZ);
        double yawAngle = (180 / Math.PI) * Math.atan2(trueMotion.z, trueMotion.x)+90;
        frogYaw=(float)-yawAngle;
        MotionPathHelper motionPathHelper = frog.getMotionPathHelper();
        float pitchMultiplier = motionPathHelper.noPath()?1:(float)(motionPathHelper.solver.getLateralDistance(motionPathHelper.path.getStartPos(),motionPathHelper.path.getTargetPos())/2f);
        setFrogPitch((float) angle*(pitchMultiplier));
    }

    public float getFrogSwimFactor() {
        return frogSwimFactor;
    }

    public void setFrogSwimFactor(float factor) {
        prevFrogSwimFactor = frogSwimFactor;
        frogSwimFactor = factor;
    }

    public float getInterpolatedFrogSwimFactor(float partialTicks) {
        return this.prevFrogSwimFactor + (frogSwimFactor - prevFrogSwimFactor) * partialTicks;
    }

    public float getFrogPitch() {
        return frogPitch;
    }

    void setFrogPitch(float pitch) {
        prevFrogPitch = frogPitch;
        this.frogPitch = pitch;
    }

    public float getInterpolatedFrogPitch(float partialTicks) {
        return this.prevFrogPitch + (frogPitch - prevFrogPitch) * partialTicks;
    }

    public EntityFrog.FrogState getFrogState() {
        if (cachedState == null) cachedState = frog.getState();
        return cachedState;
    }

    public void setFrogState(EntityFrog.FrogState state) {
        frog.setState(state);
    }


}
