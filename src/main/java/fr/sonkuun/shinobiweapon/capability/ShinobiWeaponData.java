package fr.sonkuun.shinobiweapon.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.capabilities.Capability;

public class ShinobiWeaponData {
	
	private boolean playerIsLookingToMinatoKunai;
	private Vec3d minatoKunaiPosition;
	
	public ShinobiWeaponData() {
		this.playerIsLookingToMinatoKunai = false;
		this.minatoKunaiPosition = Vec3d.ZERO;
	}
	
	public boolean playerIsLookingToMinatoKunai() {
		return playerIsLookingToMinatoKunai;
	}
	
	public void setPlayerIsLookingToMinatoKunai(boolean isLooking, Vec3d kunaiPosition) {
		this.playerIsLookingToMinatoKunai = isLooking;
		this.minatoKunaiPosition = kunaiPosition;
	}
	
	public void setPlayerIsLookingToMinatoKunai(boolean isLooking) {
		this.playerIsLookingToMinatoKunai = isLooking;
	}
	
	public Vec3d getMinatoKunaiPosition() {
		return minatoKunaiPosition;
	}
	
	public static final String PLAYER_IS_LOOKING_TO_MINATO_KUNAI_NBT = "player_is_looking_to_minato_kunai";
	public static final String MINATO_KUNAI_POSITION_X_NBT = "minato_kunai_position_x";
	public static final String MINATO_KUNAI_POSITION_Y_NBT = "minato_kunai_position_y";
	public static final String MINATO_KUNAI_POSITION_Z_NBT = "minato_kunai_position_z";
	
	public static class ShinobiWeaponDataNBTStorage implements Capability.IStorage<ShinobiWeaponData> {
		
		@Override
		public INBT writeNBT(Capability<ShinobiWeaponData> capability, ShinobiWeaponData instance, Direction side) {
			CompoundNBT tag = new CompoundNBT();
			
			tag.putBoolean(PLAYER_IS_LOOKING_TO_MINATO_KUNAI_NBT, instance.playerIsLookingToMinatoKunai);
			tag.putDouble(MINATO_KUNAI_POSITION_X_NBT, instance.minatoKunaiPosition.getX());
			tag.putDouble(MINATO_KUNAI_POSITION_Y_NBT, instance.minatoKunaiPosition.getY());
			tag.putDouble(MINATO_KUNAI_POSITION_Z_NBT, instance.minatoKunaiPosition.getZ());
			
			return tag;
		}

		@Override
		public void readNBT(Capability<ShinobiWeaponData> capability, ShinobiWeaponData instance, Direction side,
				INBT nbt) {
			if(nbt instanceof CompoundNBT) {
				CompoundNBT tag = (CompoundNBT) nbt;
				
				instance.setPlayerIsLookingToMinatoKunai(
						tag.getBoolean(PLAYER_IS_LOOKING_TO_MINATO_KUNAI_NBT),
						new Vec3d(tag.getDouble(MINATO_KUNAI_POSITION_X_NBT),
								tag.getDouble(MINATO_KUNAI_POSITION_Y_NBT),
								tag.getDouble(MINATO_KUNAI_POSITION_Z_NBT)));
			}
		}
		
	}
	
	public static ShinobiWeaponData createADefaultInstance() {
		return new ShinobiWeaponData();
	}
	
}
