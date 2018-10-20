package net.nolifers.storyoflife.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.nolifers.storyoflife.entity.util.ICreatureProvider;

public interface IFlee extends ICreatureProvider {
    FleeHelper getFleeHelper();
}
