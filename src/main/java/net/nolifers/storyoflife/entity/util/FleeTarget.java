package net.nolifers.storyoflife.entity.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;
import java.security.InvalidParameterException;

public class FleeTarget {


    EntityLivingBase entityTarget;
    Vec3d posTarget;



    public FleeTarget() {
    }

    public boolean hasEntityTarget(){
        return entityTarget!=null;
    }
    public boolean hasVectorTarget(){
        return posTarget!=null;
    }

    public boolean hasTarget(){
        return hasVectorTarget()||hasEntityTarget();
    }

    @Nullable
    public Vec3d getVectorTarget() {
        return posTarget;
    }

    public void setVectorTarget(Vec3d posTarget) {
        this.posTarget = posTarget;
    }

    @Nullable
    public EntityLivingBase getEntityTarget() {
        return entityTarget;
    }

    public void setEntityTarget(EntityLivingBase entityTarget) {
        this.entityTarget = entityTarget;
    }

    public void setTarget(Object target){
        if(target instanceof Vec3d){
            setVectorTarget((Vec3d)target);
        }
        else if(target instanceof EntityLivingBase){
            setEntityTarget((EntityLivingBase)target);
        }
        else{
            throw new InvalidParameterException();
        }
    }
    @Nullable
    public Vec3d getTargetAsVector(){
        if(hasVectorTarget()) return getVectorTarget();
        if(hasEntityTarget()) return entityTarget.getPositionVector();
        return null;
    }

    public void reset(){
        this.setEntityTarget(null);
        this.setVectorTarget(null);
    }
}