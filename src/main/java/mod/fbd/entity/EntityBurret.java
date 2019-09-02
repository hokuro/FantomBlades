package mod.fbd.entity;

import java.util.List;

import javax.annotation.Nullable;

import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.item.ItemBurret;
import mod.fbd.item.ItemBurret.EnumBurret;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceFluidMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityBurret extends Entity implements IEntityAdditionalSpawnData {
    private static final java.util.function.Predicate<Entity> ARROW_TARGETS = EntitySelectors.NOT_SPECTATING.and(EntitySelectors.IS_ALIVE.and(Entity::canBeCollidedWith));
   public static final String NAME = "burret";
   public static final int REGISTERID = 2;
    private int xTile;
    private int yTile;
    private int zTile;
    private Block inTile;
    private int inData;
    protected boolean inGround;
    protected int timeInGround;
    /** Seems to be some sort of timer for animating an arrow. */
    public int arrowShake;
    /** The owner of this arrow. */
    public Entity shootingEntity;
    private int ticksInGround;
    private int ticksInAir;


    private ItemStack burret;
    private EnumBurret burretType;
    private IBlockState inBlockState;

    public EntityBurret(World worldIn)
    {
        super(Mod_FantomBlade.RegistryEvents.BURRET, worldIn);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.setSize(0.5F, 0.5F);
        burretType = EnumBurret.NORMAL;
    }

    public EntityBurret(World worldIn, double x, double y, double z)
    {
        this(worldIn);
        this.setPosition(x, y, z);
    }

    public EntityBurret(World worldIn, EntityLivingBase shooter, ItemStack burretIn)
    {
        this(worldIn, shooter.posX, shooter.posY + (double)shooter.getEyeHeight() - 0.10000000149011612D, shooter.posZ);
        this.shootingEntity = shooter;
        this.burret = burretIn;
        burretType = ((ItemBurret)burret.getItem()).getBurret();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        double d0 = this.getBoundingBox().getAverageEdgeLength() * 10.0D;

        if (Double.isNaN(d0))
        {
            d0 = 1.0D;
        }

        d0 = d0 * 64.0D * getRenderDistanceWeight();
        return distance < d0 * d0;
    }

    @Override
    protected void registerData()
    {
    }

    public void shoot(Entity shooter, float pitch, float yaw, float p_184547_4_, float velocity, float inaccuracy)
    {
        float f = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        float f1 = -MathHelper.sin(pitch * 0.017453292F);
        float f2 = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        this.shoot((double)f, (double)f1, (double)f2, velocity, inaccuracy);
        this.motionX += shooter.motionX;
        this.motionZ += shooter.motionZ;

        if (!shooter.onGround)
        {
            this.motionY += shooter.motionY;
        }
    }

    public void shoot(double x, double y, double z, float velocity, float inaccuracy)
    {
        float f = MathHelper.sqrt(x * x + y * y + z * z);
        x = x / (double)f;
        y = y / (double)f;
        z = z / (double)f;
        x = x + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
        y = y + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
        z = z + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
        x = x * (double)velocity;
        y = y * (double)velocity;
        z = z * (double)velocity;
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
        float f1 = MathHelper.sqrt(x * x + z * z);
        this.rotationYaw = (float)(MathHelper.atan2(x, z) * (180D / Math.PI));
        this.rotationPitch = (float)(MathHelper.atan2(y, (double)f1) * (180D / Math.PI));
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
        this.ticksInGround = 0;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport)
    {
        this.setPosition(x, y, z);
        this.setRotation(yaw, pitch);
    }


    @Override
    @OnlyIn(Dist.CLIENT)
    public void setVelocity(double x, double y, double z)
    {
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt(x * x + z * z);
            this.rotationPitch = (float)(MathHelper.atan2(y, (double)f) * (180D / Math.PI));
            this.rotationYaw = (float)(MathHelper.atan2(x, z) * (180D / Math.PI));
            this.prevRotationPitch = this.rotationPitch;
            this.prevRotationYaw = this.rotationYaw;
            this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.ticksInGround = 0;
        }
    }

    @Override
    public void tick()
    {
        super.tick();

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * (180D / Math.PI));
            this.rotationPitch = (float)(MathHelper.atan2(this.motionY, (double)f) * (180D / Math.PI));
            this.prevRotationYaw = this.rotationYaw;
            this.prevRotationPitch = this.rotationPitch;
        }

        BlockPos blockpos = new BlockPos(this.xTile, this.yTile, this.zTile);
        IBlockState iblockstate = this.world.getBlockState(blockpos);
        Block block = iblockstate.getBlock();


        if (!iblockstate.isAir(this.world, blockpos) ) {
            VoxelShape voxelshape = iblockstate.getCollisionShape(this.world, blockpos);
            if (!voxelshape.isEmpty()) {
               for(AxisAlignedBB axisalignedbb : voxelshape.toBoundingBoxList()) {
                  if (axisalignedbb.offset(blockpos).contains(new Vec3d(this.posX, this.posY, this.posZ))) {
                     this.inGround = true;
                     break;
                  }
               }
            }
         }

        if (this.arrowShake > 0)
        {
            --this.arrowShake;
        }

        if (this.inGround)
        {
            if (this.inBlockState != iblockstate && this.world.isCollisionBoxesEmpty((Entity)null, this.getBoundingBox().grow(0.05D))) {
                this.inGround = false;
                this.motionX *= (double)(this.rand.nextFloat() * 0.2F);
                this.motionY *= (double)(this.rand.nextFloat() * 0.2F);
                this.motionZ *= (double)(this.rand.nextFloat() * 0.2F);
                this.ticksInGround = 0;
                this.ticksInAir = 0;
             } else {
                this.remove();
             }

             ++this.timeInGround;
        }
        else
        {
            this.timeInGround = 0;
            ++this.ticksInAir;
            Vec3d vec3d = new Vec3d(this.posX, this.posY, this.posZ);
            Vec3d vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            RayTraceResult raytraceresult = this.world.rayTraceBlocks(vec3d, vec3d1, RayTraceFluidMode.NEVER, true, false);
            vec3d = new Vec3d(this.posX, this.posY, this.posZ);
            vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            if (raytraceresult != null) {
               vec3d1 = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
            }

            Entity entity = this.findEntityOnPath(vec3d, vec3d1);
            if (entity != null) {
               raytraceresult = new RayTraceResult(entity);
            }

            if (raytraceresult != null && raytraceresult.entity instanceof EntityPlayer) {
               EntityPlayer entityplayer = (EntityPlayer)raytraceresult.entity;
               if (this.shootingEntity instanceof EntityPlayer && !((EntityPlayer)shootingEntity).canAttackPlayer(entityplayer)) {
                  raytraceresult = null;
               }
            }

            if (raytraceresult != null  && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
               this.onHit(raytraceresult);
               this.isAirBorne = true;
            }

            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            float f3 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * (double)(180F / (float)Math.PI));
            for(this.rotationPitch = (float)(MathHelper.atan2(this.motionY, (double)f3) * (double)(180F / (float)Math.PI)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
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
                  this.world.spawnParticle(Particles.BUBBLE, this.posX - this.motionX * 0.25D, this.posY - this.motionY * 0.25D, this.posZ - this.motionZ * 0.25D, this.motionX, this.motionY, this.motionZ);
               }

               f4 = 0.6F;
            }

            this.motionX *= (double)f4;
            this.motionY *= (double)f4;
            this.motionZ *= (double)f4;
            if (!this.hasNoGravity()) {
               this.motionY -= (double)0.05F;
            }

            this.setPosition(this.posX, this.posY, this.posZ);
            this.doBlockCollisions();
        }
    }

    /**
     * Called when the arrow hits a block or an entity
     */
    protected void onHit(RayTraceResult raytraceResultIn)
    {
    Entity entity  = raytraceResultIn.entity;
        if (entity != null) {
            float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
            int i = MathHelper.ceil((double)f * ((ItemBurret)burret.getItem()).getBurret().hitEntity(this.world, burret, entity, this.shootingEntity,this));

            DamageSource damagesource;

            if (this.shootingEntity instanceof EntityPlayer)
            {
                damagesource = DamageSource.causePlayerDamage((EntityPlayer) shootingEntity);
            }
            else
            {
                damagesource = DamageSource.ANVIL;
            }

            if (((ItemBurret)burret.getItem()).getBurret() == EnumBurret.DRAIN){
            if (entity instanceof EntityLivingBase){
            // 相手がアンデット系なら逆に体力を取られる
            if (((EntityLivingBase)entity).getCreatureAttribute() == CreatureAttribute.UNDEAD){
            i *= -1;
            }
            // ダメージ分回復
            this.shootingEntity.attackEntityFrom(DamageSource.CACTUS, i * -1);
            }
            }

            if (entity.attackEntityFrom(damagesource, (float)i))
            {
                if (entity instanceof EntityLivingBase)
                {
                    EntityLivingBase entitylivingbase = (EntityLivingBase)entity;

                    int knockbackStrength = ItemBurret.knockBackStrength(burret,this);
                    if ( knockbackStrength > 0)
                    {
                        float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

                        if (f1 > 0.0F)
                        {
                            entitylivingbase.addVelocity(this.motionX * (double)knockbackStrength * 0.6000000238418579D / (double)f1, 0.1D, this.motionZ * (double)knockbackStrength * 0.6000000238418579D / (double)f1);
                        }
                    }
                }

                this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));

                // フルメタルジャケットなら貫通するので消さない
                if (!ItemBurret.isFullMetal(burret))
                {
                    this.remove();
                }
            }
            else
            {
            // 透明化中の相手はスルー

            }
         } else {
             BlockPos blockpos = raytraceResultIn.getBlockPos();
             this.xTile = blockpos.getX();
             this.yTile = blockpos.getY();
             this.zTile = blockpos.getZ();
             IBlockState iblockstate = this.world.getBlockState(blockpos);
             this.inBlockState = iblockstate;
             this.motionX = (double)((float)(raytraceResultIn.hitVec.x - this.posX));
             this.motionY = (double)((float)(raytraceResultIn.hitVec.y - this.posY));
             this.motionZ = (double)((float)(raytraceResultIn.hitVec.z - this.posZ));
             float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ) * 20.0F;
             this.posX -= this.motionX / (double)f;
             this.posY -= this.motionY / (double)f;
             this.posZ -= this.motionZ / (double)f;
             this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
             this.inGround = true;
             this.arrowShake = 7;
             if (!iblockstate.isAir(world, blockpos)) {
                this.inBlockState.onEntityCollision(this.world, blockpos, this);
             }
         }
    }

    /**
     * Tries to move the entity towards the specified location.
     */
    public void move(MoverType type, double x, double y, double z)
    {
        super.move(type, x, y, z);

        if (this.inGround)
        {
            this.xTile = MathHelper.floor(this.posX);
            this.yTile = MathHelper.floor(this.posY);
            this.zTile = MathHelper.floor(this.posZ);
        }
    }

    @Nullable
    protected Entity findEntityOnPath(Vec3d start, Vec3d end)
    {
        Entity entity = null;
        List<Entity> list = this.world.getEntitiesInAABBexcluding(this, this.getBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow(1.0D), ARROW_TARGETS);
        double d0 = 0.0D;

        for(int i = 0; i < list.size(); ++i) {
           Entity entity1 = list.get(i);
           if (entity1 != this.shootingEntity || this.ticksInAir >= 5) {
              AxisAlignedBB axisalignedbb = entity1.getBoundingBox().grow((double)0.3F);
              RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(start, end);
              if (raytraceresult != null) {
                 double d1 = start.squareDistanceTo(raytraceresult.hitVec);
                 if (d1 < d0 || d0 == 0.0D) {
                    entity = entity1;
                    d0 = d1;
                 }
              }
           }
        }
        return entity;
    }

    @Override
    public void writeAdditional(NBTTagCompound compound)
    {
        compound.setInt("xTile", this.xTile);
        compound.setInt("yTile", this.yTile);
        compound.setInt("zTile", this.zTile);
        compound.setShort("life", (short)this.ticksInGround);
        if (this.inBlockState != null) {
           compound.setTag("inBlockState", NBTUtil.writeBlockState(this.inBlockState));
        }
        compound.setByte("inData", (byte)this.inData);
        compound.setByte("shake", (byte)this.arrowShake);
        compound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
        compound.setTag("burret", burret.write(new NBTTagCompound()));
        compound.setInt("burretType", burretType.getIndex());
    }

    @Override
    public void readAdditional(NBTTagCompound compound) {
        this.xTile = compound.getInt("xTile");
        this.yTile = compound.getInt("yTile");
        this.zTile = compound.getInt("zTile");
        this.ticksInGround = compound.getShort("life");
        if (compound.contains("inBlockState", 10)) {
           this.inBlockState = NBTUtil.readBlockState(compound.getCompound("inBlockState"));
        }

        this.inData = compound.getByte("inData") & 255;
        this.arrowShake = compound.getByte("shake") & 255;
        this.inGround = compound.getByte("inGround") == 1;
        this.burret = ItemStack.read((NBTTagCompound) compound.getCompound("burret"));
        burretType =  EnumBurret.getFromIndex(compound.getInt("burretType"));
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    public void onCollideWithPlayer(EntityPlayer entityIn)
    {
        if (!this.world.isRemote && this.inGround && this.arrowShake <= 0)
        {
                this.remove();
        }
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking()
    {
        return false;
    }

    public float getEyeHeight()
    {
        return 0.0F;
    }

    public ResourceLocation getTexture(){
    return burretType.getEntityTextrue();
    }

    @Override
    public void writeSpawnData(PacketBuffer data) {
    	data.writeInt(burretType.getIndex());
    }
    @Override
    public void readSpawnData(PacketBuffer data) {
    	burretType = EnumBurret.getFromIndex(data.readInt());
    }
}
