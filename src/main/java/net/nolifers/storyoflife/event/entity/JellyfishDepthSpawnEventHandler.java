package net.nolifers.storyoflife.event.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.nolifers.storyoflife.StoryofLife;
import net.nolifers.storyoflife.entity.EntityJellyfish;


@Mod.EventBusSubscriber(modid= StoryofLife.MOD_ID)
public class JellyfishDepthSpawnEventHandler {
    @SubscribeEvent
    public static void adjustDepth(LivingSpawnEvent.CheckSpawn event){
        if(event.getEntityLiving() instanceof EntityJellyfish){
            EntityJellyfish jelly = (EntityJellyfish)event.getEntityLiving();
            BlockPos pos = getOceanFloor(jelly.world,jelly.getPosition()).up(3+jelly.world.rand.nextInt(8));
            jelly.setPosition(pos.getX(),pos.getY(),pos.getZ());
        }
    }

    public static BlockPos getOceanFloor(World worldIn, BlockPos startPos){
        BlockPos cur = startPos;
        IBlockState curState = worldIn.getBlockState(startPos);
        while(curState.getBlock()== Blocks.WATER&&cur.getY()>0){
            cur=cur.down();
            curState=worldIn.getBlockState(cur);
        }
        return cur;
    }
}
