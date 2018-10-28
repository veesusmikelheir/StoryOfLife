package net.nolifers.storyoflife.network.motionpath;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.nolifers.storyoflife.entity.util.IMotionPathFollower;
import net.nolifers.storyoflife.util.math.ISyncedPath;
import net.nolifers.storyoflife.util.math.MotionPathSolver;

public class StartPathMessage implements IMessage {
    ISyncedPath path;
    int target;

    public StartPathMessage(ISyncedPath path, IMotionPathFollower target){
        this.path=path;
        this.target=target.getEntity().getEntityId();
    }
    public StartPathMessage(ISyncedPath path, int target){
        this.path=path;
        this.target=target;
    }

    public StartPathMessage(){}
    @Override
    public void fromBytes(ByteBuf buf) {
        this.target = buf.readInt();
        this.path = (ISyncedPath)MotionPathSolver.pathFromBytes(buf);

    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(target);
        MotionPathSolver.pathToBytes(path,buf);
    }

    public static class StartPathHandler implements IMessageHandler<StartPathMessage,IMessage> {


        @Override
        public IMessage onMessage(StartPathMessage message, MessageContext ctx) {
            if(Minecraft.getMinecraft().world.getEntityByID(message.target)==null) return null;
            if(message.path==null) System.out.println("AAAAAAAAAAAAAAAAAAA");
            System.out.println(message.path.getPath().getTargetPos());
            Minecraft.getMinecraft().addScheduledTask(()->{
                //((IMotionPathFollower)Minecraft.getMinecraft().world.getEntityByID(message.target)).getMotionPathHelper().setPath(message.path.getPath());
            });
            return null;
        }
    }
}
