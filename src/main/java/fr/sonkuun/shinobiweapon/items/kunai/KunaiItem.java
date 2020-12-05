package fr.sonkuun.shinobiweapon.items.kunai;

import fr.sonkuun.shinobiweapon.entity.kunai.AbstractKunaiEntity;
import fr.sonkuun.shinobiweapon.entity.kunai.KunaiEntity;
import fr.sonkuun.shinobiweapon.register.ItemRegister;
import net.minecraft.client.renderer.Vector3d;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class KunaiItem extends AbstractKunaiItem {

	public KunaiItem() {
		super();
	}

	@Override
	public AbstractKunaiEntity createKunaiEntity(World world, PlayerEntity thrower, Vector3d startPosition,
			float rotationYaw, float rotationPitch, double velocity) {
		return new KunaiEntity(world, thrower, startPosition, rotationYaw, rotationPitch, velocity);
	}

	@Override
	public Item getItem() {
		return ItemRegister.KUNAI;
	}

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
