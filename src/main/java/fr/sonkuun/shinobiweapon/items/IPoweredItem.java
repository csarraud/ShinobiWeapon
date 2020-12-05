package fr.sonkuun.shinobiweapon.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public interface IPoweredItem {
	
	public boolean useFirstPower(PlayerEntity player);
	public boolean useSecondPower(PlayerEntity player);
	
	public void updatePowerTicks();
	
	public boolean canUseFirstPower();
	public boolean canUseSecondPower();
	
	public ResourceLocation getFirstPowerHUDTexture();
	public ResourceLocation getSecondPowerHUDTexture();
	
	public void playerDamagedLivingEntity(LivingDamageEvent event);
	public void livingEntityDamagedPlayer(LivingDamageEvent event);
	public void environmentDamagedPlayer(LivingDamageEvent event);
}
