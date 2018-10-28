package net.nolifers.storyoflife.network.motionpath;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.nolifers.storyoflife.entity.util.IMotionPathFollower;

public class DisturbPathMessage implements IMessage {
    public int target;

    public DisturbPathMessage(IMotionPathFollower target){
        this.target=target.getEntity().getEntityId();
    }

    public DisturbPathMessage(int target){
        this.target=target;
    }
    public DisturbPathMessage(){}
    @Override
    public void fromBytes(ByteBuf buf) {
        this.target=buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(target);
    }
    public static class DisturbPathHandler implements IMessageHandler<DisturbPathMessage,IMessage> {

        @Override
        public IMessage onMessage(DisturbPathMessage message, MessageContext ctx) {
            if(Minecraft.getMinecraft().world.getEntityByID(message.target)==null) return null;

            Minecraft.getMinecraft().addScheduledTask(()->{
                //((IMotionPathFollower)Minecraft.getMinecraft().world.getEntityByID(message.target)).getMotionPathHelper().disturbPath();
            });
            return null;
        }
    }
}
