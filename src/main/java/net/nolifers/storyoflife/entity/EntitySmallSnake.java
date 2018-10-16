package net.nolifers.storyoflife.entity;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializer;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntitySmallSnake extends EntityCreature {

    public static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(EntitySmallSnake.class, DataSerializers.VARINT);
    public static final DataParameter<Boolean> HEADUP = EntityDataManager.createKey(EntitySmallSnake.class, DataSerializers.BOOLEAN);
    private final int variantCount = 4;
    public int tongueTime;
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(.2D);
    }

    public EntitySmallSnake(World worldIn) {
        super(worldIn);
        setSize(.5f,.08f);
        moveHelper=new EntitySmallSnake.MoveHelper(this);
    }



    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1,new EntityAIWatchClosest(this,EntityPlayer.class,6f));
        this.tasks.addTask(0, new EntityAIWanderAvoidWater(this, 1));
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> key) {
        super.notifyDataManagerChange(key);

        if(key.equals(HEADUP)){
            if(dataManager.get(HEADUP).booleanValue()) {
                setSize(.5f, .15f);
            }
            else{
                setSize(.5f,.08f);
            }
        }


    }



    public boolean getHeadUp(){
        return dataManager.get(HEADUP).booleanValue();
    }

    public void setHeadUp(boolean value){
        dataManager.set(HEADUP,Boolean.valueOf(value));
    }

    public boolean shouldTongueFlick(){
        return tongueTime>0;
    }
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(tongueTime>0){
            tongueTime--;
        }

        if(world.isRemote){
            if(!shouldTongueFlick()&&rand.nextInt(45)==0){
                world.setEntityState(this,(byte)155);
                tongueTime=20;
            }
        }
    }




    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        this.setVariant(rand.nextInt(variantCount));
        return super.onInitialSpawn(difficulty, livingdata);
    }

    @Override
    public void handleStatusUpdate(byte id) {
        if(id!=155) {
            super.handleStatusUpdate(id);
        }
        else{
            tongueTime=20;
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("Variant",getVariant());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        setVariant(compound.getInteger("Variant"));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataManager.register(VARIANT,Integer.valueOf(0));
        dataManager.register(HEADUP,Boolean.TRUE);
    }

    public void setVariant(int variant){
        if((variant+1)>variantCount) throw new IndexOutOfBoundsException();
        dataManager.set(VARIANT,Integer.valueOf(variant));
    }
    public int getVariant(){
        return dataManager.get(VARIANT).intValue();
    }

    public class MoveHelper extends EntityMoveHelper {
        EntitySmallSnake smallSnake;
        public MoveHelper(EntitySmallSnake entitySmallSnake) {
            super(entitySmallSnake);
            this.smallSnake=entitySmallSnake;

        }

        @Override
        public void onUpdateMoveHelper() {
            if(this.action==Action.WAIT){
                smallSnake.setHeadUp(true);
            }
            else if(this.action==Action.MOVE_TO||action==Action.STRAFE){
                smallSnake.setHeadUp(false);
            }
            super.onUpdateMoveHelper();

        }
    }
}
