package ctn.singularity.lib.registry;

import ctn.singularity.lib.core.LibMain;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

/**
 * 指令事件
 */
@EventBusSubscriber(modid = LibMain.LIB_ID)
public  final class LibCommand {
	@SubscribeEvent
	public static void registry(RegisterCommandsEvent event) {
	}
}
