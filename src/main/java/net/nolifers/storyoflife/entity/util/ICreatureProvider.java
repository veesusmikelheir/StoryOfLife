package net.nolifers.storyoflife.entity.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;

public interface ICreatureProvider extends IEntityProvider {
    EntityCreature getEntity();
}
