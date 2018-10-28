package net.nolifers.storyoflife.util.math;

import io.netty.buffer.ByteBuf;

public interface ISyncedPath {
    int toBytes(ByteBuf buffer,int index);
    MotionPath fromBytes(ByteBuf buffer,int index);
    MotionPath getPath();
}
