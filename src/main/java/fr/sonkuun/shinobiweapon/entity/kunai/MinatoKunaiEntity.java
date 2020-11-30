package fr.sonkuun.shinobiweapon.entity.kunai;

import fr.sonkuun.shinobiweapon.listener.ShinobiWeaponPowerListener;
import fr.sonkuun.shinobiweapon.register.EntityTypeRegister;
import fr.sonkuun.shinobiweapon.register.ItemRegister;
import net.minecraft.client.renderer.Vector3d;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class MinatoKunaiEntity extends AbstractKunaiEntity {

	public MinatoKunaiEntity(World world, LivingEntity thrower, Vector3d startPosition,
			float apexYaw, float apexPitch, double velocity) {
		super(EntityTypeRegister.MINATO_KUNAI, world, thrower, startPosition, apexYaw, apexPitch, velocity);
		ShinobiWeaponPowerListener.MINATO_KUNAI_ENTITIES_MAP.put(this.getUniqueID().toString(), this);
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
	}

	@Override
	public void onRemovedFromWorld() {
		
		ShinobiWeaponPowerListener.MINATO_KUNAI_ENTITIES_MAP.remove(this.getUniqueID().toString());
		
		super.onRemovedFromWorld();
	}

	@Override
	public void tick() {

		if(!this.world.isRemote && this.thrower != null) {
			if(!ShinobiWeaponPowerListener.MINATO_KUNAI_ENTITIES_MAP.containsKey(this.getUniqueID().toString())) {
				ShinobiWeaponPowerListener.MINATO_KUNAI_ENTITIES_MAP.put(this.getUniqueID().toString(), this);
			}
		}

		super.tick();
	}

	@Override
	protected Item getDefaultItem() {
		return ItemRegister.MINATO_KUNAI;
	}
}