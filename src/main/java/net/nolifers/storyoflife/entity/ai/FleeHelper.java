package net.nolifers.storyoflife.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.nolifers.storyoflife.entity.EntitySmallSnake;
import net.nolifers.storyoflife.entity.util.FleeTarget;

public class FleeHelper {
    FleeTarget fleeTarget;
    IFlee fleer;
    boolean flee;
    public FleeHelper(EntitySmallSnake fleer) {
        this.fleer = fleer;
        this.fleeTarget=new FleeTarget();
    }

    public boolean shouldFlee() {
        return flee;
    }

    public void setFlee(boolean val) {
        flee=val;
        if(!val){
            fleeTarget.reset();
        }
    }

    public FleeTarget getFleeTarget() {
        return fleeTarget;
    }

    public void setFleeTarget(EntityLivingBase target) {
        fleeTarget.setEntityTarget(target);
        if (target == null) return;
        EntityCreature entity = fleer.getEntity();
        if (target.equals(entity.getRevengeTarget()) || target.equals(entity)) {
            entity.setAttackTarget(null);
            entity.setRevengeTarget(null);
            entity.targetTasks.taskEntries.forEach(x -> x.action.resetTask());
        }
    }
}