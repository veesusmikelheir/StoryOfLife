package net.nolifers.storyoflife.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
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
    public void onUpdate() {
        super.onUpdate();
        motionPathHelper.onUpdate();
        stateManager.onUpdate();

        checkIfShouldDisturbPath();
        if(motionPathHelper.noPath()&&number<20){
            motionPathHelper.pathToPos(getPositionVector().add(new Vec3d(2   ,0,0)));
            number++;
        }

    }

    public void checkIfShouldDisturbPath(){
        if(this.collidedHorizontally){
            motionPathHelper.disturbPath();
        }
    }

    public EntityFrog(World worldIn) {
        super(worldIn);
        stateManager = new FrogStateManager(this);
        motionPathHelper = new MotionPathHelper(this);
        setSize(.3f,.3f);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(.2D);
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
}
