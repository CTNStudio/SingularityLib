package ctn.imaginarycraft.mixin;

import ctn.imaginarycraft.api.lobotomycorporation.LcDamage;
import ctn.imaginarycraft.api.lobotomycorporation.LcLevel;
import ctn.imaginarycraft.api.lobotomycorporation.util.LcDamageUtil;
import ctn.imaginarycraft.capability.ILcLevel;
import ctn.imaginarycraft.capability.entity.IInvincibleTickEntity;
import ctn.imaginarycraft.capability.entity.ILcDamageTypeEntity;
import ctn.imaginarycraft.capability.item.IInvincibleTickItem;
import ctn.imaginarycraft.capability.item.ILcDamageTypeItem;
import ctn.imaginarycraft.init.ModCapabilitys;
import ctn.imaginarycraft.mixinextend.IDamageSource;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

/**
 * @author 尽
 */
@Mixin(DamageSource.class)
@Implements(@Interface(iface = IDamageSource.class, prefix = "iSingularityLib$"))
public abstract class DamageSourceMixin implements IDamageSource {
  @Unique
  @Nullable
  private LcDamage imaginaryCraft$ColorType;

  @Unique
  @Nullable
  private LcLevel imaginaryCraft$damageLevel;

  @Unique
  private int imaginaryCraft$invincibleTick = -1;

  @Inject(method = "<init>(Lnet/minecraft/core/Holder;" +
    "Lnet/minecraft/world/entity/Entity;" +
    "Lnet/minecraft/world/entity/Entity;" +
    "Lnet/minecraft/world/phys/Vec3;)V", at = @At("RETURN"))
  private void imaginaryCraft$DamageSource(Holder<DamageType> type, Entity directEntity, Entity causingEntity, Vec3 damageSourcePosition, CallbackInfo ci) {
    DamageSource damageSource = (DamageSource) (Object) this;
    ItemStack itemStack = LcDamageUtil.getDamageItemStack(damageSource);
    ILcLevel level;
    if (itemStack != null) {
      ILcDamageTypeItem colorDamageTypeItem = itemStack.getCapability(ModCapabilitys.LcDamageType.LC_DAMAGE_TYPE_ITEM);
      IInvincibleTickItem invincibleTickItem = itemStack.getCapability(ModCapabilitys.InvincibleTick.INVINCIBLE_TICK_ITEM);
      level = itemStack.getCapability(ModCapabilitys.LcLevel.LC_LEVEL_ITEM);
      if (colorDamageTypeItem != null) {
        this.imaginaryCraft$ColorType = colorDamageTypeItem.getColorDamageType(itemStack);
      }
      if (invincibleTickItem != null) {
        this.imaginaryCraft$invincibleTick = invincibleTickItem.getInvincibleTick(itemStack);
      }
      if (level != null) {
        this.imaginaryCraft$damageLevel = level.getItemLevel();
      }
    }

    imaginaryCraft$getEntityAttribute(directEntity);
    imaginaryCraft$getEntityAttribute(causingEntity);

    this.imaginaryCraft$ColorType = LcDamageUtil.getColorDamageType(this.imaginaryCraft$ColorType, type);

    if (this.imaginaryCraft$damageLevel == null) {
      this.imaginaryCraft$damageLevel = LcLevel.ZAYIN;
    }

    if (this.imaginaryCraft$invincibleTick == -1) {
      this.imaginaryCraft$invincibleTick = 20;
    }
  }

  @Unique
  private void imaginaryCraft$getEntityAttribute(Entity entity) {
    ILcDamageTypeEntity colorDamageTypeEntity;
    IInvincibleTickEntity invincibleTickEntity;
    ILcLevel level;
    if (entity == null) {
      return;
    }

    colorDamageTypeEntity = entity.getCapability(ModCapabilitys.LcDamageType.LC_DAMAGE_TYPE_ENTITY);
    invincibleTickEntity = entity.getCapability(ModCapabilitys.InvincibleTick.INVINCIBLE_TICK_ENTITY);

    if (imaginaryCraft$ColorType == null) {
      if (colorDamageTypeEntity != null) {
        imaginaryCraft$ColorType = colorDamageTypeEntity.getDamageType(entity);
      }
    }

    if (imaginaryCraft$invincibleTick != -1 && invincibleTickEntity != null) {
      imaginaryCraft$invincibleTick = invincibleTickEntity.getInvincibleTick(entity);
    }

    level = entity.getCapability(ModCapabilitys.LcLevel.LC_LEVEL_ENTITY);
    if (level != null) {
      imaginaryCraft$damageLevel = level.getItemLevel();
    }
  }

  @Unique
  @Nullable
  public LcDamage iSingularityLib$getLcDamage() {
    return imaginaryCraft$ColorType;
  }

  @Unique
  @Nullable
  public LcLevel iSingularityLib$getLcDamageLevel() {
    return imaginaryCraft$damageLevel;
  }

  @Unique
  public int iSingularityLib$getInvincibleTick() {
    return imaginaryCraft$invincibleTick;
  }

  @Unique
  public void iSingularityLib$setLcDamage(LcDamage type) {
    this.imaginaryCraft$ColorType = type;
  }

  @Unique
  public void iSingularityLib$setDamageLevel(LcLevel level) {
    this.imaginaryCraft$damageLevel = level;
  }

  @Unique
  public void iSingularityLib$setInvincibleTick(int tick) {
    this.imaginaryCraft$invincibleTick = tick;
  }
}
