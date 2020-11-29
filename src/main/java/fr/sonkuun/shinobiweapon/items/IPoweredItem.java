package fr.sonkuun.shinobiweapon.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public interface IPoweredItem {
	
	public boolean useFirstPower(PlayerEntity player);
	public boolean useSecondPower(PlayerEntity player);
	
	public void onItemLeftClick(World world, PlayerEntity player);
}
