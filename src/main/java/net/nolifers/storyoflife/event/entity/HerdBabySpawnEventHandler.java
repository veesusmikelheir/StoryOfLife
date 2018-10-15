package net.nolifers.storyoflife.event.entity;

import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.nolifers.storyoflife.StoryofLife;
import net.nolifers.storyoflife.entity.EntityWildebeest;

@Mod.EventBusSubscriber(modid= StoryofLife.MOD_ID)
public class HerdBabySpawnEventHandler {
    @SubscribeEvent
    public static void makeBaby(LivingSpawnEvent.CheckSpawn event){
        if(event.getEntityLiving() instanceof EntityWildebeest){
            EntityWildebeest beast = (EntityWildebeest)event.getEntityLiving();
            if(beast.world.rand.nextInt(2)==0){
                beast.setGrowingAge(-24000);
            }
        }
    }
}
