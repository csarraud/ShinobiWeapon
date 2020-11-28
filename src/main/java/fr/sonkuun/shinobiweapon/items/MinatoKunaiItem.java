package fr.sonkuun.shinobiweapon.items;

import java.util.UUID;

import fr.sonkuun.shinobiweapon.capability.CapabilityShinobiWeapon;
import fr.sonkuun.shinobiweapon.capability.ShinobiWeaponData;
import fr.sonkuun.shinobiweapon.entity.kunai.AbstractKunaiEntity;
import fr.sonkuun.shinobiweapon.entity.kunai.MinatoKunaiEntity;
import fr.sonkuun.shinobiweapon.register.ItemRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Vector3d;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

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
	public boolean useFirstPower(PlayerEntity player) {
		/*
		 * Shinobi Weapon capability is required to using item power
		 */
		ShinobiWeaponData shinobiWeaponData = player.getCapability(CapabilityShinobiWeapon.CAPABILITY_SHINOBI_WEAPON).orElse(null);

		if(shinobiWeaponData == null) {
			return false;
		}

		if(shinobiWeaponData.playerIsLookingToMinatoKunai() && player.world instanceof ServerWorld) {
			ServerWorld serverWorld = (ServerWorld) player.world;
			Entity entity = serverWorld.getEntityByUuid(shinobiWeaponData.getMinatoKunaiUUID());
			
			if(entity == null)
				return false;
			
			Vec3d kunaiPos = entity.getPositionVec();

			/*
			 * Refill item in player inventory if not full
			 */
			for(ItemStack stack : player.inventory.mainInventory) {
				if(stack.getItem().equals(this.getItem()) && stack.getCount() < stack.getMaxStackSize()) {
					stack.setCount(stack.getCount() + 1);
					entity.remove();
					break;
				}
			}

			if(!entity.removed && player.inventory.getFirstEmptyStack() != -1) {
				player.inventory.add(player.inventory.getFirstEmptyStack(), new ItemStack(this.getItem()));
				entity.remove();
			}
			
			player.setPositionAndUpdate(kunaiPos.x, kunaiPos.y, kunaiPos.z);
			return true;
		}
		
		return false;
	}

	@Override
	public boolean useSecondPower(PlayerEntity player) {
		return false;
	}

}
