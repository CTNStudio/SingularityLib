package ctn.singularity.lib.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import ctn.ctnapi.client.util.ColorUtil;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import org.apache.logging.log4j.util.StringMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

import static ctn.singularity.lib.api.lobotomycorporation.LcDamage.PHYSICS;
import static net.minecraft.world.damagesource.DamageTypes.GENERIC_KILL;

public class TextParticle extends Particle {

  @Nullable
  private TextureAtlasSprite sprite;
  private final Component textComponent;
  private final boolean isHeal;
  private final int fontColor;
  private final int strokeColor;

  protected TextParticle(final ClientLevel level,
                         final @Nullable TextureAtlasSprite sprite,
                         final double x,
                         final double y,
                         final double z,
                         final Component textComponent,
                         final int fontColor,
                         final int strokeColor,
                         final boolean isHeal) {
    super(level, x, y, z);
    this.sprite = sprite;
    this.textComponent = textComponent;
    this.isHeal = isHeal;
    this.fontColor = fontColor;
    this.strokeColor = strokeColor;
  }

  @Override
  public void render(@NotNull VertexConsumer buffer, Camera camera, float partialTicks) {
  }

  @Override
  public void tick() {
  }

  @Override
  public @NotNull ParticleRenderType getRenderType() {
    return ParticleRenderType.CUSTOM;
  }
}
