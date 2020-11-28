package fr.sonkuun.shinobiweapon.items;

import fr.sonkuun.shinobiweapon.capability.CapabilityShinobiWeapon;
import fr.sonkuun.shinobiweapon.capability.ShinobiWeaponData;
import fr.sonkuun.shinobiweapon.entity.kunai.AbstractKunaiEntity;
import fr.sonkuun.shinobiweapon.entity.kunai.MinatoKunaiEntity;
import fr.sonkuun.shinobiweapon.register.ItemRegister;
import net.minecraft.client.renderer.Vector3d;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MinatoKunaiItem extends AbstractKunaiItem {
	
	public MinatoKunaiItem() {
		super();
	}

	@Override
	public AbstractKunaiEntity createKunaiEntity(World world, PlayerEntity thrower, Vector3d startPosition,
			float rotationYaw, float rotationPitch, double velocity) {
		return new MinatoKunaiEntity(world, thrower, startPosition, rotationYaw, rotationPitch, velocity);
	}

	@Override
	public Item getItem() {
		return ItemRegister.MINATO_KUNAI;
	}

	@Override
	public void useFirstPower(PlayerEntity player) {
		/*
		 * Shinobi Weapon capability is required to using item power
		 */
		ShinobiWeaponData shinobiWeaponData = player.getCapability(CapabilityShinobiWeapon.CAPABILITY_SHINOBI_WEAPON).orElse(null);
		
		if(shinobiWeaponData == null) {
			return;
		}
		
		if(shinobiWeaponData.playerIsLookingToMinatoKunai()) {
			Vec3d kunaiPos = shinobiWeaponData.getMinatoKunaiPosition();
			player.setPositionAndUpdate(kunaiPos.x, kunaiPos.y, kunaiPos.z);
		}
	}

	@Override
	public void useSecondPower(PlayerEntity player) {
		// TODO Auto-generated method stub
		
	}

}
