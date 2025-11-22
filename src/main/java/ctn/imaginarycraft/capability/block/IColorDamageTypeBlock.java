package ctn.imaginarycraft.capability.block;

import ctn.imaginarycraft.api.lobotomycorporation.LcDamage;
import net.minecraft.world.level.block.state.BlockState;

public interface IColorDamageTypeBlock {
  LcDamage getDamageType(BlockState state);
}
