package net.nolifers.storyoflife.entity;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.nolifers.storyoflife.entity.util.FrogStateManager;

import javax.annotation.Nullable;

public class EntityFrog extends EntityCreature {

    public static final DataParameter<Byte> VARIANT = EntityDataManager.createKey(EntityFrog.class, DataSerializers.BYTE);
    public static final DataParameter<Byte> STATE = EntityDataManager.createKey(EntityFrog.class,DataSerializers.BYTE);
    public static final int VARIANT_COUNT=4;

    FrogStateManager stateManager;

    @Override
    public void onUpdate() {
        super.onUpdate();
        stateManager.onUpdate();
    }

    public EntityFrog(World worldIn) {
        super(worldIn);
        stateManager = new FrogStateManager(this);
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
