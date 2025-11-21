package ctn.singularity.lib.datagen;

import ctn.singularity.lib.core.LibMain;
import ctn.singularity.lib.init.world.LibAttributes;
import ctn.singularity.lib.init.world.LibDamageTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.function.Supplier;


public final class DatagenI18ZhCn extends LanguageProvider {
  public DatagenI18ZhCn(PackOutput output) {
    super(output, LibMain.LIB_ID, "zh_cn");
  }

  @Override
  protected void addTranslations() {
    add("pack.singularitylib.description", "奇点Lib");
    addPlayerDeathMessage(LibDamageTypes.PHYSICS, "%s死于%s的造成的物理伤害");
    addPlayerDeathMessage(LibDamageTypes.SPIRIT, "%s死于%s的造成的精神污染");
    addPlayerDeathMessage(LibDamageTypes.EROSION, "%s死于%s的造成的侵蚀伤害");
    addPlayerDeathMessage(LibDamageTypes.THE_SOUL, "%s死于%s的造成的灵魂伤害");
    addPlayerDeathMessage(LibDamageTypes.EGO, "%s死于%s的E.G.O");
    addDeathMessage(LibDamageTypes.PHYSICS, "%s被剁成肉沫了");
    addDeathMessage(LibDamageTypes.SPIRIT, "%s精神崩溃而死");
    addDeathMessage(LibDamageTypes.EROSION, "%s因腐蚀而亡");
    addDeathMessage(LibDamageTypes.THE_SOUL, "%s的灵魂被超度了");
    addDeathMessage(LibDamageTypes.EGO, "%s死于E.G.O");
    addAttribute(LibAttributes.PHYSICS_VULNERABLE.get(), "物理易伤");
    addAttribute(LibAttributes.SPIRIT_VULNERABLE.get(), "精神易伤");
    addAttribute(LibAttributes.EROSION_VULNERABLE.get(), "侵蚀易伤");
    addAttribute(LibAttributes.THE_SOUL_VULNERABLE.get(), "灵魂易伤");
  }

  public <T> void addDataComponent(DataComponentType<T> dataComponentType, String name) {
    add(dataComponentType.toString(), name);
  }

  /** 生物属性翻译 */
  public void addAttribute(Attribute attributeHolder, String name) {
    add(attributeHolder.getDescriptionId(), name);
  }

  /**
   * 死亡消息翻译
   */
  public void addDeathMessage(ResourceKey<DamageType> damageType, String name) {
    add("death.attack." + damageType.location().getPath(), name);
  }

  /**
   * 玩家死亡消息翻译
   */
  public void addPlayerDeathMessage(ResourceKey<DamageType> damageType, String name) {
    add("death.attack." + damageType.location().getPath() + ".player", name);
  }
}
