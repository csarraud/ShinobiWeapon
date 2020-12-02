package fr.sonkuun.shinobiweapon.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public interface IPoweredItem {
	
	public boolean useFirstPower(PlayerEntity player);
	public boolean useSecondPower(PlayerEntity player);
	
	public void damageLivingEntity(LivingDamageEvent event);
}
