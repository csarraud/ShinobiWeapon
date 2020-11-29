package fr.sonkuun.shinobiweapon.entity.shuriken;

import fr.sonkuun.shinobiweapon.register.EntityTypeRegister;
import fr.sonkuun.shinobiweapon.register.ItemRegister;
import net.minecraft.client.renderer.Vector3d;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class ShurikenEntity extends AbstractShurikenEntity {

	public ShurikenEntity(World world, LivingEntity thrower, Vector3d startPosition,
			float apexYaw, float apexPitch, double velocity) {
		super(EntityTypeRegister.SHURIKEN, world, thrower, startPosition, apexYaw, apexPitch, velocity);
	}
	
	public ShurikenEntity(EntityType<? extends ProjectileItemEntity> type, World world) {
		super(type, world);
	}

	public ShurikenEntity(World world) {
		super(EntityTypeRegister.SHURIKEN, world);
	}

	@Override
	protected Item getDefaultItem() {
		return ItemRegister.SHURIKEN;
	}

}
