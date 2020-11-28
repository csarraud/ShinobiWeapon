package fr.sonkuun.shinobiweapon.listener;

import fr.sonkuun.shinobiweapon.items.IPoweredItem;
import fr.sonkuun.shinobiweapon.register.KeyRegister;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ShinobiWeaponPowerListener {
	
	@SubscribeEvent
	public void playerTick(PlayerTickEvent event) {
		if(KeyRegister.FIRST_ITEM_POWER.isPressed() && event.player.getHeldItemMainhand().getItem() instanceof IPoweredItem) {
			IPoweredItem poweredItem = (IPoweredItem) event.player.getHeldItemMainhand().getItem();
			poweredItem.useFirstPower(event.player);
		}
	}
}
