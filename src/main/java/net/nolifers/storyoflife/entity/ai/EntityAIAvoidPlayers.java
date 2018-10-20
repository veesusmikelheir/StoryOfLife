package net.nolifers.storyoflife.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.Vec3d;

public class EntityAIAvoidPlayers extends EntityAIAvoidEntity {
    EntityCreature in;
    Path path;
    double farSpeed;
    float avoidDist;
    public EntityAIAvoidPlayers(EntityCreature entityIn,  float avoidDistanceIn, double farSpeedIn, double nearSpeedIn) {
        super(entityIn, EntityPlayer.class, avoidDistanceIn, farSpeedIn, nearSpeedIn);
        in = entityIn;
        farSpeed = farSpeedIn;
        avoidDist=avoidDistanceIn;
    }

    @Override
    public boolean shouldExecute() {
        this.closestLivingEntity = in.world.getClosestPlayerToEntity(in,avoidDist*2);
        if(this.closestLivingEntity==null) return false;
        if(this.closestLivingEntity.equals(in.getAttackTarget())) return false;
        Vec3d vec3d = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.entity, 16, 7, new Vec3d(this.closestLivingEntity.posX, this.closestLivingEntity.posY, this.closestLivingEntity.posZ));

        if (vec3d == null)
        {
            return false;
        }
        else if (this.closestLivingEntity.getDistanceSq(vec3d.x, vec3d.y, vec3d.z) < this.closestLivingEntity.getDistanceSq(this.entity))
        {
            return false;
        }
        else
        {
            this.path = this.in.getNavigator().getPathToXYZ(vec3d.x, vec3d.y, vec3d.z);
            return this.path != null;
        }
    }

    @Override
    public void startExecuting() {
        this.in.getNavigator().setPath(path,farSpeed);
    }
}
