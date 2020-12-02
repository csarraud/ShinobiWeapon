package fr.sonkuun.shinobiweapon.items.sword;

import java.util.UUID;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
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
	}
	
	@Override
	public boolean useFirstPower(PlayerEntity player) {
		this.ritualIsReady = true;
		return true;
	}

	@Override
	public boolean useSecondPower(PlayerEntity player) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void livingEntityDamagedPlayer(LivingDamageEvent event) {
		if(ritualEntityUUID == null || !ritualIsReady
				|| !(event.getEntity().world instanceof ServerWorld)) {
			return;
		}
		
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
	public void playerDamagedLivingEntity(LivingDamageEvent event) {
		LivingEntity entity = event.getEntityLiving();
		ritualEntityUUID = entity.getUniqueID();
	}
}
