package net.nolifers.storyoflife.entity.ai;

import com.google.common.base.Predicate;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;

import javax.annotation.Nullable;

public class EntityAITargetWhenHungry<T extends EntityLivingBase> extends EntityAINearestAttackableTarget<T> {
    IHungry hungerTarget;

    @Override
    public boolean shouldExecute() {
        if(!hungerTarget.getIsHungry()) return false;
        return super.shouldExecute();
    }

    public EntityAITargetWhenHungry(IHungry creature, Class classTarget, int chance, boolean checkSight, boolean onlyNearby, @Nullable Predicate<T> targetSelector) {
        super(creature.getEntity(), classTarget, chance, checkSight, onlyNearby, targetSelector);
        hungerTarget=creature;
    }

    public EntityAITargetWhenHungry(IHungry creature, Class classTarget, boolean checkSight, boolean onlyNearby) {
        super(creature.getEntity(), classTarget, checkSight, onlyNearby);
        this.hungerTarget=creature;
    }

    public EntityAITargetWhenHungry(IHungry creature, Class classTarget, boolean checkSight) {
        super(creature.getEntity(), classTarget, checkSight);
        this.hungerTarget = creature;
    }
}
