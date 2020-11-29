package fr.sonkuun.shinobiweapon.entity.kunai;

import java.util.UUID;

import javax.annotation.Nonnull;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.Vector3d;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class AbstractKunaiEntity extends ProjectileItemEntity implements IEntityAdditionalSpawnData {

	public LivingEntity thrower;
	public UUID throwerUUID;
	private float damage;
	private boolean isStuckOnBlock;
	private BlockPos stuckedBlockPosition;
	private int ticksOnBlock;

	private Entity ignoreEntity;
	private int ignoreTime;

	public AbstractKunaiEntity(EntityType<? extends ProjectileItemEntity> type, World world,
			LivingEntity thrower, Vector3d startPosition,
			float apexYaw, float apexPitch, double velocity) {
		this(type, thrower, world);

		this.setPosition(startPosition.x, startPosition.y, startPosition.z);

		this.throwerUUID = thrower.getUniqueID();
		this.thrower = thrower;

		Vec3d look = thrower.getLookVec().mul(new Vec3d(velocity, velocity, velocity));
		this.setVelocity(look.x, look.y, look.z);

		this.damage = 10.0f;
		this.isStuckOnBlock = false;
	}

	public AbstractKunaiEntity(EntityType<? extends ProjectileItemEntity> type, LivingEntity thrower, World world) {
		super(type, thrower, world);
	}


	public AbstractKunaiEntity(EntityType<? extends ProjectileItemEntity> type, World world) {
		super(type, world);
	}

	// If you forget to override this method, the default vanilla method will be called.
	// This sends a vanilla spawn packet, which is then silently discarded when it reaches the client.
	//  Your entity will be present on the server and can cause effects, but the client will not have a copy of the entity
	//    and hence it will not render.
	@Nonnull
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public void onCollideWithPlayer(PlayerEntity player) {
		if(this.world.isRemote)
			return;

		if(isStuckOnBlock) {
			for(ItemStack stack : player.inventory.mainInventory) {
				if(stack.getItem().equals(this.getDefaultItem()) && stack.getCount() < stack.getMaxStackSize()) {
					stack.setCount(stack.getCount() + 1);
					this.remove();
					break;
				}
			}

			if(!this.removed && player.inventory.getFirstEmptyStack() != -1) {
				player.inventory.add(player.inventory.getFirstEmptyStack(), new ItemStack(getDefaultItem()));
				this.remove();
			}
		}
	}

	private final int TICKS_PER_SECOND = 20;
	private final int TICKS_PER_MINUTE = TICKS_PER_SECOND * 60;
	private final int TICKS_IN_FIVE_MINUTES = TICKS_PER_MINUTE * 5;

	public void tick() {

		if (!this.world.isRemote) {

			if(this.isStuckOnBlock) {

				if(this.ticksOnBlock >= TICKS_IN_FIVE_MINUTES) {
					this.remove();
				}
				this.ticksOnBlock++;

				if(this.world.getBlockState(stuckedBlockPosition).getMaterial().equals(Material.AIR)) {
					this.isStuckOnBlock = false;
					this.setNoGravity(false);
				}
				
				if(this.throwerUUID != null && this.thrower == null) {
					this.thrower = this.world.getPlayerByUuid(this.throwerUUID);
				}
			}
		}

		super.tick();
	}

	@Override
	protected void onImpact(RayTraceResult result) {

		result = getRealRayTraceResult();

		if(!world.isRemote) {
			if (result.getType() == RayTraceResult.Type.ENTITY && !this.inGround) {
				EntityRayTraceResult entityRayTraceResult = (EntityRayTraceResult) result;
				Entity entity = entityRayTraceResult.getEntity();

				if(entity instanceof LivingEntity) {
					LivingEntity livingEntity = (LivingEntity) entity;
					livingEntity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), damage);
					this.remove();
				}

			}
			else if(result.getType() == RayTraceResult.Type.BLOCK) {
				this.setVelocity(0, 0, 0);
				this.setNoGravity(true);
				this.isStuckOnBlock = true;
				this.stuckedBlockPosition = ((BlockRayTraceResult)result).getPos();
				this.ticksOnBlock = 0;
			}
		}
	}

	/*
	 * The current RayTraceResult is obtained with a GROWTH_COEF = 1.0D
	 * on the ThrowableEntity class. This is too much for a kunai.
	 */
	public RayTraceResult getRealRayTraceResult() {
		double GROWTH_COEF = 0.1D;
		AxisAlignedBB axisalignedbb = this.getBoundingBox().expand(this.getMotion()).grow(GROWTH_COEF);

		for(Entity entity : this.world.getEntitiesInAABBexcluding(this, axisalignedbb, (entity) -> {
			return !entity.isSpectator() && entity.canBeCollidedWith();
		})) {
			if (entity == this.ignoreEntity) {
				++this.ignoreTime;
				break;
			}

			if (this.owner != null && this.ticksExisted < 2 && this.ignoreEntity == null) {
				this.ignoreEntity = entity;
				this.ignoreTime = 3;
				break;
			}
		}

		RayTraceResult raytraceresult = ProjectileHelper.rayTrace(this, axisalignedbb, (entity) -> {
			return !entity.isSpectator() && entity.canBeCollidedWith() && entity != this.ignoreEntity;
		}, RayTraceContext.BlockMode.OUTLINE, true);

		return raytraceresult;
	}

	private final String IS_STUCK_ON_BLOCK_NBT = "is_stuck_on_block";
	private final String STUCKED_BLOCK_POS_X_NBT = "stucked_block_pos_x";
	private final String STUCKED_BLOCK_POS_Y_NBT = "stucked_block_pos_y";
	private final String STUCKED_BLOCK_POS_Z_NBT = "stucked_block_pos_z";
	private final String TICKS_ON_BLOCK_NBT = "ticks_on_block";
	private final String THROWER_UUID_NBT = "thrower_uuid";

	@Override
	public void readAdditional(CompoundNBT compound) {
		this.isStuckOnBlock = compound.getBoolean(IS_STUCK_ON_BLOCK_NBT);
		this.stuckedBlockPosition = new BlockPos(
				compound.getDouble(STUCKED_BLOCK_POS_X_NBT),
				compound.getDouble(STUCKED_BLOCK_POS_Y_NBT),
				compound.getDouble(STUCKED_BLOCK_POS_Z_NBT));
		this.ticksOnBlock = compound.getInt(TICKS_ON_BLOCK_NBT);

		this.throwerUUID = compound.getUniqueId(THROWER_UUID_NBT);
		this.thrower = this.world.getPlayerByUuid(this.throwerUUID);

		super.readAdditional(compound);
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
		
		compound.putBoolean(IS_STUCK_ON_BLOCK_NBT, isStuckOnBlock);
		compound.putDouble(STUCKED_BLOCK_POS_X_NBT, stuckedBlockPosition.getX());
		compound.putDouble(STUCKED_BLOCK_POS_Y_NBT, stuckedBlockPosition.getY());
		compound.putDouble(STUCKED_BLOCK_POS_Z_NBT, stuckedBlockPosition.getZ());
		compound.putInt(TICKS_ON_BLOCK_NBT, ticksOnBlock);
		
		if(this.throwerUUID != null) {
			compound.putUniqueId(THROWER_UUID_NBT, this.throwerUUID);
		}

		super.writeAdditional(compound);
	}

	@Override
	public void writeSpawnData(PacketBuffer buffer) {
		// TODO Auto-generated method stub
	}

	@Override
	public void readSpawnData(PacketBuffer additionalData) {
		// TODO Auto-generated method stub
	}
}
