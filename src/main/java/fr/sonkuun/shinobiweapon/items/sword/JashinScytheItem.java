package fr.sonkuun.shinobiweapon.items.sword;

import java.util.UUID;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class JashinScytheItem extends AbstractSwordItem {

	public static int MAX_DAMAGE = 4;
	public static float ATTACK_DAMAGE = -3.0f;
	
	public UUID ritualEntityUUID;
	
	public JashinScytheItem() {
		super(MAX_DAMAGE, ATTACK_DAMAGE);
		
		this.ritualEntityUUID = null;
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
	public void damageLivingEntity(LivingDamageEvent event) {
		LivingEntity entity = event.getEntityLiving();
		ritualEntityUUID = entity.getUniqueID();
	}
}
