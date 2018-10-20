package net.nolifers.storyoflife.entity.ai;

import com.google.common.base.Predicates;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class EntityAIFleeFromTarget extends EntityAIBase {
    IFlee fleer;
    double farSpeed;
    double nearSpeed;
    float avoidDistance;
    Path path;
    PathNavigate navigation;
    public EntityAIFleeFromTarget(IFlee fleer,float avoidDistance, double farSpeed,double nearSpeed){

        this.fleer=fleer;
        this.navigation=fleer.getEntity().getNavigator();
        this.avoidDistance=avoidDistance;
        this.farSpeed=farSpeed;

        this.nearSpeed=nearSpeed;
        setMutexBits(1);
    }
    @Override
    public boolean shouldExecute()
    {
        FleeHelper fleeHelper = fleer.getFleeHelper();
        if(!fleeHelper.shouldFlee()) return false;
        if(!fleeHelper.getFleeTarget().hasTarget()) return false;


        Vec3d targetAsVector = fleeHelper.getFleeTarget().getTargetAsVector();
        Vec3d vec3d = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.fleer.getEntity(), 16, 7, new Vec3d(targetAsVector.x, targetAsVector.y, targetAsVector.z));

        if (vec3d == null)
        {
            return false;
        }
        else {

            if (targetAsVector.squareDistanceTo(vec3d.x, vec3d.y, vec3d.z) < targetAsVector.squareDistanceTo(this.fleer.getEntity().getPositionVector()))
            {
                return false;
            }
            else
            {
                this.path = this.navigation.getPathToXYZ(vec3d.x, vec3d.y, vec3d.z);
                return this.path != null;
            }
        }

    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
        return !this.navigation.noPath();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.navigation.setPath(this.path, this.farSpeed);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask()
    {
        fleer.getFleeHelper().setFlee(false);
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        if (this.fleer.getEntity().getPositionVector().squareDistanceTo(this.fleer.getFleeHelper().getFleeTarget().getTargetAsVector()) < 49.0D)
        {
            this.fleer.getEntity().getNavigator().setSpeed(this.nearSpeed);
        }
        else
        {
            this.fleer.getEntity().getNavigator().setSpeed(this.farSpeed);
        }
    }
}
