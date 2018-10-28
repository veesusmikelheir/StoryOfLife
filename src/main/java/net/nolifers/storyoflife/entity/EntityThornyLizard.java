package net.nolifers.storyoflife.entity;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.nolifers.storyoflife.entity.ai.EntityAIAvoidPlayers;

public class EntityThornyLizard extends EntityCreature {

    public static final IAttribute THORNS_PERCENTAGE = new RangedAttribute(null,"thorns_percentage",.25,0,1);

    public EntityThornyLizard(World worldIn) {
        super(worldIn);
        setSize(.2f,.25f);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAvoidPlayers(this,6,1.1,1.3f));
        this.tasks.addTask(2,new EntityAIWanderAvoidWater(this,1));
        this.tasks.addTask(3,new EntityAIWatchClosest(this,EntityPlayer.class,6f));
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if(source==DamageSource.CACTUS) return false;
        boolean attackFrom = super.attackEntityFrom(source,amount);
        if(attackFrom&&!source.isExplosion()&&!source.isFireDamage()&&!source.isMagicDamage()&&source.getImmediateSource()!=null){
            source.getImmediateSource().attackEntityFrom(DamageSource.CACTUS,amount*(float)getEntityAttribute(THORNS_PERCENTAGE).getAttributeValue());
        }
        return attackFrom;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5d);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(.2d);
        getAttributeMap().registerAttribute(THORNS_PERCENTAGE);
    }
}
