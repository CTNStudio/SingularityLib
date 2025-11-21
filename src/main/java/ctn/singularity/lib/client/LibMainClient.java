package ctn.singularity.lib.client;

import ctn.singularity.lib.core.LibMain;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(value = LibMain.LIB_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = LibMain.LIB_ID, value = Dist.CLIENT)
public final class LibMainClient {
  public LibMainClient(ModContainer container) {
  }

  @SubscribeEvent
  static void onClientSetup(FMLClientSetupEvent event) {
    LibMain.LOGGER.error("Client {}", LibMain.LIB_NAME);
  }
}
