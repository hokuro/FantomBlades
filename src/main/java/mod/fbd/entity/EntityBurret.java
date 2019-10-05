package mod.fbd.entity;

import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.item.guns.ItemBurret;
import mod.fbd.item.guns.ItemBurret.EnumBurret;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntityBurret extends AbstractArrowEntity implements IEntityAdditionalSpawnData{
	public static final String NAME = "burret";
	public static final int REGISTERID = 2;
	private int ticksInGround;
	private int ticksInAir;

    private ItemStack burret;
    private EnumBurret burretType;
    private BlockState inBlockState;

	public EntityBurret(FMLPlayMessages.SpawnEntity packet, World world) {
		this(Mod_FantomBlade.RegistryEvents.BURRET, world);
	}

    public EntityBurret(EntityType<? extends AbstractArrowEntity> type, World worldIn){
        super(type, worldIn);
        burretType = EnumBurret.NORMAL;
    }

    public EntityBurret(World worldIn, double x, double y, double z){
    	super(Mod_FantomBlade.RegistryEvents.BURRET, x, y, z, worldIn);
        burretType = EnumBurret.NORMAL;
    }

    public EntityBurret(World worldIn, LivingEntity shooter, ItemStack burretIn) {
    	super(Mod_FantomBlade.RegistryEvents.BURRET, shooter, worldIn);
        this.burret = burretIn;
        burretType = ((ItemBurret)burret.getItem()).getBurret();
        this.pickupStatus = AbstractArrowEntity.PickupStatus.DISALLOWED;
    }

    @Override
    protected void registerData(){
    	super.registerData();
    }

    @Override
    public void shoot(Entity shooter, float pitch, float yaw, float p_184547_4_, float velocity, float inaccuracy){
        super.shoot(shooter, pitch, yaw, p_184547_4_, velocity, inaccuracy);
    }

    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy){
    	super.shoot(x, y, z, velocity, inaccuracy);
        float f = MathHelper.sqrt(x * x + y * y + z * z);
    }

    @Override
    public void tick(){
    	if (!this.world.isRemote) {
    		this.setFlag(6, this.isGlowing());
    	}
    	this.baseTick();

        Vec3d vec3d = this.getMotion();
        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F){
            float f = MathHelper.sqrt(func_213296_b(vec3d));
            this.rotationYaw = (float)(MathHelper.atan2(vec3d.x, vec3d.z) * (180D / Math.PI));
            this.rotationPitch = (float)(MathHelper.atan2(vec3d.y, (double)f) * (180D / Math.PI));
            this.prevRotationYaw = this.rotationYaw;
            this.prevRotationPitch = this.rotationPitch;
        }

        BlockPos blockpos = new BlockPos(this.posX, this.posY, this.posZ);
        BlockState blockstate = this.world.getBlockState(blockpos);
        if (!blockstate.isAir(this.world, blockpos)) {
        	VoxelShape voxelshape = blockstate.getCollisionShape(this.world, blockpos);
        	if (!voxelshape.isEmpty()) {
        		for(AxisAlignedBB axisalignedbb : voxelshape.toBoundingBoxList()) {
        			if (axisalignedbb.offset(blockpos).contains(new Vec3d(this.posX, this.posY, this.posZ))) {
        				this.inGround = true;
        				break;
        			}
        		}
        	}
        }

        if (this.arrowShake > 0){
        	--this.arrowShake;
        }

        if (this.inGround) {
        	if (this.inBlockState != blockstate && this.world.areCollisionShapesEmpty(this.getBoundingBox().grow(0.06D))) {
                this.inGround = false;
                this.setMotion(vec3d.mul((double)(this.rand.nextFloat() * 0.2F), (double)(this.rand.nextFloat() * 0.2F), (double)(this.rand.nextFloat() * 0.2F)));
                this.ticksInGround = 0;
                this.ticksInAir = 0;
             } else if (!this.world.isRemote) {
                this.remove();
             }
             ++this.timeInGround;
        }
        else
        {
            this.timeInGround = 0;
            ++this.ticksInAir;
            Vec3d vec3d1 = new Vec3d(this.posX, this.posY, this.posZ);
            Vec3d vec3d2 =vec3d1.add(vec3d);
            RayTraceResult raytraceresult = this.world.rayTraceBlocks(new RayTraceContext(vec3d1, vec3d2, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
            if (raytraceresult.getType() != RayTraceResult.Type.MISS) {
            	vec3d2 = raytraceresult.getHitVec();
            }

            while(!this.removed) {
	            EntityRayTraceResult entityraytraceresult = this.func_213866_a(vec3d1, vec3d2);
	            if (entityraytraceresult != null) {
	            	raytraceresult = entityraytraceresult;
	            }
	            if (raytraceresult != null && raytraceresult.getType() == RayTraceResult.Type.ENTITY) {
	            	Entity entity = ((EntityRayTraceResult)raytraceresult).getEntity();
	            	Entity entity1 = this.getShooter();
	            	 if (entity instanceof PlayerEntity && entity1 instanceof PlayerEntity && !((PlayerEntity)entity1).canAttackPlayer((PlayerEntity)entity)) {
	            		 raytraceresult = null;
	            		 entityraytraceresult = null;
	            	 }
	            }
	            if (raytraceresult != null) {
	            	this.onHit(raytraceresult);
	            	this.isAirBorne = true;
	            }
	            if (entityraytraceresult == null || this.func_213874_s() <= 0) {
	            	break;
	            }
	            raytraceresult = null;
        	}

            vec3d = this.getMotion();
            double d1 = vec3d.x;
            double d2 = vec3d.y;
            double d0 = vec3d.z;
            if (this.getIsCritical()) {
            	for(int i = 0; i < 4; ++i) {
            		this.world.addParticle(ParticleTypes.CRIT, this.posX + d1 * (double)i / 4.0D, this.posY + d2 * (double)i / 4.0D, this.posZ + d0 * (double)i / 4.0D, -d1, -d2 + 0.2D, -d0);
            	}
            }

            this.posX += d1;
            this.posY += d2;
            this.posZ += d0;
            float f3 = MathHelper.sqrt(func_213296_b(vec3d));
            this.rotationYaw = (float)(MathHelper.atan2(d1, d0) * (double)(180F / (float)Math.PI));
            for(this.rotationPitch = (float)(MathHelper.atan2(d2, (double)f3) * (double)(180F / (float)Math.PI)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
               ;
            }

            while(this.rotationPitch - this.prevRotationPitch >= 180.0F) {
               this.prevRotationPitch += 360.0F;
            }

            while(this.rotationYaw - this.prevRotationYaw < -180.0F) {
               this.prevRotationYaw -= 360.0F;
            }

            while(this.rotationYaw - this.prevRotationYaw >= 180.0F) {
               this.prevRotationYaw += 360.0F;
            }

            this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
            this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
            float f4 = 0.99F;
            float f1 = 0.05F;
            if (this.isInWater()) {
               for(int i = 0; i < 4; ++i) {
                  float f2 = 0.25F;
                  this.world.addParticle(ParticleTypes.BUBBLE, this.posX - d1 * 0.25D, this.posY - d2 * 0.25D, this.posZ - d0 * 0.25D, d1, d2, d0);
               }

               f4 = this.getWaterDrag();
            }

            this.setMotion(vec3d.scale((double)f4));
            if (!this.hasNoGravity()) {
            	Vec3d vec3d3 = this.getMotion();
            	this.setMotion(vec3d3.x, vec3d3.y - (double)0.05F, vec3d3.z);
            }

            this.setPosition(this.posX, this.posY, this.posZ);
            this.doBlockCollisions();
        }
    }

    protected void tryDespawn() {
    	this.remove();
    }

    @Override
    protected void onHit(RayTraceResult raytraceResultIn) {
    	if (raytraceResultIn.getType() == RayTraceResult.Type.ENTITY) {
    		hitEntity((EntityRayTraceResult)raytraceResultIn);
    	}else if (raytraceResultIn.getType() == RayTraceResult.Type.BLOCK) {
    		hitBlock((BlockRayTraceResult)raytraceResultIn);
    	}
    }

    protected void hitEntity(EntityRayTraceResult raytraceResultIn) {
    	// iエンティティにヒット
    	Entity target  = raytraceResultIn.getEntity();
    	Entity shooter = this.getShooter();
    	Vec3d motion = this.getMotion();
        float f = MathHelper.sqrt(motion.x * motion.x + motion.y * motion.y + motion.z * motion.z);
        int i = MathHelper.ceil((double)f * ((ItemBurret)burret.getItem()).getBurret().hitEntity(this.world, burret, target, shooter,this));

        DamageSource damagesource;
		if (shooter == null) {
			// i撃ったのはモブ以外
			damagesource = DamageSource.causeArrowDamage(this, this);
		} else {
			// i撃ったのはモブ
			damagesource = DamageSource.causeArrowDamage(this, shooter);
			if (shooter instanceof LivingEntity) {
				((LivingEntity)shooter).setLastAttackedEntity(target);
			}
		}

		// iドレインバレット
        if (shooter != null && ((ItemBurret)burret.getItem()).getBurret() == EnumBurret.DRAIN){
        	if (target instanceof LivingEntity){
        		// i相手がアンデット系なら逆に体力を取られる
        		if (((LivingEntity)target).getCreatureAttribute() == CreatureAttribute.UNDEAD){
        			i *= -1;
        		}
        		// iダメージ分回復
        		shooter.attackEntityFrom(DamageSource.CACTUS, i * -1);
        	}
        }

        if (i < 0) {
        	// i回復
        	if (target instanceof LivingEntity) {
        		this.world.addParticle(ParticleTypes.HEART, target.posX, target.posY + 0.5, target.posZ, this.rand.nextDouble() + 1.0, this.rand.nextDouble() + 0.5, this.rand.nextDouble()+ 1.0);
        		((LivingEntity)target).heal(i * -1);
        	}
        }else {
	        // iターゲットにダメージ
	        if (target.attackEntityFrom(damagesource, (float)i)){
	            if (target instanceof LivingEntity){
	                LivingEntity entitylivingbase = (LivingEntity)target;

	                int knockbackStrength = ItemBurret.knockBackStrength(burret,this);
	                if ( knockbackStrength > 0){
	                    Vec3d vec3d = this.getMotion().mul(1.0D, 0.0D, 1.0D).normalize().scale((double)knockbackStrength * 0.6D);
	                    if (vec3d.lengthSquared() > 0.0D) {
	                    	entitylivingbase.addVelocity(vec3d.x, 0.1D, vec3d.z);
	                    }
	                }
	                // iエンチャントの久賀発動
	                if (!this.world.isRemote && shooter instanceof LivingEntity) {
	                	EnchantmentHelper.applyThornEnchantments(entitylivingbase, shooter);
	                	EnchantmentHelper.applyArthropodEnchantments((LivingEntity)shooter, entitylivingbase);
	                }
	            }

	            this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));

	            // iフルメタルジャケットなら貫通するので消さない
	            if (!ItemBurret.isFullMetal(burret)){
	                this.remove();
	            }
	        }else{
	        	// i透明化中の相手は無視
	        	if (!target.isInvisible()) {
	        		// iフルメタルジャケットなら貫通するので消さない
	        		if (!ItemBurret.isFullMetal(burret)){
	        			// i飛び道具無効の相手にあったった
	        			this.setMotion(this.getMotion().scale(-0.1D));
	        			this.rotationYaw += 180.0F;
	        			this.prevRotationYaw += 180.0F;
	        			//this.ticksInAir = 0;
	        			this.remove();
	        		}
	        	}
	        }
        }
    }

    protected void hitBlock(BlockRayTraceResult raytraceResultIn) {
    	BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult)raytraceResultIn;
    	BlockState blockstate = this.world.getBlockState(blockraytraceresult.getPos());
    	this.inBlockState = blockstate;
    	Vec3d vec3d = blockraytraceresult.getHitVec().subtract(this.posX, this.posY, this.posZ);
    	this.setMotion(vec3d);
    	Vec3d vec3d1 = vec3d.normalize().scale((double)0.05F);
    	this.posX -= vec3d1.x;
    	this.posY -= vec3d1.y;
    	this.posZ -= vec3d1.z;
    	this.playSound(this.getHitGroundSound(), 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
    	this.inGround = true;
    	this.arrowShake = 7;
        ((ItemBurret)burret.getItem()).getBurret().hitBlock(world, burret, this.getShooter(),this);
    	this.setIsCritical(false);
    	this.func_213872_b((byte)0);
    	this.setHitSound(SoundEvents.ENTITY_ARROW_HIT);
    	this.func_213865_o(false);
    	//this.func_213870_w();
    	blockstate.onProjectileCollision(this.world, blockstate, blockraytraceresult, this);
    }

    @Override
    public void writeAdditional(CompoundNBT compound){
    	super.writeAdditional(compound);
        compound.put("burret", burret.write(new CompoundNBT()));
        compound.putInt("burretType", burretType.getIndex());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
    	super.readAdditional(compound);
        this.burret = ItemStack.read((CompoundNBT) compound.getCompound("burret"));
        burretType =  EnumBurret.getFromIndex(compound.getInt("burretType"));
    }

    @Override
    public void onCollideWithPlayer(PlayerEntity entityIn){
        if (!this.world.isRemote && this.inGround && this.arrowShake <= 0){
        	this.remove();
        }
    }

    @Override
    protected boolean canTriggerWalking(){
        return false;
    }

    public ResourceLocation getTexture(){
    	return burretType.getEntityTextrue();
    }

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected ItemStack getArrowStack() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void writeSpawnData(PacketBuffer data) {
        data.writeItemStack(burret);
        data.writeInt(burretType.getIndex());
	}

	@Override
	public void readSpawnData(PacketBuffer data) {
		burret = data.readItemStack();
		burretType = EnumBurret.getFromIndex(data.readInt());
	}
}
