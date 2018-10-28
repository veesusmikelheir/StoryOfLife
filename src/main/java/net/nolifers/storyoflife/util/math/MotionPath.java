package net.nolifers.storyoflife.util.math;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.Vec3d;

public abstract class MotionPath {
    protected Vec3d startPos;
    protected Vec3d targetPos;
    protected double initialSpeed;

    public MotionPath(){}

    public MotionPath(Vec3d startpos,Vec3d targpos,double speed){
        startPos=startpos;
        targetPos=targpos;
        initialSpeed=speed;
    }



    public Vec3d getStartPos() {
        return startPos;
    }

    public Vec3d getTargetPos() {
        return targetPos;
    }

    public double getInitialSpeed() {
        return initialSpeed;
    }

    public abstract boolean isPathValid();

    public abstract Vec3d getPositionAt(float time);

    public abstract Vec3d getVelocityAt(float time);

    public abstract boolean isFinishedAt(float time);

    public abstract PathType getMotionPathType();

    public enum PathType{
        INITIALVELOCITYONLY,
        CONSTANTLYUPDATING
    }

}
