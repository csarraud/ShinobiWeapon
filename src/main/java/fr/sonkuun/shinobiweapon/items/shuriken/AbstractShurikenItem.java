package fr.sonkuun.shinobiweapon.items.shuriken;

import java.util.Random;
import fr.sonkuun.shinobiweapon.entity.shuriken.AbstractShurikenEntity;
import fr.sonkuun.shinobiweapon.items.IPoweredItem;
import net.minecraft.client.renderer.Vector3d;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public abstract class AbstractShurikenItem extends Item implements IPoweredItem {

	public long firstPowerLastUseInTicks;
	public long secondPowerLastUseInTicks;
	
	public long firstPowerCooldownInTicks;
	public long secondPowerCooldownInTicks;
	
	public AbstractShurikenItem() {
		super(new Properties().group(ItemGroup.COMBAT));

		this.firstPowerLastUseInTicks = 0;
		this.secondPowerLastUseInTicks = 0;
		
		this.firstPowerCooldownInTicks = 0;
		this.secondPowerCooldownInTicks = 0;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);

		if(!playerIn.isCreative())
			stack.shrink(1);

		worldIn.playSound((PlayerEntity)null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(),
				SoundEvents.BLOCK_ANVIL_FALL, SoundCategory.NEUTRAL, 0.5F, 0.4F / (new Random().nextFloat() * 0.4F + 0.8F));
		playerIn.getCooldownTracker().setCooldown(this, 20);

		if(!worldIn.isRemote) {
			final double OFFSET_FROM_PLAYER_EYE = -0.11;
			Vector3d startPosition = new Vector3d(playerIn.getPosX(), playerIn.getPosYEye() + OFFSET_FROM_PLAYER_EYE, playerIn.getPosZ());
			double velocity = 1.5;
			
			AbstractShurikenEntity shuriken = createShurikenEntity(worldIn, playerIn, startPosition,
					playerIn.rotationYaw, playerIn.rotationPitch, velocity);
			worldIn.addEntity(shuriken);
		}
		
		playerIn.addStat(Stats.ITEM_USED.get(getItem()));
		return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
	}

	@Override
	public void updatePowerTicks() {
		this.firstPowerLastUseInTicks++;
		this.secondPowerLastUseInTicks++;		
	}

	@Override
	public boolean canUseFirstPower() {
		return this.firstPowerLastUseInTicks >= this.firstPowerCooldownInTicks;
	}

	@Override
	public boolean canUseSecondPower() {
		return this.secondPowerLastUseInTicks >= this.secondPowerCooldownInTicks;
	}
	
	@Override
	public void playerDamagedLivingEntity(LivingDamageEvent event) {
		
	}
	
	@Override
	public void livingEntityDamagedPlayer(LivingDamageEvent event) {
		
	}
	
	@Override
	public void environmentDamagedPlayer(LivingDamageEvent event) {
		
	}

	public abstract AbstractShurikenEntity createShurikenEntity(World world, PlayerEntity thrower,
			Vector3d startPosition, float rotationYaw, float rotationPitch, double velocity);
	
	public abstract Item getItem();
}