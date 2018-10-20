package net.nolifers.storyoflife.event.entity;


import net.minecraft.block.BlockFence;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.border.WorldBorder;
import net.minecraftforge.event.world.GetCollisionBoxesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.nolifers.storyoflife.StoryofLife;
import net.nolifers.storyoflife.entity.EntitySmallSnake;



@Mod.EventBusSubscriber(modid=StoryofLife.MOD_ID)
public class SnakeFenceCollisionEventHandler {
    @SubscribeEvent
    public static void collisionEvent(GetCollisionBoxesEvent event){
        if(!(event.getEntity() instanceof EntitySmallSnake)) return;
        event.getCollisionBoxesList().clear();
        AxisAlignedBB aabb=event.getAabb();
        Entity entityIn = event.getEntity();
        int i = MathHelper.floor(aabb.minX) - 1;
        int j = MathHelper.ceil(aabb.maxX) + 1;
        int k = MathHelper.floor(aabb.minY) - 1;
        int l = MathHelper.ceil(aabb.maxY) + 1;
        int i1 = MathHelper.floor(aabb.minZ) - 1;
        int j1 = MathHelper.ceil(aabb.maxZ) + 1;
        WorldBorder worldborder = event.getWorld().getWorldBorder();
        boolean flag = entityIn != null && entityIn.isOutsideBorder();
        boolean flag1 = entityIn != null && event.getWorld().isInsideWorldBorder(entityIn);
        IBlockState iblockstate = Blocks.STONE.getDefaultState();
        BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();


        try
        {
            for (int k1 = i; k1 < j; ++k1)
            {
                for (int l1 = i1; l1 < j1; ++l1)
                {
                    boolean flag2 = k1 == i || k1 == j - 1;
                    boolean flag3 = l1 == i1 || l1 == j1 - 1;

                    if ((!flag2 || !flag3) && event.getWorld().isBlockLoaded(blockpos$pooledmutableblockpos.setPos(k1, 64, l1)))
                    {
                        for (int i2 = k; i2 < l; ++i2)
                        {
                            if (!flag2 && !flag3 || i2 != l - 1)
                            {

                                    if (k1 < -30000000 || k1 >= 30000000 || l1 < -30000000 || l1 >= 30000000)
                                    {
                                        boolean lvt_21_2_ = true;
                                        return;
                                    }

                                else if (event.getEntity() != null && flag == flag1)
                                {
                                    event.getEntity().setOutsideBorder(!flag1);
                                }

                                blockpos$pooledmutableblockpos.setPos(k1, i2, l1);
                                IBlockState iblockstate1;

                                if (!worldborder.contains(blockpos$pooledmutableblockpos) && flag1)
                                {
                                    iblockstate1 = iblockstate;
                                }
                                else
                                {
                                    iblockstate1 = event.getWorld().getBlockState(blockpos$pooledmutableblockpos);
                                }
                                if(i2!=k&&iblockstate1.getBlock() instanceof BlockFence){
                                    AxisAlignedBB axisalignedbb =BlockFence.PILLAR_AABB.offset(blockpos$pooledmutableblockpos);

                                    if (event.getAabb().intersects(axisalignedbb))
                                    {
                                        event.getCollisionBoxesList().add(axisalignedbb);
                                    }

                                }
                                else {
                                    iblockstate1.addCollisionBoxToList(event.getEntity().world, blockpos$pooledmutableblockpos, event.getAabb(), event.getCollisionBoxesList(), event.getEntity(), false);
                                }

                            }
                        }
                    }
                }
            }
        }
        finally
        {
            blockpos$pooledmutableblockpos.release();
        }

    }

}
