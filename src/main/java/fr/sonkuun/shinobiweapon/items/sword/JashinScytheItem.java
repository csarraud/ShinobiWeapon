package fr.sonkuun.shinobiweapon.items.sword;

import java.util.UUID;

import fr.sonkuun.shinobiweapon.ShinobiWeapon;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class JashinScytheItem extends AbstractSwordItem {

	public static int MAX_DAMAGE = 4;
	public static float ATTACK_DAMAGE = -3.0f;

	public UUID ritualEntityUUID;
	public boolean ritualIsReady;

	public JashinScytheItem() {
		super(MAX_DAMAGE, ATTACK_DAMAGE);

		this.ritualEntityUUID = null;
		this.ritualIsReady = false;
		
		int TICKS_IN_ONE_SECOND = 20 * 2;
		
		this.firstPowerCooldownInTicks = 10 * TICKS_IN_ONE_SECOND;
		this.secondPowerCooldownInTicks = 30 * TICKS_IN_ONE_SECOND;
	}

	@Override
	public boolean useFirstPower(PlayerEntity player) {
		if(this.ritualIsReady) {
			return false;
		}

		this.ritualIsReady = true;
		
		this.firstPowerLastUseInTicks = 0;
		return true;
	}

	@Override
	public boolean useSecondPower(PlayerEntity player) {
		if(this.ritualEntityUUID == null || !(player.world instanceof ServerWorld)) {
			return false;
		}
		
		ServerWorld world = (ServerWorld) player.world;
		LivingEntity ritualEntity = (LivingEntity) world.getEntityByUuid(ritualEntityUUID);

		if(ritualEntity != null && player.getHealth() > (player.getMaxHealth() / 10)) {
			/* After using this power, the player will be set to 10% of his max health */
			float healthAfter = player.getMaxHealth() / 10;
			
			float damage = (player.getHealth() - healthAfter) * 2;

			ritualEntity.attackEntityFrom(DamageSource.MAGIC, damage);
			player.setHealth(healthAfter);

			if(!ritualEntity.isAlive()) {
				ritualEntityUUID = null;
				ritualIsReady = false;
			}
			
			this.secondPowerLastUseInTicks = 0;
		}
		else {
			return false;
		}
		
		return true;
	}

	public void proceedRitual(LivingDamageEvent event) {
		ServerWorld world = (ServerWorld) event.getEntity().world;
		LivingEntity ritualEntity = (LivingEntity) world.getEntityByUuid(ritualEntityUUID);

		if(ritualEntity == null) {
			ritualEntityUUID = null;
			ritualIsReady = false;
			return;
		}

		ritualEntity.attackEntityFrom(DamageSource.MAGIC, event.getAmount());

		if(!ritualEntity.isAlive()) {
			ritualEntityUUID = null;
			ritualIsReady = false;
		}
	}

	@Override
	public void updatePowerTicks() {
		if(this.ritualIsReady && this.firstPowerLastUseInTicks >= this.firstPowerCooldownInTicks) {
			this.ritualIsReady = false;
		}
		super.updatePowerTicks();
	}

	@Override
	public boolean canUseFirstPower() {
		return this.firstPowerLastUseInTicks >= this.firstPowerCooldownInTicks
				&& this.ritualEntityUUID != null;
	}

	@Override
	public boolean canUseSecondPower() {
		return this.secondPowerLastUseInTicks >= this.secondPowerCooldownInTicks
				&& this.ritualIsReady;
	}

	@Override
	public void livingEntityDamagedPlayer(LivingDamageEvent event) {
		if(ritualEntityUUID == null || !ritualIsReady
				|| !(event.getEntity().world instanceof ServerWorld)) {
			return;
		}

		proceedRitual(event);
	}

	@Override
	public void environmentDamagedPlayer(LivingDamageEvent event) {
		if(ritualEntityUUID == null || !ritualIsReady
				|| !(event.getEntity().world instanceof ServerWorld)) {
			return;
		}

		proceedRitual(event);
	}
	
	@Override
	public void playerDamagedLivingEntity(LivingDamageEvent event) {
		LivingEntity entity = event.getEntityLiving();
		ritualEntityUUID = entity.getUniqueID();
	}

	@Override
	public ResourceLocation getFirstPowerHUDTexture() {
		return new ResourceLocation(ShinobiWeapon.MODID, "textures/hud/power/jashin/jashin_ritual.png");
	}

	@Override
	public ResourceLocation getSecondPowerHUDTexture() {
		return new ResourceLocation(ShinobiWeapon.MODID, "textures/hud/power/jashin/hara_kiri.png");
	}
}
