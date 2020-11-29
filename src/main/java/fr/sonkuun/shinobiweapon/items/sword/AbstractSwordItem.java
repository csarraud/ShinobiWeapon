package fr.sonkuun.shinobiweapon.items.sword;

import fr.sonkuun.shinobiweapon.items.IPoweredItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public abstract class AbstractSwordItem extends Item implements IPoweredItem {

	public AbstractSwordItem() {
		super(new Properties().group(ItemGroup.COMBAT).maxStackSize(1));
	}

	
}
