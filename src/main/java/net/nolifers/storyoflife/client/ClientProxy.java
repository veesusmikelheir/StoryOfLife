package net.nolifers.storyoflife.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.nolifers.storyoflife.IProxy;
import net.nolifers.storyoflife.client.render.ModRenders;
import net.nolifers.storyoflife.StoryofLife;

@Mod.EventBusSubscriber(modid=StoryofLife.MOD_ID)
public class ClientProxy implements IProxy {
    @Override
    public void preinit(FMLPreInitializationEvent event) {
        ModRenders.registerRenders();
    }

    @Override
    public void init(FMLInitializationEvent event) {
    }

    @Override
    public void postinit(FMLPostInitializationEvent event) {

    }

    @SubscribeEvent
    public static void registerItemModels(ModelRegistryEvent event){

    }
    static Item getItemBlockForBlock(Block b){

        return ForgeRegistries.ITEMS.getValue(b.getRegistryName());
    }
    static void RegisterDefaultVariants(Item item){
        ModelLoader.setCustomModelResourceLocation(item,0,new ModelResourceLocation(item.getRegistryName(),"inventory"));
    }
}
