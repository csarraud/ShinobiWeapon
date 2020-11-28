package fr.sonkuun.shinobiweapon.listener;

import fr.sonkuun.shinobiweapon.items.IPoweredItem;
import fr.sonkuun.shinobiweapon.register.KeyRegister;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ShinobiWeaponPowerListener {
	
	public static long POWER_COOLDOWN_DURATION_IN_TICKS = 20;
	public static long LAST_POWER_USE_IN_TICKS = 0;
	
	@SubscribeEvent
	public void playerTick(PlayerTickEvent event) {
		LAST_POWER_USE_IN_TICKS += 1;
		
		if(KeyRegister.FIRST_ITEM_POWER.isKeyDown() && isCooldownValid()
				&& event.player.getHeldItemMainhand().getItem() instanceof IPoweredItem) {
			IPoweredItem poweredItem = (IPoweredItem) event.player.getHeldItemMainhand().getItem();
			if(poweredItem.useFirstPower(event.player))
				LAST_POWER_USE_IN_TICKS = 0;
		}
		else if(KeyRegister.SECOND_ITEM_POWER.isKeyDown() && isCooldownValid()
				&& event.player.getHeldItemMainhand().getItem() instanceof IPoweredItem) {
			IPoweredItem poweredItem = (IPoweredItem) event.player.getHeldItemMainhand().getItem();
			if(poweredItem.useSecondPower(event.player))
				LAST_POWER_USE_IN_TICKS = 0;
		}
	}
	
	public boolean isCooldownValid() {
		return LAST_POWER_USE_IN_TICKS >= POWER_COOLDOWN_DURATION_IN_TICKS;
	}
}
