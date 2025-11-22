package ctn.imaginarycraft.client.gui.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import ctn.ctnapi.client.gui.widget.ImageProgressBar;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HorizontalStatusBar extends ImageProgressBar.Horizontal {
  private final int internalXPos;
  private final int internalYPos;
  private final int internalWidth;
  private final int internalHeight;
  private final int bottomInternalXPos;
  private final int bottomInternalYPos;
  private final int bottomInternalWidth;
  private final int bottomInternalHeight;
  private final int coverInternalXPos;
  private final int coverInternalYPos;
  private final int coverInternalWidth;
  private final int coverInternalHeight;

  public HorizontalStatusBar(int x, int y,
                             int width, int height,
                             int internalXPos, int internalYPos,
                             int internalWidth, int internalHeight,
                             int bottomInternalXPos, int bottomInternalYPos,
                             int bottomInternalWidth, int bottomInternalHeight,
                             int coverInternalXPos, int coverInternalYPos,
                             int coverInternalWidth, int coverInternalHeight,
                             ResourceLocation texture) {
    super(x, y, width, height, 0, 0, texture, "", true);
    this.internalXPos = internalXPos;
    this.internalYPos = internalYPos;
    this.internalWidth = internalWidth;
    this.internalHeight = internalHeight;
    this.bottomInternalXPos = bottomInternalXPos;
    this.bottomInternalYPos = bottomInternalYPos;
    this.bottomInternalWidth = bottomInternalWidth;
    this.bottomInternalHeight = bottomInternalHeight;
    this.coverInternalXPos = coverInternalXPos;
    this.coverInternalYPos = coverInternalYPos;
    this.coverInternalWidth = coverInternalWidth;
    this.coverInternalHeight = coverInternalHeight;
  }

  @Override
  protected void renderTexture(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
    var pose = guiGraphics.pose();
    var x = getX();
    var y = getY();
    var width1 = getWidth();
    var height1 = getHeight();

//    if (this.bottom != null) {
//      guiGraphics.blitSprite(getTexture(), x, y, width1, height1);
//    }
//    renderInternal(guiGraphics, x, y, pose);
//    if (this.cover != null) {
//      guiGraphics.blitSprite(getTexture(), x, y, width1, height1);
//    }
  }

  private void renderInternal(final @NotNull GuiGraphics guiGraphics,
                              int x,
                              int y,
                              final PoseStack pose) {
    var textureWidth = this.getWidth();
    var textureHeight = this.getHeight();
    int uWidth = getInternalWidth();
    int vFlickerHeight = 0;
    int vHeight = (int) getRenderHeightValue();

    int xPosition = this.internalXPos;
    int yPosition = this.internalYPos + getInternalHeight() - vHeight;

    var internalHeight1 = getInternalHeight();
    x += xPosition;
    y += yPosition;

    vHeight = Math.clamp(vHeight, 0, internalHeight1);
    guiGraphics.blitSprite(getTexture(), textureWidth, textureHeight, xPosition, yPosition, x, y, uWidth, vHeight);
  }

  public double getRenderHeightValue() {
    return getRenderValue() / getMaxValue() * getInternalHeight();
  }

  public int getInternalHeight() {
    return this.internalHeight;
  }

  public int getInternalWidth() {
    return this.internalWidth;
  }
}
