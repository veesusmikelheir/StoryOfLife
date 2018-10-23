package net.nolifers.storyoflife.client.render.entity.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class EntityRenderUtil {
    public static Vec3d getRenderedVelocity(Entity entity){
        return new Vec3d(entity.posX-entity.prevPosX,entity.posY-entity.prevPosY,entity.posZ-entity.prevPosZ);
    }
    public static Vec3d getRenderedVelocity(Entity entity,float partialTicks){
        return getRenderedVelocity(entity).scale(1);
    }
}
