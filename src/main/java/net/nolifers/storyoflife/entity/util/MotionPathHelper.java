package net.nolifers.storyoflife.entity.util;

import net.minecraft.entity.EntityCreature;
import net.minecraft.util.math.Vec3d;
import net.nolifers.storyoflife.init.ModMessages;
import net.nolifers.storyoflife.network.motionpath.DisturbPathMessage;
import net.nolifers.storyoflife.network.motionpath.StartPathMessage;
import net.nolifers.storyoflife.util.math.ISyncedPath;
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
            if(checkCollisions()) return;
            updateEntity();
            checkShouldFinish();
        }
    }

    public void checkShouldFinish(){
        if(path.isFinishedAt(currentTime)){
            reset();
        }
    }

    public void updateEntityPositions(){

        creature.setEntityBoundingBox(creature.getEntityBoundingBox().offset(creature.getPositionVector().scale(-1)).offset(curPos));
    }

    public void updateEntity(){
        //if(currentTime>1) return;
        creature.motionX = curVelocity.x;
        creature.motionY = curVelocity.y;
        creature.motionZ = curVelocity.z;
        updateEntityPositions();
    }

    public boolean checkCollisions(){

        if(!creature.world.getCollisionBoxes(creature,creature.getEntityBoundingBox().offset(creature.getPositionVector().scale(-1)).offset(curPos)).isEmpty()){
            reset();
            return true;
        }
        return false;
    }

    public void updatePath(){
        currentTime+=1f;
        curVelocity=path.getVelocityAt(currentTime);
        lastPos = curPos;
        curPos=path.getPositionAt(currentTime);
    }

    public void sendPathIfNeed(){
        if(creature.world.isRemote) return;
        if(noPath()) return;
        if(!(this.path instanceof ISyncedPath)) return;
        //ModMessages.NETWORK.sendToAll(new StartPathMessage((ISyncedPath)path,creature.getEntityId()));
    }

    public void sendDisturbIfNeeded(){
        if(creature.world.isRemote) return;
        if(noPath()) return;
        //ModMessages.NETWORK.sendToAll(new DisturbPathMessage(creature.getEntityId()));
    }

    public MotionPath pathToPos(Vec3d pos){
        setPath(solver.getMotionPathToPoint(pos));
        return this.path;
    }

    public void setPath(MotionPath path){
        this.curPos = path.getStartPos();


        this.path=path;
        sendPathIfNeed();
    }
    public void disturbPath(){

        reset();
    }

    public boolean noPath(){
        return path==null;
    }

    public void reset(){
        sendDisturbIfNeeded();
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
