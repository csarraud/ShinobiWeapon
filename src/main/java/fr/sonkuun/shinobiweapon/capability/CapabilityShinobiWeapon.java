package fr.sonkuun.shinobiweapon.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityShinobiWeapon {
	
	@CapabilityInject(ShinobiWeaponData.class)
	public static Capability<ShinobiWeaponData> CAPABILITY_SHINOBI_WEAPON = null;
	
	public static void register() {
		CapabilityManager.INSTANCE.register(
				ShinobiWeaponData.class,
				new ShinobiWeaponData.ShinobiWeaponDataNBTStorage(),
				ShinobiWeaponData::createADefaultInstance);
	}
}
