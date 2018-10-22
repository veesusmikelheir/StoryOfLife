package net.nolifers.storyoflife.entity;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityFrog extends EntityCreature {

    public static final DataParameter<Byte> VARIANT = EntityDataManager.createKey(EntityFrog.class, DataSerializers.BYTE);
    public static final int VARIANT_COUNT=4;
    public EntityFrog(World worldIn) {
        super(worldIn);
        setSize(.3f,.3f);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataManager.register(VARIANT,(byte)0);
    }

    public int getVariant(){
        return dataManager.get(VARIANT).intValue();
    }
    public void setVariant(int variant){
        dataManager.set(VARIANT,(byte)variant);
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        setVariant(rand.nextInt(VARIANT_COUNT));
        return super.onInitialSpawn(difficulty, livingdata);

    }
}
