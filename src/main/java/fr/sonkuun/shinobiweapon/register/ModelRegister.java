package fr.sonkuun.shinobiweapon.register;

import fr.sonkuun.shinobiweapon.renderer.KunaiRenderer;
import fr.sonkuun.shinobiweapon.renderer.MinatoKunaiRenderer;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class ModelRegister {

	public static void init() {
		
	}

	@SubscribeEvent
	public static void onModelRegistryEvent(ModelRegistryEvent event) {
		ModelLoader.addSpecialModel(KunaiRenderer.KUNAI_MODEL_RESOURCE_LOCATION);
		ModelLoader.addSpecialModel(MinatoKunaiRenderer.MINATO_KUNAI_MODEL_RESOURCE_LOCATION);
	}
}
