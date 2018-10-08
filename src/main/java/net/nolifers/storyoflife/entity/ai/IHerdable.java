package net.nolifers.storyoflife.entity.ai;

import net.minecraft.entity.passive.EntityAnimal;

public interface IHerdable {
    EntityAnimal getEntity();
    void setHerdingOverride(IHerdable target);
}
