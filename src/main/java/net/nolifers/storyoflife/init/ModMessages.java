package net.nolifers.storyoflife.init;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.nolifers.storyoflife.network.motionpath.DisturbPathMessage;
import net.nolifers.storyoflife.network.motionpath.StartPathMessage;

public class ModMessages {

    public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel("test");
    static int ID = 0;
    public static void registerMessages(){
        SimpleNetworkWrapper net = NETWORK;
        net.registerMessage(StartPathMessage.StartPathHandler.class,StartPathMessage.class,0, Side.CLIENT);
        net.registerMessage(DisturbPathMessage.DisturbPathHandler.class,DisturbPathMessage.class,1, Side.CLIENT);
    }
}
