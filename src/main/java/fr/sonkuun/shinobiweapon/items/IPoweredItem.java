package fr.sonkuun.shinobiweapon.items;

import net.minecraft.entity.player.PlayerEntity;

public interface IPoweredItem {
	
	public boolean useFirstPower(PlayerEntity player);
	public boolean useSecondPower(PlayerEntity player);
	
	
}
