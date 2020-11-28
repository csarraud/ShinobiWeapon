package fr.sonkuun.shinobiweapon.entity.kunai;

import fr.sonkuun.shinobiweapon.capability.CapabilityShinobiWeapon;
import fr.sonkuun.shinobiweapon.capability.ShinobiWeaponData;
import fr.sonkuun.shinobiweapon.register.EntityTypeRegister;
import fr.sonkuun.shinobiweapon.register.ItemRegister;
import net.minecraft.client.renderer.Vector3d;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MinatoKunaiEntity extends AbstractKunaiEntity {

	public MinatoKunaiEntity(World world, LivingEntity thrower, Vector3d startPosition,
			float apexYaw, float apexPitch, double velocity) {
		super(EntityTypeRegister.MINATO_KUNAI, world, thrower, startPosition, apexYaw, apexPitch, velocity);
	}

	public MinatoKunaiEntity(EntityType<? extends ProjectileItemEntity> type, World world) {
		super(type, world);
	}

	public MinatoKunaiEntity(World world) {
		super(EntityTypeRegister.MINATO_KUNAI, world);
	}

	@Override
	public void onCollideWithPlayer(PlayerEntity player) {
		super.onCollideWithPlayer(player);
		
		if(this.thrower == null)
			return;
		
		if(this.removed && this.thrower.getCapability(CapabilityShinobiWeapon.CAPABILITY_SHINOBI_WEAPON).isPresent()) {
			ShinobiWeaponData shinobiWeaponData = this.thrower.getCapability(CapabilityShinobiWeapon.CAPABILITY_SHINOBI_WEAPON).orElse(null);
			shinobiWeaponData.setPlayerIsLookingToMinatoKunai(false);
		}
	}

	@Override
	public void tick() {

		if(!this.world.isRemote && this.thrower != null) {
			
			double distance = 10.0d;
			if(throwerLookInEntityDirection(this.thrower.getLookVec(), this.thrower.getEyePosition(0.5f), distance)) {
				if(this.thrower.getCapability(CapabilityShinobiWeapon.CAPABILITY_SHINOBI_WEAPON).isPresent()) {
					ShinobiWeaponData shinobiWeaponData = this.thrower.getCapability(CapabilityShinobiWeapon.CAPABILITY_SHINOBI_WEAPON).orElse(null);
					shinobiWeaponData.setPlayerIsLookingToMinatoKunai(true, this.getPositionVec());
					shinobiWeaponData.setMinatoKunaiUUID(this.getUniqueID());
				}
				this.setGlowing(true);
			}
			else {
				if(this.thrower.getCapability(CapabilityShinobiWeapon.CAPABILITY_SHINOBI_WEAPON).isPresent()) {
					ShinobiWeaponData shinobiWeaponData = this.thrower.getCapability(CapabilityShinobiWeapon.CAPABILITY_SHINOBI_WEAPON).orElse(null);
					shinobiWeaponData.setPlayerIsLookingToMinatoKunai(false);
				}
				this.setGlowing(false);
			}
		}

		super.tick();
	}
	
	public boolean throwerLookInEntityDirection(Vec3d lookVec, Vec3d eyePos, double distance) {
		double distanceReached = 0.0d;
		Vec3d entityPos = this.getPositionVec();
		Vec3d newPos = eyePos;
		
		while(distanceReached <= distance) {
			if(isInRangeToDetectLook(newPos, entityPos, 1.0d)) {
				return true;
			}
			
			distanceReached += 1.0d;
			newPos = new Vec3d(newPos.getX() + lookVec.getX(),
					newPos.getY() + lookVec.getY(),
					newPos.getZ() + lookVec.getZ());
		}
		
		return false;
	}
	
	public boolean isInRangeToDetectLook(Vec3d newPos, Vec3d entityPos, double rangeValue) {
		return isInRangeToDetectLookInOneAxis(newPos.getX(), entityPos.getX(), rangeValue)
				&& isInRangeToDetectLookInOneAxis(newPos.getY(), entityPos.getY(), rangeValue)
				&& isInRangeToDetectLookInOneAxis(newPos.getZ(), entityPos.getZ(), rangeValue);
	}
	
	public boolean isInRangeToDetectLookInOneAxis(double newCoord, double entityCoord, double rangeValue) {
		return newCoord <= entityCoord + rangeValue && newCoord >= entityCoord - rangeValue;
	}

	@Override
	protected Item getDefaultItem() {
		return ItemRegister.MINATO_KUNAI;
	}
}