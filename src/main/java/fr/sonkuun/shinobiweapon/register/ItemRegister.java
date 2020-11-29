package fr.sonkuun.shinobiweapon.register;

import java.util.ArrayList;
import java.util.List;

import fr.sonkuun.shinobiweapon.ShinobiWeapon;
import fr.sonkuun.shinobiweapon.items.kunai.KunaiItem;
import fr.sonkuun.shinobiweapon.items.kunai.MinatoKunaiItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class ItemRegister {

	public static final List<Item> ITEMS = new ArrayList<Item>();

	public static final Item KUNAI = new KunaiItem();
	public static final Item MINATO_KUNAI = new MinatoKunaiItem();

	static {
		KUNAI.setRegistryName(ShinobiWeapon.MODID, "kunai");
		MINATO_KUNAI.setRegistryName(ShinobiWeapon.MODID, "minato_kunai");

		ITEMS.add(KUNAI);
		ITEMS.add(MINATO_KUNAI);
	}

	public static void init() {

	}

	@SubscribeEvent
	public static void registerItems(final RegistryEvent.Register<Item> event) {

		for(final Item item : ITEMS) {
			event.getRegistry().register(item);
		}
	}
}
