package ctn.imaginarycraft.mixinextend;

import ctn.imaginarycraft.api.lobotomycorporation.LcDamage;
import ctn.imaginarycraft.api.lobotomycorporation.LcLevel;
import net.minecraft.world.damagesource.DamageSource;

import javax.annotation.Nullable;

public interface IDamageSource {
  static IDamageSource of(DamageSource source) {
    return (IDamageSource) source;
  }

  @Nullable
  LcDamage getLcDamage();

  void setLcDamage(LcDamage type);

  @Nullable
  LcLevel getLcDamageLevel();

  void setDamageLevel(LcLevel pmLevel);

  int getInvincibleTick();

  void setInvincibleTick(int tick);
}
