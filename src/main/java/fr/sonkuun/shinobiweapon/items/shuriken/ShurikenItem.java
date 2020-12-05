package fr.sonkuun.shinobiweapon.items.shuriken;

import fr.sonkuun.shinobiweapon.entity.shuriken.AbstractShurikenEntity;
import fr.sonkuun.shinobiweapon.entity.shuriken.ShurikenEntity;
import fr.sonkuun.shinobiweapon.register.ItemRegister;
import net.minecraft.client.renderer.Vector3d;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ShurikenItem extends AbstractShurikenItem {

	@Override
	public boolean useFirstPower(PlayerEntity player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean useSecondPower(PlayerEntity player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AbstractShurikenEntity createShurikenEntity(World world, PlayerEntity thrower, Vector3d startPosition,
			float rotationYaw, float rotationPitch, double velocity) {
		return new ShurikenEntity(world, thrower, startPosition, rotationYaw, rotationPitch, velocity);
	}

	@Override
	public Item getItem() {
		return ItemRegister.SHURIKEN;
	}

	@Override
	public ResourceLocation getFirstPowerHUDTexture() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResourceLocation getSecondPowerHUDTexture() {
		// TODO Auto-generated method stub
		return null;
	}
}
