package fr.sonkuun.shinobiweapon;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import fr.sonkuun.shinobiweapon.capability.CapabilityAttachEventHandler;
import fr.sonkuun.shinobiweapon.capability.CapabilityShinobiWeapon;
import fr.sonkuun.shinobiweapon.entity.kunai.KunaiEntity;
import fr.sonkuun.shinobiweapon.entity.kunai.MinatoKunaiEntity;
import fr.sonkuun.shinobiweapon.entity.shuriken.ShurikenEntity;
import fr.sonkuun.shinobiweapon.listener.ShinobiWeaponPowerListener;
import fr.sonkuun.shinobiweapon.register.EntityTypeRegister;
import fr.sonkuun.shinobiweapon.register.RegistryHandler;
import fr.sonkuun.shinobiweapon.renderer.KunaiRenderer;
import fr.sonkuun.shinobiweapon.renderer.MinatoKunaiRenderer;
import fr.sonkuun.shinobiweapon.renderer.ShurikenRenderer;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("shinobiweapon")
public class ShinobiWeapon
{
	public static final String MODID = "shinobiweapon";

	private static final Logger LOGGER = LogManager.getLogger();

	public ShinobiWeapon() {
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.addListener(this::setup);
		modEventBus.addListener(this::enqueueIMC);
		modEventBus.addListener(this::processIMC);
		modEventBus.addListener(this::doClientStuff);
		
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new ShinobiWeaponPowerListener());
		
		ShinobiWeaponPowerListener.init();
		RegistryHandler.init();
		
	}

	private void setup(final FMLCommonSetupEvent event)
	{
		CapabilityShinobiWeapon.register();
		
		MinecraftForge.EVENT_BUS.register(CapabilityAttachEventHandler.class);
	}


	private void doClientStuff(final FMLClientSetupEvent event) {

		RenderingRegistry.registerEntityRenderingHandler(EntityTypeRegister.KUNAI, new KunaiRenderFactory());
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeRegister.MINATO_KUNAI, new MinatoKunaiRenderFactory());
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeRegister.SHURIKEN, new ShurikenRenderFactory());
	}

	private static class KunaiRenderFactory implements IRenderFactory<KunaiEntity> {
		@Override
		public EntityRenderer<KunaiEntity> createRenderFor(EntityRendererManager manager) {
			return new KunaiRenderer(manager);
		}
	}
	
	private static class MinatoKunaiRenderFactory implements IRenderFactory<MinatoKunaiEntity> {
		@Override
		public EntityRenderer<MinatoKunaiEntity> createRenderFor(EntityRendererManager manager) {
			return new MinatoKunaiRenderer(manager);
		}
	}
	
	private static class ShurikenRenderFactory implements IRenderFactory<ShurikenEntity> {
		@Override
		public EntityRenderer<? super ShurikenEntity> createRenderFor(EntityRendererManager manager) {
			return new ShurikenRenderer(manager);
		}
	}

	private void enqueueIMC(final InterModEnqueueEvent event)
	{
		InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
	}

	private void processIMC(final InterModProcessEvent event)
	{
		LOGGER.info("Got IMC {}", event.getIMCStream().
				map(m->m.getMessageSupplier().get()).
				collect(Collectors.toList()));
	}

	@SubscribeEvent
	public void onServerStarting(FMLServerStartingEvent event) {
		LOGGER.info("HELLO from server starting");
	}
}
