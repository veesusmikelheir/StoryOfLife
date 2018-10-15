package net.nolifers.storyoflife.init;

import net.minecraftforge.common.util.ModFixs;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.nolifers.storyoflife.StoryofLife;

public class ModDataFixers {

    public static final int VERSION = 0;
    public static ModFixs DATAFIXER = FMLCommonHandler.instance().getDataFixer().init(StoryofLife.MOD_ID,VERSION);

    public static void registerFixes(){
    }
}
