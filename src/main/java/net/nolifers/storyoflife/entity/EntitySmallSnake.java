package net.nolifers.storyoflife.entity;

import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.nolifers.storyoflife.entity.ai.*;
import net.nolifers.storyoflife.util.PathDebugger;

import javax.annotation.Nullable;

public class EntitySmallSnake extends EntityCreature implements IFlee, IHungry {

    public static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(EntitySmallSnake.class, DataSerializers.VARINT);
    public static final DataParameter<Byte> STATE = EntityDataManager.createKey(EntitySmallSnake.class, DataSerializers.BYTE);

    public static final float globalScale = .75f;
    public FleeHelper fleeHelper;

    int hungerTicks;
    private final int variantCount = 4;
    public int tongueTime;
    public boolean isClimbing;

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(.2D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2D);
    }

    public EntitySmallSnake(World worldIn) {
        super(worldIn);
        setSize(.85f*globalScale,1);
        moveHelper=new EntitySmallSnake.MoveHelper(this);
        jumpHelper=new EntitySmallSnake.JumpHelper(this);
        this.fleeHelper=new FleeHelper(this);
        this.setPathPriority(PathNodeType.FENCE,0f);
        this.setPathPriority(PathNodeType.WALKABLE,.1f);
    }

    @Override
    public void setDead() {
        super.setDead();
    }

    @Override
    protected PathNavigate createNavigator(World worldIn) {
        return new PathNavigateFence(this,worldIn);
    }
    @Override
    protected void initEntityAI() {
        int AI=0;
        this.tasks.addTask(AI++,new EntityAISwimming(this));
        this.tasks.addTask(AI++,new EntityAIAvoidPlayers(this,6,1,1.2));
        this.tasks.addTask(AI++,new EntityAISneakOnTarget(this,.8,x->x instanceof EntityChicken||x instanceof EntityRabbit||(x instanceof EntityAnimal&&((EntityAnimal) x).getGrowingAge()<0)));
        this.tasks.addTask(AI++,new EntityAIFleeFromTarget(this,8,1,1.2));

        this.tasks.addTask(AI++,new EntityAIAttackMelee(this,1.1,false));

        this.tasks.addTask(AI++, new EntityAIWanderAvoidWater(this, 1));



        this.tasks.addTask(AI++,new EntityAIWatchClosest(this,EntityPlayer.class,6f));
        this.tasks.addTask(AI++, new EntityAILookIdle(this));
        this.targetTasks.addTask(0,new EntityAIHurtByTarget(this,false));

        this.targetTasks.addTask(1,new EntityAITargetWhenHungry(this,EntityChicken.class,false,true));
        this.targetTasks.addTask(2,new EntityAITargetWhenHungry(this, EntityRabbit.class,false,true));
        this.targetTasks.addTask(3,new EntityAITargetWhenHungry<EntityAnimal>(this, EntityAnimal.class,10, true,false,x->x.getGrowingAge()<0));
    }

    @Override
    public boolean isOnLadder() {
        // TODO: make proper is on ladder handling
        return this.collidedHorizontally&&(isClimbing);//&&this.world.getBlockState(this.getPosition()).getBlock().getClass()== Blocks.ACACIA_FENCE.getClass()));

    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        float f = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();


        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), f);

        if (flag)
        {

            if (entityIn instanceof EntityLivingBase)
            {
                if(!world.isRemote) ((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(MobEffects.POISON,20*10,0));
                if(entityIn.height>.75f) {

                    fleeHelper.setFlee(true);
                    fleeHelper.setFleeTarget((EntityLivingBase)entityIn);
                }
            }
            this.setHeadUp(true);

            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }

    @Override
    protected boolean canDespawn()
    {
        return false;
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> key) {
        super.notifyDataManagerChange(key);

        if(key.equals(STATE)){
            State state = State.fromByte(dataManager.get(STATE));
            if(state==State.HEADUP||state==State.FULL) {
                setSize(.66f*globalScale, .3f*globalScale);

            }
            else{
                setSize(.66f*globalScale,.16f*globalScale);
            }
        }


    }



    public State getState(){
        return State.fromByte(dataManager.get(STATE));
    }

    public void setState(State value){
        dataManager.set(STATE,value.getByte());
    }

    public boolean shouldTongueFlick(){
        return tongueTime>0;
    }
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(tongueTime>0) tongueTime--;
        if(hungerTicks>0) hungerTicks--;

        if(!world.isRemote){
            if(hungerTicks<=0&&getState()==State.FULL){
                setState(State.HEADUP);
            }
            if(hungerTicks>0&&getState()!=State.FULL){
                setState(State.FULL);
            }
            if(this.onGround){
                isClimbing=false;
            }

        }
        else{
            if(!shouldTongueFlick()&&rand.nextInt(45)==0){
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
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("Variant",getVariant());
        compound.setInteger("Hunger",hungerTicks);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        setVariant(compound.getInteger("Variant"));
        hungerTicks=compound.getInteger("Hunger");
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataManager.register(VARIANT,Integer.valueOf(0));
        dataManager.register(STATE,Byte.valueOf((byte)1));
    }

    public void setHeadUp(boolean up){
        if(getState()==State.FULL) return;
        setState(up?State.HEADUP:State.HEADDOWN);
    }

    public boolean getHeadUp(){
        return getState()==State.HEADUP;
    }
    public void setVariant(int variant){
        if((variant+1)>variantCount) throw new IndexOutOfBoundsException();
        dataManager.set(VARIANT,Integer.valueOf(variant));
    }
    public int getVariant(){
        return dataManager.get(VARIANT).intValue();
    }

    @Override
    public void onKillEntity(EntityLivingBase entityLivingIn) {
        super.onKillEntity(entityLivingIn);
        if(!(entityLivingIn instanceof EntityCreature)) return;
        eat((EntityCreature)entityLivingIn);
    }



    @Override
    public boolean handleWaterMovement()
    {
        if (this.getRidingEntity() instanceof EntityBoat)
        {
            this.inWater = false;
        }
        else if (this.world.handleMaterialAcceleration(this.getEntityBoundingBox().grow(0.0D, -0.0100000059604645D, 0.0D).shrink(0.00001D), Material.WATER, this))
        {
            if (!this.inWater && !this.firstUpdate)
            {
                this.doWaterSplashEffect();
            }

            this.fallDistance = 0.0F;
            this.inWater = true;
            this.extinguish();
        }
        else
        {
            this.inWater = false;
        }

        return this.inWater;
    }

    @Override
    public EntityCreature getEntity() {
        return this;
    }

    @Override
    public boolean getIsHungry() {
        return hungerTicks<=0;
    }

    @Override
    public void eat(EntityCreature food) {
        if(food instanceof EntityChicken||food instanceof EntityRabbit||(food instanceof EntityAgeable&&((EntityAgeable)food).getGrowingAge()<0)){
            hungerTicks+=5000;
            fleeHelper.setFlee(true);
            fleeHelper.getFleeTarget().setVectorTarget(food.getPositionVector());
        }
    }

    @Override
    public FleeHelper getFleeHelper() {
        return this.fleeHelper;
    }


    public static enum State{
        HEADDOWN((byte)0),
        HEADUP((byte)1),
        FULL((byte)2);


        byte byteVal;
        State(byte byteVal){
            this.byteVal = byteVal;
        }

        public byte getByte(){
            return this.byteVal;
        }

        public static State fromByte(byte val){
            for(State state : State.values()){
                if(state.getByte()==val) return state;
            }
            throw new IndexOutOfBoundsException();
        }
    }

    public static class MoveHelper extends EntityMoveHelper {
        EntitySmallSnake smallSnake;
        double inputtedSpeed;
        @Override
        public void setMoveTo(double x, double y, double z, double speedIn) {
            super.setMoveTo(x, y, z, speedIn);
            this.inputtedSpeed=speedIn;
        }

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
            if(this.smallSnake.getState()==State.FULL){
                this.speed = inputtedSpeed*.75;
            }
            else{
                this.speed=inputtedSpeed;
            }
            super.onUpdateMoveHelper();

        }
    }

    public static class PathNavigateFence extends PathNavigateGround{
        @Override
        protected void pathFollow() {

            Vec3d vec3d = this.getEntityPosition();
            int i = this.currentPath.getCurrentPathLength();

            for (int j = this.currentPath.getCurrentPathIndex(); j < this.currentPath.getCurrentPathLength(); ++j) {
                if ((double) this.currentPath.getPathPointFromIndex(j).y != Math.floor(vec3d.y)) {
                    i = j;
                    break;
                }
            }

            this.maxDistanceToWaypoint = this.entity.width > 0.75F ? this.entity.width / 2.0F : 0.75f - entity.width / 2;
            Vec3d vec3d1 = this.currentPath.getCurrentPos();
            vec3d1 = vec3d1.add(0.5, 0, .5);
            if (world.getBlockState(new BlockPos(vec3d1)).getBlock() instanceof BlockFence) {

                vec3d1 = solveFence(vec3d1);
                maxDistanceToWaypoint=.05f;
            }
            if (MathHelper.abs((float) (this.entity.posX - (vec3d1.x))) < this.maxDistanceToWaypoint && MathHelper.abs((float) (this.entity.posZ - (vec3d1.z ))) < this.maxDistanceToWaypoint && Math.abs(this.entity.posY - vec3d1.y) < 1.0D) {
                this.currentPath.setCurrentPathIndex(this.currentPath.getCurrentPathIndex() + 1);
            }


            this.checkForStuck(vec3d);
        }

        @Override
        protected void debugPathFinding() {
            if(this.noPath()) return;
            //PathDebugger.addPath(getPath());
        }

        @Override
        public void onUpdateNavigation() {

            ++this.totalTicks;

            if (this.tryUpdatePath) {
                this.updatePath();
            }

            if (!this.noPath()) {
                if (this.canNavigate()) {
                    this.pathFollow();
                } else if (this.currentPath != null && this.currentPath.getCurrentPathIndex() < this.currentPath.getCurrentPathLength()) {
                    Vec3d vec3d = this.getEntityPosition();
                    Vec3d vec3d1 = this.currentPath.getVectorFromIndex(this.entity, this.currentPath.getCurrentPathIndex());
                    vec3d1 = solveFence(vec3d1);
                    if (vec3d.y > vec3d1.y && !this.entity.onGround && MathHelper.floor(vec3d.x) == MathHelper.floor(vec3d1.x) && MathHelper.floor(vec3d.z) == MathHelper.floor(vec3d1.z)) {
                        this.currentPath.setCurrentPathIndex(this.currentPath.getCurrentPathIndex() + 1);
                    }
                }

                this.debugPathFinding();

                if (!this.noPath()) {
                    Vec3d vec3d2 = this.currentPath.getPosition(this.entity);

                    BlockPos blockpos = (new BlockPos(vec3d2)).down();
                    vec3d2 = solveFence(vec3d2);
                    AxisAlignedBB axisalignedbb = this.world.getBlockState(blockpos).getBoundingBox(this.world, blockpos);
                    vec3d2 = vec3d2.subtract(0.0D, 1.0D - axisalignedbb.maxY, 0.0D);
                    this.entity.getMoveHelper().setMoveTo(vec3d2.x, vec3d2.y, vec3d2.z, this.speed);
                }
            }
        }

        // TODO; find and fix edge cases
        // TODO: (maybe) defer to custom pathfinder to avoid getting stuck on adjecent fences
        public Vec3d solveFence(Vec3d start) {
            IBlockState state = world.getBlockState(new BlockPos(start));
            if (!(state.getBlock() instanceof BlockFence)) return start;
            Vec3d aaaaaa = currentPath.getVectorFromIndex(entity, currentPath.getCurrentPathIndex() + this.currentPath.getCurrentPathIndex() < this.currentPath.getCurrentPathLength() ? 1 : 0);
            Vec3d newVec = (aaaaaa.subtract(start)).normalize();

            EnumFacing facing = EnumFacing.getFacingFromVector((float) newVec.x, 0, (float) newVec.z).rotateAround(EnumFacing.Axis.Y);

            EnumFacing facing2 = facing.getOpposite();
            Vec3d one = start.add(new Vec3d(facing.getDirectionVec()).scale(.5));
            Vec3d two = start.add(new Vec3d(facing2.getDirectionVec()).scale(.5));
            if (entity.getDistanceSq(one.x, one.y, one.z) < entity.getDistanceSq(two.x, two.y, two.z)) two = one;
            return two;

        }

        public PathNavigateFence(EntityLiving entitylivingIn, World worldIn) {
            super(entitylivingIn, worldIn);
        }
    }

    public static class JumpHelper extends EntityJumpHelper{


        EntitySmallSnake snake;
        @Override
        public void setJumping() {
            super.setJumping();

        }

        public JumpHelper(EntitySmallSnake entityIn) {
            super(entityIn);
            snake=entityIn;
        }

        @Override
        public void doJump() {
            if(snake.isJumping) snake.setJumping(false);
            if(this.isJumping) {
                if(snake.isInWater()){
                    snake.setJumping(true);
                    snake.isClimbing = false;
                }
                else{
                    snake.isClimbing=true;
                }
                isJumping = false;

            }
        }
    }
}
