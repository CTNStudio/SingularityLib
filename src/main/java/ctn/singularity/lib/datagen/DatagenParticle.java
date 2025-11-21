package ctn.singularity.lib.datagen;

import ctn.singularity.lib.client.particle.TextParticle;
import ctn.singularity.lib.client.particle.TextParticleProvider;
import ctn.singularity.lib.core.LibMain;
import ctn.singularity.lib.init.LibParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.ParticleDescriptionProvider;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class DatagenParticle extends ParticleDescriptionProvider {
  public DatagenParticle(PackOutput output, ExistingFileHelper fileHelper) {
    super(output, fileHelper);
  }

  private static @NotNull ResourceLocation getPath(String name) {
    return ResourceLocation.fromNamespaceAndPath(LibMain.LIB_ID, name);
  }

  @Override
  protected void addDescriptions() {
    createSprite(LibParticleTypes.TEXT_PARTICLE_TYPE, TextParticleProvider.TEXTURE_MAP.values().toArray(String[]::new));
  }

  private <p extends ParticleOptions> void createSprite(Supplier<ParticleType<p>> type, String name) {
    sprite(type.get(), ResourceLocation.fromNamespaceAndPath(LibMain.LIB_ID, name));
  }

  private <p extends ParticleOptions> void createSprite(Supplier<ParticleType<p>> type, String... names) {
    List<ResourceLocation> list = new ArrayList<>();
    for (String name : names) {
      list.add(getPath(name));
    }
    spriteSet(type.get(), list);
  }
}
