package ctn.imaginarycraft.eventexecute;

import ctn.imaginarycraft.api.lobotomycorporation.LcDamage;
import ctn.imaginarycraft.api.lobotomycorporation.LcLevel;
import ctn.imaginarycraft.api.lobotomycorporation.util.LcDamageUtil;
import ctn.imaginarycraft.api.lobotomycorporation.util.RationalityUtil;
import ctn.imaginarycraft.capability.ILcLevel;
import ctn.imaginarycraft.capability.entity.IAbnos;
import ctn.imaginarycraft.client.util.ParticleUtil;
import ctn.imaginarycraft.mixinextend.IDamageSource;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import javax.annotation.Nullable;
import java.util.Iterator;

import static net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN;

public final class LcDamageEventExecutes {
  /**
   * 伤害回血
   */
  public static void heal(final LivingDamageEvent.Pre event, final float newDamage, final LivingEntity entity) {
    float healed = Math.abs(newDamage);
    entity.heal(healed);
    ParticleUtil.createTextParticles(entity, healed, false, true);
    event.getContainer().setPostAttackInvulnerabilityTicks(0);
    event.setNewDamage(0);
  }

  /**
   * 伤害计算
   */
  public static void vulnerableTreatment(LivingIncomingDamageEvent event,
                                         DamageSource damageSource,
                                         LivingEntity entity,
                                         @Nullable LcLevel damageLcLevel,
                                         @Nullable LcDamage lcDamageTypes) {
    Holder<DamageType> damageTypeHolder = damageSource.typeHolder();
    for (final ResourceKey<DamageType> damageType : LcDamage.getBypassKeys()) {
      if (damageTypeHolder.is(damageType)) {
        return;
      }
    }

    // 新伤害
    float newDamageAmount = event.getAmount();
    // 盔甲等级
    int armorItemStackLaval = 0;
    // 护甲数量
    int number = 0;
    boolean isArmorItemStackEmpty = true;

    //  盔甲
    Iterator<ItemStack> aitor = entity.getArmorAndBodyArmorSlots().iterator();
    ItemStack[] armorSlots = new ItemStack[4];
    for (int i = 0; aitor.hasNext(); i++) {
      armorSlots[i] = !(entity instanceof IAbnos) ?
        aitor.next() :
        ItemStack.EMPTY;
    }

    // 盔甲等级
    for (ItemStack armorItemStack : armorSlots) {
      if (armorItemStack != null && !armorItemStack.isEmpty()) {
        isArmorItemStackEmpty = false;
        armorItemStackLaval += ILcLevel.getItemLevelValue(armorItemStack);
        number++;
      }
    }

    // 等级处理 判断实体是否有护甲如果没有就用实体的等级
    if (damageLcLevel == null) {
      damageLcLevel = LcLevel.ZAYIN;
    }

    // 盔甲判断
    if (isArmorItemStackEmpty) {
      newDamageAmount *= LcDamageUtil.getDamageMultiple(LcLevel.getEntityLevel(entity), damageLcLevel);
    } else {
      armorItemStackLaval /= number;
      newDamageAmount *= LcDamageUtil.getDamageMultiple(armorItemStackLaval - damageLcLevel.getLevel());
    }

    // 伤害类型
    if (lcDamageTypes != null) {
      // 抗性处理
      var attributeInstance = entity.getAttribute(lcDamageTypes.getVulnerable());
      if (attributeInstance != null) {
        newDamageAmount *= (float) attributeInstance.getValue();
      }
    }

    event.setAmount(newDamageAmount);
  }

  /**
   * 伤害应用
   */
  public static void appliedDamageToEntity(final ServerLevel serverLevel, final LivingEntity entity, final DamageSource source, final float newDamage) {
    if (entity instanceof Player player) {
      RationalityUtil.setRecoveryTick(player, 10 * 20);
    }

    // 低抗缓慢
    AttributeInstance attributeInstance;
    @Nullable LcDamage lcDamage = IDamageSource.of(source).getLcDamage();
    if (lcDamage != null &&
      (attributeInstance = entity.getAttribute(lcDamage.getVulnerable())) != null &&
      attributeInstance.getValue() > 1.0) {
      entity.addEffect(new MobEffectInstance(MOVEMENT_SLOWDOWN, 20, 2));
    }

    // 生成粒子
    @Nullable ResourceKey<DamageType> damageType = lcDamage != null ? lcDamage.getDamageType() : source.typeHolder().getKey();
    // TODO 需要改良
//    @Nullable Vec3 sourcePosition = source.getSourcePosition();
//    @Nullable Entity entity1 = source.getEntity();
//    @Nullable Entity entity2 = source.getDirectEntity();
//    if (sourcePosition != null && (
//        entity1 != null && !entity1.position().equals(sourcePosition))) {
//      double x = sourcePosition.x;
//      double y = sourcePosition.y;
//      double z = sourcePosition.z;
//      ParticleUtil.randomColorTextParticles(serverLevel,
//        ParticleUtil.getText(newDamage, false), damageType, false, false, true, x, y, z);
//      return;
//    }
    ParticleUtil.createTextParticles(entity, damageType, newDamage, false, false);
  }
}
