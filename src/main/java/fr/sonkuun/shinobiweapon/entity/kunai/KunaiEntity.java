package fr.sonkuun.shinobiweapon.entity.kunai;

import fr.sonkuun.shinobiweapon.register.EntityTypeRegister;
import fr.sonkuun.shinobiweapon.register.ItemRegister;
import net.minecraft.client.renderer.Vector3d;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class KunaiEntity extends AbstractKunaiEntity {

	public KunaiEntity(World world, LivingEntity thrower, Vector3d startPosition,
			float apexYaw, float apexPitch, double velocity) {
		super(EntityTypeRegister.KUNAI, world, thrower, startPosition, apexYaw, apexPitch, velocity);
	}
	
	public KunaiEntity(EntityType<? extends ProjectileItemEntity> type, World world) {
		super(type, world);
	}

	public KunaiEntity(World world) {
		super(EntityTypeRegister.KUNAI, world);
	}

	@Override
	protected Item getDefaultItem() {
		return ItemRegister.KUNAI;
	}
}
