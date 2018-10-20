package net.nolifers.storyoflife.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

import java.util.function.Predicate;

public class EntityAISneakOnTarget extends EntityAIBase
{
    EntityLivingBase lastTarget;
    EntityLiving entityIn;
    double sneakSpeed;
    int delay=0;
    Predicate<EntityLivingBase> sneakFilter;
    public EntityAISneakOnTarget(EntityLiving in,double sneakSpeed,Predicate<EntityLivingBase> sneakFilter){
        this.entityIn=in;
        this.sneakSpeed=sneakSpeed;
        this.sneakFilter=sneakFilter;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {

        if(isLastTargetValid()) return false;
        if(entityIn.getAttackTarget()==null) return false;
        //if((entityIn.getAttackTarget().getRevengeTarget()==null?false:entityIn.getAttackTarget().getRevengeTarget().getClass().isInstance(entityIn))) return false;
        if(!sneakFilter.test(entityIn.getAttackTarget())) return false;
        return true;
    }

    @Override
    public boolean shouldContinueExecuting() {
        //TODO: add check for attacks from other members of same species
        return (!isLastTargetValid()&&entityIn.getAttackTarget()!=null&&!entityIn.getAttackTarget().isDead);//&&!(entityIn.getAttackTarget().getRevengeTarget()==null?false:entityIn.getAttackTarget().getRevengeTarget().getClass().isInstance(entityIn)));
    }

    @Override
    public void resetTask() {
        entityIn.getNavigator().clearPath();
    }

    @Override
    public void updateTask() {
        EntityLivingBase target = entityIn.getAttackTarget();
        entityIn.getLookHelper().setLookPositionWithEntity(target, 30.0F, 30.0F);
        double d0 = this.entityIn.getDistanceSq(target.posX, target.getEntityBoundingBox().minY, target.posZ);
        entityIn.getNavigator().tryMoveToEntityLiving(target,sneakSpeed);
        if(checkIfGoodDist(target,d0)){
            lastTarget=target;

        }

    }
    protected boolean checkIfGoodDist(EntityLivingBase enemy, double distToEnemySqr)
    {
        double d0 = this.getAttackReachSqr(enemy);

        return (distToEnemySqr <= d0);
    }

    protected double getAttackReachSqr(EntityLivingBase attackTarget)
    {
        return (double)(this.entityIn.width * 2.0F * this.entityIn.width * 2.0F + attackTarget.width);
    }

    boolean isLastTargetValid(){
        if(entityIn.getAttackTarget()!=null&&entityIn.getAttackTarget().getRevengeTarget() != null && entityIn.getAttackTarget().getRevengeTarget().getClass().isInstance(entityIn)){
            return true;
        }
        if(lastTarget==null) return false;

        if(lastTarget.isDead||entityIn.getAttackTarget()==null||!lastTarget.equals(entityIn.getAttackTarget())){
            lastTarget=null;
            return false;
        }
        return true;
    }
}
