package fr.sonkuun.shinobiweapon.register;

import java.util.ArrayList;
import java.util.List;

import fr.sonkuun.shinobiweapon.ShinobiWeapon;
import fr.sonkuun.shinobiweapon.items.kunai.KunaiItem;
import fr.sonkuun.shinobiweapon.items.kunai.MinatoKunaiItem;
import fr.sonkuun.shinobiweapon.items.scroll.BlankScrollItem;
import fr.sonkuun.shinobiweapon.items.shuriken.ShurikenItem;
import fr.sonkuun.shinobiweapon.items.sword.JashinScytheItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class ItemRegister {

	public static final List<Item> ITEMS = new ArrayList<Item>();

	public static final Item KUNAI = new KunaiItem();
	public static final Item MINATO_KUNAI = new MinatoKunaiItem();
	public static final Item SHURIKEN = new ShurikenItem();
	public static final Item JASHIN_SCYTHE = new JashinScytheItem();
	public static final Item BLANK_SCROLL = new BlankScrollItem();

	static {
		KUNAI.setRegistryName(ShinobiWeapon.MODID, "kunai");
		MINATO_KUNAI.setRegistryName(ShinobiWeapon.MODID, "minato_kunai");
		SHURIKEN.setRegistryName(ShinobiWeapon.MODID, "shuriken");
		JASHIN_SCYTHE.setRegistryName(ShinobiWeapon.MODID, "jashin_scythe");
		BLANK_SCROLL.setRegistryName(ShinobiWeapon.MODID, "blank_scroll");

		ITEMS.add(KUNAI);
		ITEMS.add(MINATO_KUNAI);
		ITEMS.add(SHURIKEN);
		ITEMS.add(JASHIN_SCYTHE);
		ITEMS.add(BLANK_SCROLL);
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
