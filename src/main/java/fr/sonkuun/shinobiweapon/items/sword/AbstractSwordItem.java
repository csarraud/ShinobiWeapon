package fr.sonkuun.shinobiweapon.items.sword;

import fr.sonkuun.shinobiweapon.items.IPoweredItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemTier;
import net.minecraft.item.SwordItem;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public abstract class AbstractSwordItem extends SwordItem implements IPoweredItem {

	public long firstPowerLastUseInTicks;
	public long secondPowerLastUseInTicks;
	
	public long firstPowerCooldownInTicks;
	public long secondPowerCooldownInTicks;
	
	public AbstractSwordItem(int maxDamage, float attackDamage) {
		super(ItemTier.DIAMOND, maxDamage, attackDamage, new Properties().group(ItemGroup.COMBAT).maxStackSize(1));
		
		this.firstPowerLastUseInTicks = 0;
		this.secondPowerLastUseInTicks = 0;
		
		this.firstPowerCooldownInTicks = 0;
		this.secondPowerCooldownInTicks = 0;
	}

	@Override
	public void updatePowerTicks() {
		this.firstPowerLastUseInTicks++;
		this.secondPowerLastUseInTicks++;		
	}

	@Override
	public void playerDamagedLivingEntity(LivingDamageEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void livingEntityDamagedPlayer(LivingDamageEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void environmentDamagedPlayer(LivingDamageEvent event) {
		// TODO Auto-generated method stub
		
	}
	
}
