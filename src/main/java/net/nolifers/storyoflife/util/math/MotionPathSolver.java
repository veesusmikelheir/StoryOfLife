package net.nolifers.storyoflife.util.math;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MotionPathSolver {

    //TODO: Finish this

    public static final Map<Byte,ISyncedPath> networkedPaths = new HashMap<>();

    static{
        networkedPaths.put((byte)0,new ParabolicMotionPath());
    }


    Entity entity;
    public MotionPathSolver(Entity entity){
        this.entity=entity;
    }

    public MotionPath getMotionPathToPoint(Vec3d point){
        return new ParabolicMotionPath(entity.getPositionVector(),point,0,-0.08,true);
    }

    public double getLateralDistance(Vec3d start, Vec3d end){
        Vec3d end2 = new Vec3d(end.x,start.y,end.z);
        return start.distanceTo(end2);
    }

    public static void pathToBytes(ISyncedPath path, ByteBuf buf){
        path.toBytes(buf,0);
        for(int i = 0; i<networkedPaths.size();i++){
            ISyncedPath networkedPath = networkedPaths.get((byte)i);
            if(networkedPath.getClass().equals(path.getClass())){
                //buf.writeByte((byte)i);


            }
        }
    }
    public static MotionPath pathFromBytes(ByteBuf buf){
        //byte index = buf.readByte();
        return networkedPaths.get((byte)0).fromBytes(buf,0);
    }
}
