package net.nolifers.storyoflife.entity;

import net.minecraft.client.audio.SoundList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

import javax.annotation.Nullable;

public class EntityJellyfish extends EntityWaterMob {
    private float randomMotionVecX;
    private float randomMotionVecY;
    private float randomMotionVecZ;
    public float jellyPitch;
    public float prevJellyPitch;
    public float speedFactor;
    public EntityJellyfish(World worldIn) {
        super(worldIn);
        setSize(.6f,.55f);
        rand.setSeed(getEntityId()+1);
    }



    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(.03D);
    }

    @Override
    protected void collideWithEntity(Entity entityIn) {
        super.collideWithEntity(entityIn);
        if(entityIn instanceof EntityLivingBase&&!(entityIn instanceof EntityJellyfish)){
            ((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(MobEffects.POISON,20*(int)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));
        }
    }

    @Override
    protected void initEntityAI() {
        tasks.addTask(0,new AIMoveRandom(this));
    }

    @Override
    protected float getSoundVolume() {
        return .3f;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_SMALL_SLIME_HURT;
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return LootTableList.ENTITIES_SLIME;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SMALL_SLIME_DEATH;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(this.isInWater()) {
            this.randomMotionVecX *= .988;
            this.randomMotionVecY *= .988;
            this.randomMotionVecZ *= .988;
            double speed = getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
            if (!this.world.isRemote)
            {

                this.motionX = (this.randomMotionVecX * speed);
                this.motionY = (this.randomMotionVecY * speed);
                this.motionZ = (this.randomMotionVecZ * speed);
            }
            float velocity = MathHelper.sqrt(motionX*motionX+motionY*motionY+motionZ*motionZ);
            float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.renderYawOffset += (-((float) MathHelper.atan2(this.motionX, this.motionZ)) * (180F / (float) Math.PI) - this.renderYawOffset) * 0.1F;
            this.rotationYaw = this.renderYawOffset;
            speedFactor = (float)(velocity/speed);
            if(speedFactor>1) speedFactor=1;
            float f2=speedFactor;
            f2*=.3;
            prevJellyPitch=jellyPitch;
            float val = (float)-MathHelper.atan2((double) f1, this.motionY) * (180F / (float) Math.PI)*f2;
            this.jellyPitch += ((val-this.jellyPitch)*.1f);
        }
    }

    public void setMovementVector(float randomMotionVecXIn, float randomMotionVecYIn, float randomMotionVecZIn)
    {
        this.randomMotionVecX = randomMotionVecXIn;
        this.randomMotionVecY = randomMotionVecYIn;
        this.randomMotionVecZ = randomMotionVecZIn;
    }

    public boolean hasMovementVector()
    {
        return this.randomMotionVecX != 0.0F || this.randomMotionVecY != 0.0F || this.randomMotionVecZ != 0.0F;
    }

    static class AIMoveRandom extends EntityAIBase
    {
        private final EntityJellyfish jelly;

        public AIMoveRandom(EntityJellyfish p_i45859_1_)
        {
            this.jelly = p_i45859_1_;
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            return true;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask()
        {
            int i = this.jelly.getIdleTime();

            if (i > 100)
            {
                this.jelly.setMovementVector(0.0F, 0.0F, 0.0F);
            }
            else if (this.jelly.getRNG().nextInt(50) == 0 || !this.jelly.inWater || !this.jelly.hasMovementVector())
            {
                float f = this.jelly.getRNG().nextFloat() * ((float)Math.PI * 2F);
                float f1 = MathHelper.cos(f) * 0.2F;
                float f2 = -0.1F + this.jelly.getRNG().nextFloat() * 0.2F;
                float f3 = MathHelper.sin(f) * 0.2F;
                float f4 = MathHelper.sqrt(f1*f1+f2*f2+f3*f3);
                this.jelly.setMovementVector(f1/f4, f2/f4, f3/f4);
            }
        }
    }
}
