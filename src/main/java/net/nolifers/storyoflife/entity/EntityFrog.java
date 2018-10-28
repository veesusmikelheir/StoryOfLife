package net.nolifers.storyoflife.entity;

import com.google.common.base.Objects;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.nolifers.storyoflife.entity.util.FrogStateManager;
import net.nolifers.storyoflife.entity.util.IMotionPathFollower;
import net.nolifers.storyoflife.entity.util.MotionPathHelper;

import javax.annotation.Nullable;

public class EntityFrog extends EntityCreature implements IMotionPathFollower {

    public static final DataParameter<Byte> VARIANT = EntityDataManager.createKey(EntityFrog.class, DataSerializers.BYTE);
    public static final DataParameter<Byte> STATE = EntityDataManager.createKey(EntityFrog.class,DataSerializers.BYTE);
    public static final int VARIANT_COUNT=4;

    FrogStateManager stateManager;

    MotionPathHelper motionPathHelper;
    int number;

    @Override
    protected void onInsideBlock(IBlockState p_191955_1_) {
        //motionPathHelper.disturbPath();
        super.onInsideBlock(p_191955_1_);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAIWander(this,1));
    }

    @Override
    public void onUpdate() {
        if(!world.isRemote) motionPathHelper.onUpdate();
        stateManager.onUpdate();
        super.onUpdate();

        stateManager.onUpdate();

        checkIfShouldDisturbPath();
        if(world.isRemote) return;

    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(5==5) return;
        if (this.newPosRotationIncrements > 0 && !this.canPassengerSteer())
        {
            double d0 = this.posX + (this.prevPosX - this.posX) / (double)this.newPosRotationIncrements;
            double d1 = this.posY + (this.prevPosY - this.posY) / (double)this.newPosRotationIncrements;
            double d2 = this.posZ + (this.prevPosZ - this.posZ) / (double)this.newPosRotationIncrements;
            double d3 = MathHelper.wrapDegrees(this.interpTargetYaw - (double)this.rotationYaw);
            this.rotationYaw = (float)((double)this.rotationYaw + d3 / (double)this.newPosRotationIncrements);
            this.rotationPitch = (float)((double)this.rotationPitch + (this.interpTargetPitch - (double)this.rotationPitch) / (double)this.newPosRotationIncrements);
            --this.newPosRotationIncrements;
            this.setPosition(d0, d1, d2);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        }

        if (Math.abs(this.motionX) < 0.003D)
        {
            this.motionX = 0.0D;
        }

        if (Math.abs(this.motionY) < 0.003D)
        {
            this.motionY = 0.0D;
        }

        if (Math.abs(this.motionZ) < 0.003D)
        {
            this.motionZ = 0.0D;
        }

        this.world.profiler.startSection("ai");

        if (this.isMovementBlocked())
        {
            this.isJumping = false;
            this.moveStrafing = 0.0F;
            this.moveForward = 0.0F;
            this.randomYawVelocity = 0.0F;
        }
        else if (this.isServerWorld())
        {
            this.world.profiler.startSection("newAi");
            this.updateEntityActionState();
            this.world.profiler.endSection();
        }

        this.world.profiler.endSection();
        this.world.profiler.startSection("jump");

        if (this.isJumping)
        {
            if (this.isInWater())
            {
                this.handleJumpWater();
            }
            else if (this.isInLava())
            {
                this.handleJumpLava();
            }
            /*else if (this.onGround && this.jumpTicks == 0)
            {
                this.jump();
                this.jumpTicks = 10;
            }*/
        }
        else
        {
            //this.jumpTicks = 0;
        }

        this.world.profiler.endSection();
        this.world.profiler.startSection("travel");
        //this.moveStrafing *= 0.98F;
        //this.moveForward *= 0.98F;
        this.randomYawVelocity *= 0.9F;
        this.travel(this.moveStrafing, this.moveVertical, this.moveForward);
        this.world.profiler.endSection();
        this.world.profiler.startSection("push");
        this.collideWithNearbyEntities();
        this.world.profiler.endSection();
    }

    @Override
    public void travel(float strafe, float vertical, float forward) {
        super.travel(strafe,vertical,forward);
        if(5==5) return;
        if (!this.isInWater())
        {
            if (!this.isInLava())
            {

                float f6 = motionPathHelper.noPath()?.91f:1f;
                BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain(this.posX, this.getEntityBoundingBox().minY - 1.0D, this.posZ);

                if (this.onGround)
                {
                    IBlockState underState = this.world.getBlockState(blockpos$pooledmutableblockpos);
                    //f6 = underState.getBlock().getSlipperiness(underState, this.world, blockpos$pooledmutableblockpos, this) * 0.91F;
                }

                float f7 = 0.16277136F / (f6 * f6 * f6);
                float f8;

                if (this.onGround)
                {
                    f8 = this.getAIMoveSpeed() * f7;
                }
                else
                {
                    f8 = this.jumpMovementFactor;
                }

                //this.moveRelative(strafe, vertical, forward, f8);
                f6 = motionPathHelper.noPath()?.91f:1f;

                if (this.onGround)
                {
                    IBlockState underState = this.world.getBlockState(blockpos$pooledmutableblockpos.setPos(this.posX, this.getEntityBoundingBox().minY - 1.0D, this.posZ));

                    f6 =underState.getBlock().getSlipperiness(underState, this.world, blockpos$pooledmutableblockpos, this) * 0.91F;
                }


                this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);


                if (this.isPotionActive(MobEffects.LEVITATION))
                {
                    this.motionY += (0.05D * (double)(this.getActivePotionEffect(MobEffects.LEVITATION).getAmplifier() + 1) - this.motionY) * 0.2D;
                }
                else
                {
                    blockpos$pooledmutableblockpos.setPos(this.posX, 0.0D, this.posZ);

                    if (this.world.isBlockLoaded(blockpos$pooledmutableblockpos) && this.world.getChunk(blockpos$pooledmutableblockpos).isLoaded())
                    {
                        if (!this.hasNoGravity()&&getMotionPathHelper().noPath())
                        {
                            this.motionY -= 0.08D;
                        }
                    }
                    else if (this.posY > 0.0D)
                    {
                        this.motionY = -0.1D;
                    }
                    else
                    {
                        this.motionY = 0.0D;
                    }
                }

                //this.motionY *= 0.9800000190734863D;
                this.motionX *= (double)f6;
                this.motionZ *= (double)f6;
                blockpos$pooledmutableblockpos.release();

            }
            else
            {
                double d4 = this.posY;
                this.moveRelative(strafe, vertical, forward, 0.02F);
                this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
                this.motionX *= 0.5D;
                this.motionY *= 0.5D;
                this.motionZ *= 0.5D;

                if (!this.hasNoGravity())
                {
                    this.motionY -= 0.02D;
                }

                if (this.collidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579D - this.posY + d4, this.motionZ))
                {
                    this.motionY = 0.30000001192092896D;
                }
            }
        }
        else
        {
            double d0 = this.posY;
            float f2 = 0.02F;


            this.moveRelative(strafe, vertical, forward, f2);
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);

            if (!this.hasNoGravity())
            {
                this.motionY -= 0.02D;
            }

            if (this.collidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579D - this.posY + d0, this.motionZ))
            {
                this.motionY = 0.30000001192092896D;
            }
        }


        this.prevLimbSwingAmount = this.limbSwingAmount;
        double d5 = this.posX - this.prevPosX;
        double d7 = this.posZ - this.prevPosZ;
        float f10 = MathHelper.sqrt(d5 * d5 + d7 * d7) * 4.0F;

        if (f10 > 1.0F)
        {
            f10 = 1.0F;
        }

        this.limbSwingAmount += (f10 - this.limbSwingAmount) * 0.4F;
        this.limbSwing += this.limbSwingAmount;
    }

    public void checkIfShouldDisturbPath(){
        if(this.collidedHorizontally){
            //motionPathHelper.disturbPath();
        }
    }

    public EntityFrog(World worldIn) {
        super(worldIn);
        stateManager = new FrogStateManager(this);
        motionPathHelper = new MotionPathHelper(this);
        moveHelper = new FrogMoveHelper(this);
        setSize(.3f,.3f);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(.2D);
    }



    public void hopToPos(Vec3d pos){
        if(!motionPathHelper.noPath()) return;
        if(!onGround) return;
        if(!collidedVertically) return;
        if(isAirBorne) return;
        this.motionPathHelper.pathToPos(pos);
        this.setJumping(true);

    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> key) {
        super.notifyDataManagerChange(key);
        if(key.equals(STATE)){
            stateManager.notifyStateChange(FrogState.fromByteID(dataManager.get(STATE)));
        }
    }

    @Override
    protected void collideWithEntity(Entity entityIn) {
        motionPathHelper.disturbPath();
        super.collideWithEntity(entityIn);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        motionPathHelper.disturbPath();
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setByte("Variant",getStateManager().getFrogState().getByteID());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        getStateManager().setFrogState(FrogState.fromByteID(compound.getByte("Variant")));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataManager.register(VARIANT,(byte)0);
        dataManager.register(STATE,(byte)0);
    }
    public FrogStateManager getStateManager(){
        return this.stateManager;
    }

    public FrogState getState(){
        return FrogState.fromByteID(dataManager.get(STATE));
    }

    public void setState(FrogState state){
        dataManager.set(STATE,state.getByteID());
    }

    public int getVariant(){
        return dataManager.get(VARIANT).intValue();
    }
    public void setVariant(int variant){
        dataManager.set(VARIANT,(byte)variant);
    }
    public boolean getIsJumping(){
        return isJumping;
    }
    public float getSwimSpeed(){
        return (float)getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        setVariant(rand.nextInt(VARIANT_COUNT));

        return super.onInitialSpawn(difficulty, livingdata);

    }

    @Override
    protected boolean pushOutOfBlocks(double x, double y, double z) {
        motionPathHelper.disturbPath();
        return super.pushOutOfBlocks(x, y, z);
    }

    @Override
    public MotionPathHelper getMotionPathHelper() {
        return motionPathHelper;
    }

    @Override
    public EntityCreature getEntity() {
        return this;
    }

    public enum FrogState{
        STANDING(0),
        JUMPING(1),
        SWIMMING(2);

        byte id;

        FrogState(byte id){
            this.id = id;
        }
        FrogState(int id){
            this((byte)id);
        }
        byte getByteID(){
            return this.id;
        }
        static FrogState fromByteID(byte id){
            for(FrogState state : FrogState.values()){
                if(id==state.id) return state;
            }
            return STANDING;
        }

    }

    public class FrogMoveHelper extends EntityMoveHelper{
        private EntityFrog frog;

        public FrogMoveHelper(EntityFrog entitylivingIn) {
            super(entitylivingIn);
            frog = entitylivingIn;
        }

        public Vec3d getVectorPosition(){
            return new Vec3d(getX(),getY(),getZ());
        }

        @Override
        public void onUpdateMoveHelper() {
            if (this.action == EntityMoveHelper.Action.STRAFE)
            {
                // Does any entity in this game even use strafing?
            }
            else if (this.action == EntityMoveHelper.Action.MOVE_TO)
            {
                this.action = EntityMoveHelper.Action.WAIT;
                double d0 = this.posX - this.entity.posX;
                double d1 = this.posZ - this.entity.posZ;
                double d2 = this.posY - this.entity.posY;
                double d3 = d0 * d0 + d2 * d2;

                if (d3 < .1)
                {

                    this.frog.getMotionPathHelper().reset();
                    return;
                }

                float f9 = (float)(MathHelper.atan2(d1, d0) * (180D / Math.PI)) - 90.0F;
                this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, f9, 90.0F);
                //this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));
                Vec3d pos = d3<1?getVectorPosition():cutPath(4);
                frog.hopToPos(pos);
                if (d2 > (double)this.entity.stepHeight && d0 * d0 + d1 * d1 < (double)Math.max(1.0F, this.entity.width))
                {
                    //this.entity.getJumpHelper().setJumping();
                    //this.action = EntityMoveHelper.Action.JUMPING;
                }
            }
            else if (this.action == EntityMoveHelper.Action.JUMPING)
            {
                //this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));

                if (this.entity.onGround)
                {
                    this.action = EntityMoveHelper.Action.WAIT;
                }
            }
            else
            {
                this.entity.setMoveForward(0);
                //this.frog.getMotionPathHelper().reset();
            }
        }
        public Vec3d cutPath(double stride){
            Vec3d delta = getVectorPosition().subtract(frog.getPositionVector());
            delta = new Vec3d(delta.x,0,delta.z);
            delta = delta.normalize().scale(stride);
            return frog.getPositionVector().add(delta);
        }
    }
}
