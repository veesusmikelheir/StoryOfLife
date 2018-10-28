package net.nolifers.storyoflife.util.math;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.Vec3d;
import scala.xml.PrettyPrinter;

public class ParabolicMotionPath extends MotionPath implements ISyncedPath{
    double launchAngle;
    boolean isValidPath;
    float yawOffset;
    double verticalAcceleration=0.08;
    boolean allowInitialVelocityAdjustment;
    public ParabolicMotionPath(){
        super();

    }
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
        Vec3d delta = (targetPos.subtract(startPos));

        return new Vec3d(Math.sqrt(delta.x*delta.x+delta.z*delta.z),delta.y,0);
    }

    float calculateYawOffset(){
        Vec3d offset = targetPos.subtract(startPos);
        float v = (float)-Math.atan2(offset.z, offset.x);

        return v;
    }
    double calculateMinimumLaunchAngle(){
        Vec3d relativeTargetOffset = getRelativeTargetOffset();
        double x = Math.sqrt(relativeTargetOffset.x*relativeTargetOffset.x+relativeTargetOffset.z*relativeTargetOffset.z);
        return Math.atan(relativeTargetOffset.y/x+Math.sqrt(relativeTargetOffset.y*relativeTargetOffset.y/(x*x)+1));
    }

    double calculateLaunchAngle(boolean firstSolution){
        double sign = firstSolution?1:-1;
        double velocitySquared = initialSpeed*initialSpeed;
        Vec3d offset = getRelativeTargetOffset();
        return Math.atan((velocitySquared*sign+Math.sqrt(velocitySquared*velocitySquared-(offset.x*offset.x*verticalAcceleration+2*offset.y*velocitySquared)*verticalAcceleration))/(verticalAcceleration*offset.x));
    }

    void initPath(){
        yawOffset=calculateYawOffset();
        isValidPath=true;
        if(allowInitialVelocityAdjustment){
            initialSpeed=calculateMinimumInitialVelocity();
            launchAngle=calculateMinimumLaunchAngle();
        }
    }

    @Override
    public boolean isPathValid() {
        return isValidPath;
    }


    @Override
    public Vec3d getPositionAt(float time) {
        return new Vec3d(initialSpeed*Math.cos(launchAngle)*time,initialSpeed*Math.sin(launchAngle)*time-verticalAcceleration*time*time/2f,0).add(startPos);
    }

    @Override
    public Vec3d getVelocityAt(float time) {
        Vec3d vec3d = new Vec3d(initialSpeed * Math.cos(launchAngle), initialSpeed * Math.sin(launchAngle) - verticalAcceleration * time, 0).rotateYaw(yawOffset);

        return vec3d;
    }

    @Override
    public boolean isFinishedAt(float time) {
        return getRelativeTargetOffset().x<=getPositionAt(time).subtract(startPos).x;
    }

    @Override
    public PathType getMotionPathType() {
        return PathType.CONSTANTLYUPDATING;
    }

    @Override
    public int toBytes(ByteBuf buffer,int index) {

        buffer.writeDouble(startPos.x);
        buffer.writeDouble(startPos.y);
        buffer.writeDouble(startPos.z);
        buffer.writeDouble(targetPos.x);
        buffer.writeDouble(targetPos.y);
        buffer.writeDouble(targetPos.z);
        buffer.writeDouble(initialSpeed);
        buffer.writeDouble(launchAngle);
        buffer.writeFloat(yawOffset);
        return index;
    }

    @Override
    public MotionPath getPath(){
        return this;
    }

    @Override
    public MotionPath fromBytes(ByteBuf buffer,int index) {
        ParabolicMotionPath path = new ParabolicMotionPath();

        path.startPos= new Vec3d(buffer.readDouble(),buffer.readDouble(),buffer.readDouble());
        path.targetPos= new Vec3d(buffer.readDouble(),buffer.readDouble(),buffer.readDouble());
        path.initialSpeed=buffer.readDouble();

        path.launchAngle=buffer.readDouble();
        path.yawOffset=buffer.readFloat();
        return path;
    }
}
