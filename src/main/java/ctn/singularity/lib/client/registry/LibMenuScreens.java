package ctn.singularity.lib.client.registry;

import ctn.singularity.lib.core.LibMain;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = LibMain.LIB_ID, value = Dist.CLIENT)
public final class LibMenuScreens {
	@SubscribeEvent
	public static void registry(RegisterMenuScreensEvent event) {
	}
}
