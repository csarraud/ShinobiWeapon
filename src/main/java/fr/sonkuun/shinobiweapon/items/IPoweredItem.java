package fr.sonkuun.shinobiweapon.items;

import net.minecraft.entity.player.PlayerEntity;

public interface IPoweredItem {
	
	public void useFirstPower(PlayerEntity player);
	public void useSecondPower(PlayerEntity player);
	
	
}
