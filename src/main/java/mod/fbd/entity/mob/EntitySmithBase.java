package mod.fbd.entity.mob;

import javax.annotation.Nullable;

import mod.fbd.core.SoundManager;
import mod.fbd.core.log.ModLog;
import mod.fbd.entity.ai.EntityAISmithTask;
import mod.fbd.entity.ai.EntityAIWatchClosestSmith;
import mod.fbd.inventory.InventorySmithBase;
import mod.fbd.resource.TextureInfo;
import mod.fbd.resource.TextureUtil;
import mod.fbd.util.ModUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.INPC;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class EntitySmithBase extends EntityElmBase implements INPC {

	public static final ResourceLocation TEXTURE_SMITH_DEFAULT = new ResourceLocation("fbd","textures/entity/bladesmith_default.png");
	public static final int MAX_LEVEL = 255;
	/**************************************************************/
	/** DataWatcher                                              **/
	/**************************************************************/
	// テクスチャー
	protected String ddtexture;
	protected static final DataParameter<String> DW_TEXTURE = EntityDataManager.<String>createKey(EntityElmBase.class, DataSerializers.STRING);
	public void Dw_Texture(String value){
		this.dataManager.set(DW_TEXTURE, value);
	}

	public String Dw_Texture(){
		return this.dataManager.get(DW_TEXTURE);
	}

	// 作業状況
	protected static final DataParameter<Integer> DW_WORKTIMER = EntityDataManager.<Integer>createKey(EntityElmBase.class, DataSerializers.VARINT);
	public void Dw_WORKTIMER(int value){
		this.dataManager.set(DW_WORKTIMER, value);
	}

	public int Dw_WORKTIMER(){
		return this.dataManager.get(DW_WORKTIMER);
	}
	public void addWorkTimeValue(int value){
		Dw_WORKTIMER(Dw_WORKTIMER()+value);
	}

	// 作業状態
	protected static final DataParameter<Boolean> DW_ISWORK = EntityDataManager.<Boolean>createKey(EntityElmBase.class, DataSerializers.BOOLEAN);
	public void Dw_ISWORK(boolean value){
		this.dataManager.set(DW_ISWORK, value);
	}

	public boolean Dw_ISWORK(){
		return this.dataManager.get(DW_ISWORK);
	}

	// 腕ふり
	protected static final DataParameter<Integer> DW_SWING = EntityDataManager.<Integer>createKey(EntityElmBase.class, DataSerializers.VARINT);
	public void Dw_Swing(int value){
		this.dataManager.set(DW_SWING, value);
	}

	public int Dw_Swing() {
		return this.dataManager.get(DW_SWING);
	}
	public void addSwingValue(int value){
		Dw_Swing(Dw_Swing()+value);
	}


	public EntitySmithBase(EntityType<? extends CreatureEntity> etype, World worldIn) {
		super(etype, worldIn);
		// TODO 自動生成されたコンストラクター・スタブ
	}

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(DW_TEXTURE,"");
        this.dataManager.register(DW_ISWORK,false);
        this.dataManager.register(DW_WORKTIMER,0);
        this.dataManager.register(DW_SWING,0);
    }

	@Override
    protected void registerGoals() {
		// AI登録
		super.registerGoals();
    	this.goalSelector.addGoal(0, new EntityAIWatchClosestSmith(this, PlayerEntity.class, 64));
    	this.goalSelector.addGoal(0, new EntityAISmithTask(this));
    }

    private int soundCount = 100;
    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        if (this.Dw_ISWORK()){
        	soundCount++;
        	if (soundCount > 80){
        		if (ModUtil.random(100) < 80){
        			playSound(SoundManager.sound_smith_work, this.getSoundVolume(), this.getSoundPitch());
        			soundCount = 0;
        		}
        	}
        }else{
        	soundCount = 100;
        }
    }

    @Override
    public void tick() {
    	super.tick();
        if (this.Dw_ISWORK()){
	    	BlockPos summonPos = Dw_SUMMONPOS();
	    	if (!this.getPos().equals(summonPos)){
	    		// 位置ずれしている場合召喚位置へテレポートしない
	    		if (!(summonPos == null)){
	    			//this.setPosition(summonPos.getX()+0.5f, summonPos.getY(), summonPos.getZ()+0.5f);
	    			ModLog.log().debug("now:"+this.getPos().toString() + "   :  summon:"+summonPos.toString());
	    		}
	    	}
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("isWork", Dw_ISWORK());
        if (Dw_ISWORK()){
        	compound.putInt("workTimer", Dw_WORKTIMER());
        }else{
        	compound.putInt("workTimer", 0);
        }

        if (Dw_Texture() != ""){
        	compound.putString("texture", Dw_Texture());
        }
    }

    @Override
    public void readAdditional(CompoundNBT compound)
    {
        super.readAdditional(compound);
        Dw_ISWORK(compound.getBoolean("isWork"));
    	Dw_WORKTIMER(compound.getInt("workTimer"));

        if (compound.contains("texture")){
        	String file = compound.getString("texture");
        	setCustomResourceLocation(TextureUtil.TextureManager().getTexture(getSmithName(), file));
        }else{
        	setCustomResourceLocation(TextureUtil.TextureManager().getRandomTexture(getSmithName()));
        }
    }

    @Override
    public void knockBack(Entity entityIn, float strength, double xRatio, double zRatio) {
        // 作業中じゃなければノックバック
    	if (!this.Dw_ISWORK()){
        	super.knockBack(entityIn, strength, xRatio, zRatio);
        }
    }

    @Override
    public boolean startRiding(Entity entityIn, boolean force) {
    	boolean ret = false;
    	if (!this.Dw_ISWORK()){
    		ret = super.startRiding(entityIn, force);
    	}
    	return ret;
    }

    @Override
    public void applyEntityCollision(Entity entityIn){
    	if (!this.Dw_ISWORK()){
    		super.applyEntityCollision(entityIn);
    	}
    }

    @Override
    public int getVerticalFaceSpeed() {
        return 80;
    }

    @Override
    public int getHorizontalFaceSpeed() {
        return 90;
    }


    @Override
    public boolean canBePushed(){
    	boolean ret = true;
    	if (this.Dw_ISWORK()){
    		ret = false;
    	}
    	return ret;
    }

    @Override
    public boolean canDespawn(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
       return mod.fbd.core.SoundManager.sound_smith_hart;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return mod.fbd.core.SoundManager.sound_smith_damage;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return mod.fbd.core.SoundManager.sound_smith_dead;
    }


    @Override
    public void notifyDataManagerChange(DataParameter<?> key) {
        super.notifyDataManagerChange(key);
    }

    @Override
    public void setRevengeTarget(@Nullable LivingEntity livingBase)  {
        super.setRevengeTarget(livingBase);
    }

    @Override
    public void onDeath(DamageSource cause) {
        super.onDeath(cause);
    }

    public World getWorld() {
        return this.world;
    }

    public BlockPos getPos() {
        return new BlockPos(this);
    }

    @Override
    public float getEyeHeight(Pose pose) {
        return 1.62F;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 12) {
            this.spawnParticles(ParticleTypes.HEART);
        } else if (id == 13) {
            this.spawnParticles(ParticleTypes.ANGRY_VILLAGER);
        } else if (id == 14) {
            this.spawnParticles(ParticleTypes.HAPPY_VILLAGER);
        } else {
            super.handleStatusUpdate(id);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void spawnParticles(BasicParticleType particleType) {
        for (int i = 0; i < 5; ++i) {
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            double d2 = this.rand.nextGaussian() * 0.02D;
            this.world.addParticle(particleType, this.posX + (double)(this.rand.nextFloat() * this.getSize(Pose.STANDING).width * 2.0F) - (double)this.getSize(Pose.STANDING).width, this.posY + 1.0D + (double)(this.rand.nextFloat() * this.getSize(Pose.STANDING).height), this.posZ + (double)(this.rand.nextFloat() * this.getSize(Pose.STANDING).height * 2.0F) - (double)this.getSize(Pose.STANDING).width, d0, d1, d2);
        }
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData entityLivingData, @Nullable CompoundNBT itemNbt) {
    	return super.onInitialSpawn(worldIn, difficulty, reason, entityLivingData, itemNbt);
    }

    @Override
    public boolean canBeLeashedTo(PlayerEntity player) {
        return false;
    }

    @Override
    public void onStruckByLightning(LightningBoltEntity lightningBolt) {
        //雷にあたったときの処理
    }

    @Override
    protected void updateEquipmentIfNeeded(ItemEntity itemEntity) {
    	// エンティティが何か拾った時の処理
    }

    @Override
    public ITextComponent getDisplayName()
    {
        Team team = this.getTeam();
        ITextComponent s = this.getCustomName();

        if (s != null && !s.getFormattedText().isEmpty())
        {
            ITextComponent textcomponentstring = ScorePlayerTeam.formatMemberName(team, s);
            textcomponentstring.getStyle().setHoverEvent(this.getHoverEvent());
            textcomponentstring.getStyle().setInsertion(this.getCachedUniqueIdString());
            return textcomponentstring;
        }
        return  new StringTextComponent(this.getSmithName());
    }

    ResourceLocation dynamicTexture = null;
	public ResourceLocation getTexture(){
		TextureInfo tex = TextureUtil.TextureManager().getTexture(getSmithName(), Dw_Texture());
		if (tex != null && dynamicTexture == null) {
			dynamicTexture = Minecraft.getInstance().getTextureManager().getDynamicTextureLocation(this.getUniqueID() + "_texture",new DynamicTexture(tex.Image()));
		}
		return dynamicTexture != null?dynamicTexture:getDefaultTexture();
	}

	public void setCustomResourceLocation(TextureInfo txture ){
		if (txture != null){
			try {
				// 特製テクスチャを読み込む
				this.dataManager.set(DW_TEXTURE, txture.FileName());
			} catch (Exception e) {
				// 読み込めない場合デフォルトテクスチャを使用する]
			}
		}
	}

	private boolean textureUpdate = false;
	public boolean updateTexture() {
		return textureUpdate;
	}

	public void updateTexture(boolean flag) {
		textureUpdate = flag;
	}

	public void addExp(int nextExp) {
		int value = Dw_Exp() + nextExp;
		if (getMaxExp() < value){
			value = getMaxExp();
		}
		Dw_Exp(value);
	}

	public int getExpParcent() {
		if (getLevel() < MAX_LEVEL){
			return (Dw_Exp()*100)/getExpValue(getLevel()+1);
		}
		return 100;
	}

	public abstract boolean checkWork(World world, BlockPos pos);
    public abstract void finishWork(World world,BlockPos pos, boolean success);
	public abstract InventorySmithBase getInventory();
	public abstract boolean canStartSmith(World world, BlockPos checkPos, boolean b, EntitySmithBase smith);
	public abstract void startWork(World world, BlockPos pos, ItemStack result);
	public abstract ItemStack create();
	public abstract void repair();
	public abstract String getSmithName();
	public abstract int getMaxExp();
	public abstract int getExpValue(int level);
	public abstract ResourceLocation getDefaultTexture();
}
