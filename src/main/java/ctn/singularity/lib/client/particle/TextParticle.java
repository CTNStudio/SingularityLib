package ctn.singularity.lib.client.particle;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.OutlineBufferSource;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

public class TextParticle extends TextureSheetParticle {
  private final Component textComponent;
  private final boolean isHeal;
  private final int fontColor;
  private final int strokeColor;
  private final float maxSize;
  private final float maxTick;

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
    setSprite(sprite);
    this.textComponent = textComponent;
    this.isHeal = isHeal;
    this.fontColor = fontColor;
    this.strokeColor = strokeColor;
    float maxSize = 0.05f;
    float maxTick = isHeal ? 20 : 20 * 3;
    this.maxSize = maxSize;
    this.maxTick = maxTick;
  }

  @Override
  public void render(@NotNull VertexConsumer vertexConsumer, Camera camera, float partialTicks) {
    Minecraft minecraft = Minecraft.getInstance();
    OutlineBufferSource bufferSource = minecraft.renderBuffers().outlineBufferSource();
    Font font = minecraft.font;
    Vec3 camPos = camera.getPosition();
    int getLightColor = getLightColor(partialTicks);
    PoseStack poseStack = new PoseStack();
    float partialAge = this.age + partialTicks;
    float sizeFactor = Math.abs((float) Math.sin((partialAge / this.maxTick) * Math.PI));
    float size = sizeFactor * maxSize;
    float fontWidth = font.getSplitter().stringWidth(this.textComponent);
    int height = font.lineHeight;
    float textX = - fontWidth / 2f;
    float textY = - height / 2f;

    poseStack.pushPose();

    float quadSize = getQuadSize(1) * 0.15f;
    double x1 = getX(partialTicks) - camPos.x;
    double y1 = getY(partialTicks) - camPos.y;
    double z1 = getZ(partialTicks) - camPos.z;
    poseStack.translate(x1, y1, z1);
    poseStack.mulPose(camera.rotation());
    poseStack.mulPose(Axis.XP.rotationDegrees(180));

    // TODO 补充
    poseStack.scale(quadSize, quadSize, quadSize);
    poseStack.translate(fontWidth / 2 + 7, 0, 0);
    Matrix4f matrix = poseStack.last().pose();

    font.drawInBatch(this.textComponent, textX + 1, textY + 1, this.strokeColor, false, matrix, bufferSource, Font.DisplayMode.SEE_THROUGH, this.strokeColor, getLightColor);
    font.drawInBatch(this.textComponent, textX, textY, this.fontColor, false, matrix.translate(0, 0, -0.5f), bufferSource, Font.DisplayMode.SEE_THROUGH, this.fontColor, getLightColor);

    bufferSource.endOutlineBatch();
    if (sprite != null) {
      RenderSystem.enableDepthTest();
      super.render(vertexConsumer, camera, partialTicks);
      RenderSystem.disableDepthTest();
    }

    poseStack.popPose();
  }

  private double getX(float partialTicks) {
    return Mth.lerp(partialTicks, this.xo, this.x);
  }

  private double getY(float partialTicks) {
    return Mth.lerp(partialTicks, this.yo, this.y);
  }

  private double getZ(float partialTicks) {
    return Mth.lerp(partialTicks, this.zo, this.z);
  }

  @Override
  protected int getLightColor(final float partialTick) {
    return LightTexture.FULL_BRIGHT;
  }

  @Override
  public void tick() {
    this.age++;
    if (this.age > this.maxTick) {
      remove();
    }
  }

  @Override
  public @NotNull ParticleRenderType getRenderType() {
    return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
  }
}
