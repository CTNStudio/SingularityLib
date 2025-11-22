package ctn.imaginarycraft.client.gui.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import ctn.ctnapi.client.gui.widget.ImageProgressBar;
import ctn.imaginarycraft.api.lobotomycorporation.util.RationalityUtil;
import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class RationalityBarLayer implements LayeredDraw.Layer {
  public static final RationalityBarLayer INSTANCE = new RationalityBarLayer();
  public static final ResourceLocation TEXTURE = ImaginaryCraft.modRl("hud_bar/rationality_bar");

  private final Minecraft minecraft;
  private Player player;
  private final Font font;
  private int leftPos;
  private int topPos;
  private int screenWidth;
  private int screenHeight;
  private double rationality;
  private double maxRationality;

  public RationalityBarLayer() {
    this.minecraft = Minecraft.getInstance();
    this.font = this.minecraft.font;
  }

  @Override
  public void render(final GuiGraphics guiGraphics, final DeltaTracker deltaTracker) {
    if (this.minecraft.options.hideGui) {
      return;
    }
    init(guiGraphics);
  }

  private void init(final GuiGraphics guiGraphics) {
    var newPlayer = Objects.requireNonNull(this.minecraft.player);
    var newRationality = RationalityUtil.getValue(player);
    var newMaxRationality = RationalityUtil.getMaxValue(player);
    if (this.player != newPlayer) {
      this.player = newPlayer;
    }
    if (newRationality != this.rationality) {
      this.rationality = newRationality;
    }
    if (newMaxRationality != this.maxRationality) {
      this.maxRationality = newMaxRationality;
    }
    int oldScreenWidth = guiGraphics.guiWidth();
    int oldScreenHeight = guiGraphics.guiHeight();

    if (oldScreenWidth != this.screenWidth || oldScreenHeight != this.screenHeight) {
      this.screenWidth = oldScreenWidth;
      this.screenHeight = oldScreenHeight;
//      this.leftPos = oldScreenWidth / 2 - this.sourceSoulBarWidth / 2;
//      this.topPos = oldScreenHeight - 65;
//      this.sourceSoulBar.setX(this.leftPos);
//      this.sourceSoulBar.setY(this.topPos);
    }
  }
}
