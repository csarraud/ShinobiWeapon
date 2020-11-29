package fr.sonkuun.shinobiweapon.register;

import java.util.ArrayList;
import java.util.List;

import fr.sonkuun.shinobiweapon.ShinobiWeapon;
import fr.sonkuun.shinobiweapon.entity.kunai.KunaiEntity;
import fr.sonkuun.shinobiweapon.entity.kunai.MinatoKunaiEntity;
import fr.sonkuun.shinobiweapon.entity.shuriken.ShurikenEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class EntityTypeRegister {

	public static final List<EntityType<?>> ENTITY_TYPES = new ArrayList<EntityType<?>>();

	public static final EntityType<KunaiEntity> KUNAI = EntityType.Builder.<KunaiEntity>create(KunaiEntity::new, EntityClassification.MISC)
			.size(0.4f, 0.4f)
			.setShouldReceiveVelocityUpdates(true)
			.setTrackingRange(20)
			.setCustomClientFactory((spawnEntity, world) -> new KunaiEntity(world))
			.build(new ResourceLocation(ShinobiWeapon.MODID, "kunai").toString());
	
	public static final EntityType<MinatoKunaiEntity> MINATO_KUNAI = EntityType.Builder.<MinatoKunaiEntity>create(MinatoKunaiEntity::new, EntityClassification.MISC)
			.size(0.4f, 0.4f)
			.setShouldReceiveVelocityUpdates(true)
			.setTrackingRange(20)
			.setCustomClientFactory((spawnEntity, world) -> new MinatoKunaiEntity(world))
			.build(new ResourceLocation(ShinobiWeapon.MODID, "minato_kunai").toString());
	
	public static final EntityType<ShurikenEntity> SHURIKEN = EntityType.Builder.<ShurikenEntity>create(ShurikenEntity::new, EntityClassification.MISC)
			.size(0.4f, 0.4f)
			.setShouldReceiveVelocityUpdates(true)
			.setTrackingRange(20)
			.setCustomClientFactory((spawnEntity, world) -> new ShurikenEntity(world))
			.build(new ResourceLocation(ShinobiWeapon.MODID, "shuriken").toString());

	static {
		KUNAI.setRegistryName(ShinobiWeapon.MODID, "kunai_entity");
		MINATO_KUNAI.setRegistryName(ShinobiWeapon.MODID, "minato_kunai_entity");
		SHURIKEN.setRegistryName(ShinobiWeapon.MODID, "shuriken_entity");

		ENTITY_TYPES.add(KUNAI);
		ENTITY_TYPES.add(MINATO_KUNAI);
		ENTITY_TYPES.add(SHURIKEN);
	}

	public static void init() {
		
	}

	@SubscribeEvent
	public static void registerTypes(final RegistryEvent.Register<EntityType<?>> event) {

		for(final EntityType<?> type : ENTITY_TYPES) {
			event.getRegistry().register(type);
		}
	}
}
