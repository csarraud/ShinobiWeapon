package fr.sonkuun.shinobiweapon.listener;

import java.util.HashMap;

import fr.sonkuun.shinobiweapon.capability.CapabilityShinobiWeapon;
import fr.sonkuun.shinobiweapon.capability.ShinobiWeaponData;
import fr.sonkuun.shinobiweapon.entity.kunai.MinatoKunaiEntity;
import fr.sonkuun.shinobiweapon.items.IPoweredItem;
import fr.sonkuun.shinobiweapon.register.KeyRegister;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ShinobiWeaponPowerListener {

	public static long POWER_COOLDOWN_DURATION_IN_TICKS = 20;
	public static long LAST_POWER_USE_IN_TICKS = 0;

	public static HashMap<String, MinatoKunaiEntity> MINATO_KUNAI_ENTITIES_MAP;

	public static void init() {
		MINATO_KUNAI_ENTITIES_MAP = new HashMap<String, MinatoKunaiEntity>();
	}

	@SubscribeEvent
	public void playerTick(PlayerTickEvent event) {

		updatePlayerLookingAtMinatoKunai(event);

		LAST_POWER_USE_IN_TICKS += 1;

		if(KeyRegister.FIRST_ITEM_POWER.isKeyDown() && isCooldownValid()
				&& event.player.getHeldItemMainhand().getItem() instanceof IPoweredItem) {
			IPoweredItem poweredItem = (IPoweredItem) event.player.getHeldItemMainhand().getItem();
			if(poweredItem.useFirstPower(event.player))
				LAST_POWER_USE_IN_TICKS = 0;
		}
		else if(KeyRegister.SECOND_ITEM_POWER.isKeyDown() && isCooldownValid()
				&& event.player.getHeldItemMainhand().getItem() instanceof IPoweredItem) {
			IPoweredItem poweredItem = (IPoweredItem) event.player.getHeldItemMainhand().getItem();
			if(poweredItem.useSecondPower(event.player))
				LAST_POWER_USE_IN_TICKS = 0;
		}
		
	}

	public boolean isCooldownValid() {
		return LAST_POWER_USE_IN_TICKS >= POWER_COOLDOWN_DURATION_IN_TICKS;
	}

	public void updatePlayerLookingAtMinatoKunai(PlayerTickEvent event) {
		double distance = 10.0d;
		PlayerEntity player = event.player;
		
		if(!player.getCapability(CapabilityShinobiWeapon.CAPABILITY_SHINOBI_WEAPON).isPresent()) {
			return;
		}
		
		ShinobiWeaponData shinobiWeaponData = player.getCapability(CapabilityShinobiWeapon.CAPABILITY_SHINOBI_WEAPON).orElse(null);
		shinobiWeaponData.setPlayerIsLookingToMinatoKunai(false);
		
		for(MinatoKunaiEntity kunai : MINATO_KUNAI_ENTITIES_MAP.values()) {
			if(throwerLookInEntityDirection(player.getLookVec(), player.getEyePosition(0.5f), kunai.getPositionVec(), distance)) {

				shinobiWeaponData.setPlayerIsLookingToMinatoKunai(true, kunai.getPositionVec());
				shinobiWeaponData.setMinatoKunaiUUID(kunai.getUniqueID());

				kunai.setGlowing(true);
			}
			else {
				kunai.setGlowing(false);
			}
		}
	}

	public boolean throwerLookInEntityDirection(Vec3d lookVec, Vec3d eyePos, Vec3d entityPos, double distance) {
		double distanceReached = 0.0d;
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
}
