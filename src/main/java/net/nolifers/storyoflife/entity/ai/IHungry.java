package net.nolifers.storyoflife.entity.ai;

import net.minecraft.entity.EntityCreature;
//TODO: make more robust hunger system
public interface IHungry {
    EntityCreature getEntity();
    boolean getIsHungry();
    void eat(EntityCreature food);

}
