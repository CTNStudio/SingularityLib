package ctn.imaginarycraft.capability.entity;

import ctn.imaginarycraft.api.lobotomycorporation.LcDamage;
import net.minecraft.world.entity.Entity;

public interface ILcDamageTypeEntity {
  LcDamage getDamageType(Entity entity);
}
