package net.nolifers.storyoflife.entity.ai;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.nolifers.storyoflife.StoryofLife;
import net.nolifers.storyoflife.entity.EntityWildebeast;

import java.util.List;
import java.util.logging.Logger;

public class EntityAIFollowHerd extends EntityAIBase {

    IHerdable animal;
    IHerdable targetAnimal;
    double curDist = 0;
    int entityCount = 0;
    float stopComingBackRange = 9;
    int delayCounter;
    public boolean executeoverride;
    float speed;
    public EntityAIFollowHerd(EntityAnimal animal,float speed){
        this.animal = (IHerdable)animal;
        this.speed=speed;
        this.setMutexBits(1);
    }
    @Override
    public boolean shouldExecute() {

        //TODO: overhaul this and make a better herding system

        if(--delayCounter>0) return false;
        delayCounter=5;

        if(animal.getEntity().getGrowingAge()<0) return false;

        if(executeoverride) {
            executeoverride=false;
            getFarthestEntity();
            stopComingBackRange = entityCount*5f-animal.getEntity().world.rand.nextInt(entityCount)*4;
            return true;
        }
        IHerdable tempAnimal = getFarthestEntity();

        if(tempAnimal==null) return false;

        stopComingBackRange = entityCount*9f-animal.getEntity().world.rand.nextInt(entityCount)*4;

        if(curDist<stopComingBackRange) return false;


        targetAnimal=tempAnimal;

        return true;

    }

    IHerdable getFarthestEntity(){
        List<EntityAnimal> list = animal.getEntity().world.getEntitiesWithinAABB(animal.getEntity().getClass(), animal.getEntity().getEntityBoundingBox().grow(20, 4.0D, 20),p->!p.isEntityEqual(animal.getEntity()));
        curDist = 0;

        entityCount=list.size();
        EntityAnimal curanimal=null;
        for(EntityAnimal a : list){
            if(a.getLeashed()) {
                entityCount--;
                continue;
            }
            double dist = animal.getEntity().getDistanceSq(a);
            if(dist>curDist){
                curDist = dist;
                curanimal=a;
            }
        }
        return (IHerdable)curanimal;
    }

    public void enableOverride(IHerdable animal){
        if(this.targetAnimal!=null) return;
        this.executeoverride=true;
        targetAnimal=animal;
    }
    @Override
    public void resetTask()
    {

        this.animal.getEntity().getNavigator().clearPath();
        this.targetAnimal = null;
        stopComingBackRange=9;
    }

    @Override
    public boolean shouldContinueExecuting(){
        if(this.animal.getEntity().getNavigator().noPath()) return false;
        if(this.targetAnimal==null) return false;
        if(!targetAnimal.getEntity().isEntityAlive()){
            return false;
        }
        else {
            double dist = animal.getEntity().getDistanceSq(targetAnimal.getEntity());
            return !(dist>2500||dist<stopComingBackRange);
        }
    }

    @Override
    public void startExecuting()
    {
        this.targetAnimal.setHerdingOverride(animal);
    }

    @Override
    public void updateTask(){
        if(targetAnimal==null) return;
        this.animal.getEntity().getNavigator().tryMoveToEntityLiving(this.targetAnimal.getEntity(), this.speed);

    }
}
