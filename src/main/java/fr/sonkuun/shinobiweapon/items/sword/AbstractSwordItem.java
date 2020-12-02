package fr.sonkuun.shinobiweapon.items.sword;

import fr.sonkuun.shinobiweapon.items.IPoweredItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemTier;
import net.minecraft.item.SwordItem;

public abstract class AbstractSwordItem extends SwordItem implements IPoweredItem {

	public AbstractSwordItem(int maxDamage, float attackDamage) {
		super(ItemTier.DIAMOND, maxDamage, attackDamage, new Properties().group(ItemGroup.COMBAT).maxStackSize(1));
	}
	
}
