package ctn.imaginarycraft.config;

import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;

public final class ModClientConfig extends ConfigUtil {
  /**
   * 新玩家生命条
   */
  public final BooleanValue enableNewHealthBar;
  /**
   * 玩家低理智滤镜
   */
  public final BooleanValue enableLowRationalityFilter;
  /**
   * 玩家遭受四色伤害滤镜
   */
  public final BooleanValue enableFourColorDamageFilter;

  ModClientConfig(Builder builder) {
    enableNewHealthBar = define(
      builder, false, "enable_new_health_bar", "新玩家生命条");
    enableLowRationalityFilter = define(
      builder, true, "enable_low_rationality_filter", "玩家低理智滤镜");
    enableFourColorDamageFilter = define(
      builder, true, "enable_lobotomy_corporation_damage_filter", "玩家遭受四色伤害滤镜");
  }
}
