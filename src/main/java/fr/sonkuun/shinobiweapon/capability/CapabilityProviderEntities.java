package fr.sonkuun.shinobiweapon.capability;

import javax.annotation.Nonnull;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class CapabilityProviderEntities implements ICapabilitySerializable<INBT> {

	private final Direction NO_SPECIFIC_SIDE = null;

	private ShinobiWeaponData shinobiWeaponData = new ShinobiWeaponData();

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
		if (CapabilityShinobiWeapon.CAPABILITY_SHINOBI_WEAPON == capability) {
			return (LazyOptional<T>)LazyOptional.of(()-> shinobiWeaponData);
		}

		return LazyOptional.empty();
	}

	private static final String SHINOBI_WEAPON_DATA_NBT = "shinobi_weapon_data";

	@Override
	public INBT serializeNBT() {
		CompoundNBT nbt = new CompoundNBT();

		INBT shinobiWeaponDataNBT = CapabilityShinobiWeapon.CAPABILITY_SHINOBI_WEAPON.writeNBT(shinobiWeaponData, NO_SPECIFIC_SIDE);

		nbt.put(SHINOBI_WEAPON_DATA_NBT, shinobiWeaponDataNBT);
		return nbt;
	}

	@Override
	public void deserializeNBT(INBT nbt) {
		CompoundNBT tag = (CompoundNBT) nbt;
		INBT shinobiWeaponDataNBT = tag.get(SHINOBI_WEAPON_DATA_NBT);
		
		CapabilityShinobiWeapon.CAPABILITY_SHINOBI_WEAPON.readNBT(shinobiWeaponData, NO_SPECIFIC_SIDE, shinobiWeaponDataNBT);
	}

}
