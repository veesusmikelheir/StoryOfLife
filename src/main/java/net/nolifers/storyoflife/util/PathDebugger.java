package net.nolifers.storyoflife.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.nolifers.storyoflife.StoryofLife;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = StoryofLife.MOD_ID)
public class PathDebugger {

    static ArrayList<Path> pathsToDebug = new ArrayList<>();


    public static void addPath(Path path){
        if(pathsToDebug.contains(path)){
            return;
        }
        pathsToDebug.add(path);
    }

    public static void removePath(Path path){
        pathsToDebug.remove(path);
    }
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void renderEvent(RenderWorldLastEvent event){
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        double x = player.lastTickPosX + (player.posX-player.lastTickPosX)*event.getPartialTicks();
        double y = player.lastTickPosY + (player.posY-player.lastTickPosY)*event.getPartialTicks();
        double z = player.lastTickPosZ + (player.posZ-player.lastTickPosZ)*event.getPartialTicks();

        GlStateManager.pushMatrix();
        GlStateManager.translate(-x,-y,-z);
        GlStateManager.glLineWidth(2.0F);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        for(int i = 0;i<pathsToDebug.size();i++){
            Path path = pathsToDebug.get(i);
            if(path==null||path.isFinished()){
                pathsToDebug.remove(path);
                return;
            }


        }
        for(int i = 0;i<pathsToDebug.size();i++){
            Path path = pathsToDebug.get(i);



            for(int j = path.getCurrentPathIndex();j<path.getCurrentPathLength();j++){
                PathPoint point = path.getPathPointFromIndex(j);
                float r = 1;
                float g = 1;
                float b = 0;

                RenderGlobal.drawBoundingBox(point.x,point.y,point.z,point.x+1,point.y+1,point.z+1,r,g,b,1);
            }
        }

        GlStateManager.glLineWidth(1f);
        GlStateManager.enableTexture2D();
        GlStateManager.depthMask(true);
        GlStateManager.popMatrix();
    }

}
