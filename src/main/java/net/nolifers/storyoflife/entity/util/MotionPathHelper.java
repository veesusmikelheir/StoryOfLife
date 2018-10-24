package net.nolifers.storyoflife.entity.util;

import net.minecraft.entity.EntityCreature;
import net.minecraft.util.math.Vec3d;
import net.nolifers.storyoflife.util.math.MotionPath;
import net.nolifers.storyoflife.util.math.MotionPathSolver;

public class MotionPathHelper {
    EntityCreature creature;
    MotionPath path;
    float currentTime;
    MotionPathSolver solver;
    Vec3d curVelocity;
    Vec3d curPos;
    Vec3d lastPos;
    public MotionPathHelper(IMotionPathFollower follower){
        this.creature=follower.getEntity();
        this.solver=new MotionPathSolver(creature);
    }

    public void onUpdate(){
        if(!noPath()) {
            updatePath();
            updateEntity();
            checkShouldFinish();
        }
    }

    public void checkShouldFinish(){
        if(path.isFinishedAt(currentTime)){
            this.curPos=path.getTargetPos();
            updateEntity();
            reset();
        }
    }

    public void updateEntity(){
        creature.motionX=curVelocity.x;
        creature.motionY=curVelocity.y;
        creature.motionZ=curVelocity.z;
        creature.prevPosX=lastPos.x;
        creature.prevPosY=lastPos.y;
        creature.prevPosZ=lastPos.z;
        creature.setEntityBoundingBox(creature.getEntityBoundingBox().offset(creature.getPositionVector().scale(-1)).offset(curPos));
        creature.resetPositionToBB();

    }

    public void updatePath(){
        currentTime+=.5f;
        curVelocity=path.getVelocityAt(currentTime);
        lastPos = curPos;
        curPos=path.getPositionAt(currentTime);
    }

    public MotionPath pathToPos(Vec3d pos){
        setPath(solver.getMotionPathToPoint(pos));
        return this.path;
    }

    public void setPath(MotionPath path){
        this.curPos = path.getStartPos();
        this.path=path;
    }
    public void disturbPath(){
        //reset();
    }

    public boolean noPath(){
        return path==null;
    }

    public void reset(){
        this.path=null;
        this.currentTime=0;
    }

    public Vec3d getCurVelocity() {
        return curVelocity;
    }


    public Vec3d getCurPos() {
        return curPos;
    }

}
