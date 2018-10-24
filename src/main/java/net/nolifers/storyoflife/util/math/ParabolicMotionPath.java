package net.nolifers.storyoflife.util.math;

import net.minecraft.util.math.Vec3d;

public class ParabolicMotionPath extends MotionPath{
    double launchAngle;
    boolean isValidPath;
    float yawOffset;
    double verticalAcceleration;
    boolean allowInitialVelocityAdjustment;
    public ParabolicMotionPath(Vec3d startpos, Vec3d targpos, double speed,double verticalacceleration,boolean allowAdjustInitialVelocity) {
        super(startpos, targpos, speed);
        this.allowInitialVelocityAdjustment=allowAdjustInitialVelocity;
        this.verticalAcceleration=Math.abs(verticalacceleration);
        initPath();
    }

    double calculateMinimumInitialVelocity(){
        Vec3d offset = getRelativeTargetOffset();
        return Math.sqrt(verticalAcceleration*(offset.y+offset.length()));
    }

    Vec3d getRelativeTargetOffset(){
        return (targetPos.subtract(startPos)).rotateYaw(-yawOffset);
    }

    float calculateYawOffset(){
        Vec3d offset = targetPos.subtract(startPos).normalize();
        Vec3d zAxis = new Vec3d(0,0,1);
        Vec3d upAxis=new Vec3d(0,1,0);
        return (float)offset.crossProduct(upAxis).dotProduct(zAxis);
    }

    double calculateLaunchAngle(boolean firstSolution){
        double sign = firstSolution?1:-1;
        double velocitySquared = initialSpeed*initialSpeed;
        Vec3d offset = getRelativeTargetOffset();
        return Math.atan((velocitySquared*sign+Math.sqrt(velocitySquared*velocitySquared-(offset.x*offset.x*verticalAcceleration+2*offset.y*velocitySquared)*verticalAcceleration))/(verticalAcceleration*offset.x));
    }

    void initPath(){
        yawOffset=0;//calculateYawOffset();
        isValidPath=true;
        if(allowInitialVelocityAdjustment){
            initialSpeed=calculateMinimumInitialVelocity();
            launchAngle=calculateLaunchAngle(true);
        }
    }

    @Override
    public boolean isPathValid() {
        return isValidPath;
    }


    @Override
    public Vec3d getPositionAt(float time) {
        return new Vec3d(initialSpeed*Math.cos(launchAngle)*time,initialSpeed*Math.sin(launchAngle)*time-verticalAcceleration*time*time/2f,0).rotateYaw(yawOffset).add(startPos);
    }

    @Override
    public Vec3d getVelocityAt(float time) {
        return new Vec3d(initialSpeed*Math.cos(launchAngle),initialSpeed*Math.sin(launchAngle)-verticalAcceleration*time,0).rotateYaw(yawOffset);
    }

    @Override
    public boolean isFinishedAt(float time) {
        return getRelativeTargetOffset().x<getPositionAt(time).subtract(startPos).x;
    }

    @Override
    public PathType getMotionPathType() {
        return PathType.CONSTANTLYUPDATING;
    }

}
