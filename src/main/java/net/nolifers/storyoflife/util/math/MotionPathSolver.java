package net.nolifers.storyoflife.util.math;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.util.math.Vec3d;

public class MotionPathSolver {

    //TODO: Finish this
    Entity entity;
    public MotionPathSolver(Entity entity){
        this.entity=entity;
    }

    public MotionPath getMotionPathToPoint(Vec3d point){
        return new ParabolicMotionPath(entity.getPositionVector(),point,0,-0.08,true);
    }

    public double getLateralDistance(Vec3d start, Vec3d end){
        Vec3d end2 = new Vec3d(end.x,start.y,end.z);
        return start.distanceTo(end2);
    }
}
