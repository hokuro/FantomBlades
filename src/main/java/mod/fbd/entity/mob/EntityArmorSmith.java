package mod.fbd.entity.mob;

import javax.annotation.Nullable;

import mod.fbd.block.BlockCore;
import mod.fbd.block.BlockDummyAnvil;
import mod.fbd.block.BlockDummyFurnce;
import mod.fbd.core.ModCommon;
import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.core.SoundManager;
import mod.fbd.core.log.ModLog;
import mod.fbd.entity.ai.EntityAISmithTask;
import mod.fbd.entity.ai.EntityAIWatchClosestSmith;
import mod.fbd.intaractionobject.IntaractionObjectArmorSmith;
import mod.fbd.inventory.InventoryArmorSmith;
import mod.fbd.item.ItemBladePiece;
import mod.fbd.item.ItemBladePiece.EnumBladePieceType;
import mod.fbd.item.ItemCore;
import mod.fbd.resource.TextureInfo;
import mod.fbd.util.ModUtil;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.INpc;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.Particles;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntityArmorSmith  extends EntitySmithBase implements INpc
{
	public static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation("fbd","textures/entity/armorsmith_default.png");
	public static final String NAME= "armorsmith";
	public static final int REGISTERID = 2;
	public static final ResourceLocation loot = new ResourceLocation("fbd:"+NAME);
	public static final int MAX_LEVEL = 255;

	private int randomTickDivider;
    private final InventoryArmorSmith smithInventory;
    private int nextExp=0;


    public EntityArmorSmith(World worldIn)
    {
        super(Mod_FantomBlade.RegistryEvents.ARMORSMITH,worldIn);
        this.smithInventory = new InventoryArmorSmith();
        this.setSize(0.6F, 1.95F);
    }

    @Override
    protected void initEntityAI()
    {
    	this.tasks.addTask(0, new EntityAIWatchClosestSmith(this, EntityPlayer.class,64));
    	this.tasks.addTask(0, new EntityAISmithTask(this));
    }

    @Override
    protected void registerAttributes()
    {
        super.registerAttributes();
    }

    private int soundCount = 100;
    @Override
    protected void updateAITasks()
    {
        super.updateAITasks();
        if (this.Dw_ISWORK()){
        	soundCount++;
        	if (soundCount > 80){
        		if (ModUtil.random(100) < 80){
        			playSound(SoundManager.sound_bladesmith_work, this.getSoundVolume(), this.getSoundPitch());
        			soundCount = 0;
        		}
        	}
        }else{
        	soundCount = 100;
        }

    }

    @Override
    public void tick()
    {
    	super.tick();

    	BlockPos summonPos = Dw_SUMMONPOS();
    	if (!this.getPos().equals(summonPos)){
    		if (!(summonPos == null)){
    			this.setPosition(summonPos.getX()+0.5f, summonPos.getY(), summonPos.getZ()+0.5f);
    			ModLog.log().debug("now:"+this.getPos().toString() + "   :  summon:"+summonPos.toString());
    		}
    	}
    }


    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
    	if (!player.world.isRemote){
        	if (!Dw_ISWORK()){
        		ModLog.log().debug("open gui entity id:"+this.getEntityId());
        		NetworkHooks.openGui((EntityPlayerMP)player,
            			new IntaractionObjectArmorSmith(this.getEntityId()),
            			(buf)->{
    						buf.writeInt(this.getEntityId());
    					});
            	//player.openGui(Mod_FantomBlade.instance, ModGui.GUI_ID_ARMORSMITH + this.getEntityId(), world, this.getPos().getX(), this.getPos().getY(), this.getPos().getZ());
        	}
    	}
    	return true;
    }

    @Override
    protected void registerData()
    {
        super.registerData();
        this.dataManager.register(DW_TEXTURE,"");
        this.dataManager.register(DW_ISWORK,false);
        this.dataManager.register(DW_WORKTIMER,0);
        this.dataManager.register(DW_SWING,0);
    }

    @Override
    public void knockBack(Entity entityIn, float strength, double xRatio, double zRatio)
    {
    	// ノックバックしないさせないやらせない
    }

    @Override
    public boolean startRiding(Entity entityIn, boolean force)
    {
        // 刀鍛冶は座らない座れない
    	return false;
    }

    @Override
    public void applyEntityCollision(Entity entityIn){
    	// 体当たりなんてへっちゃら
    }

    @Override
    public void writeAdditional(NBTTagCompound compound)
    {
        super.writeAdditional(compound);

        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.smithInventory.getSizeInventory(); ++i)
        {
            ItemStack itemstack = this.smithInventory.getStackInSlot(i);

            if (!itemstack.isEmpty())
            {
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				itemstack.write(nbttagcompound);
				nbttaglist.add(nbttagcompound);
            }
        }
        compound.setTag("InventorySmith", nbttaglist);

        if (Dw_Texture() != ""){
        	compound.setString("texture", Dw_Texture());
        }

        compound.setBoolean("isWork", Dw_ISWORK());
        if (Dw_ISWORK()){
        	compound.setInt("workTimer", Dw_WORKTIMER());
        }else{
        	compound.setInt("workTimer", 0);
        }
        compound.setInt("nextExp", nextExp);
    }




    @Override
    public void readAdditional(NBTTagCompound compound)
    {
        super.readAdditional(compound);

        NBTTagList nbttaglist = compound.getList("InventorySmith", 10);
        for (int i = 0; i < nbttaglist.size(); ++i)
        {
			NBTTagCompound nbttagcompound = nbttaglist.getCompound(i);
			int j = nbttagcompound.getByte("Slot");

			if (j >= 0 && j < this.smithInventory.getSizeInventory())
			{
				this.smithInventory.setInventorySlotContents(j, ItemStack.read(nbttagcompound));
			}
        }

        if (compound.hasKey("texture")){
        	String file = compound.getString("texture");
        	setCustomResourceLocation(Mod_FantomBlade.TextureManager().getTexture(NAME, file));
        }else{
        	setCustomResourceLocation(Mod_FantomBlade.TextureManager().getRandomTexture(NAME));
        }

        Dw_ISWORK(compound.getBoolean("isWork"));
    	Dw_WORKTIMER(compound.getInt("workTimer"));
    	nextExp = compound.getInt("nextExp");
    }

    public int getVerticalFaceSpeed()
    {
        return 80;
    }

    public int getHorizontalFaceSpeed()
    {
        return 90;
    }


    @Override
    public boolean canBePushed(){
    	return false;
    }


    @Override
	public boolean canDespawn()
    {
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
       return mod.fbd.core.SoundManager.sound_bladesmith_hart;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return mod.fbd.core.SoundManager.sound_bladesmith_damage;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return mod.fbd.core.SoundManager.sound_bladesmith_dead;
    }

    @Nullable
    protected ResourceLocation getLootTable()
    {
        return loot;
    }


    @Override
    public void notifyDataManagerChange(DataParameter<?> key)
    {
        super.notifyDataManagerChange(key);
    }


    @Override
    public void setRevengeTarget(@Nullable EntityLivingBase livingBase)
    {
        super.setRevengeTarget(livingBase);
    }

    @Override
    public void onDeath(DamageSource cause)
    {
        super.onDeath(cause);
    }

    public World getWorld()
    {
        return this.world;
    }

    public BlockPos getPos()
    {
        return new BlockPos(this);
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
        return null;
    }

    @Override
    public float getEyeHeight()
    {
        return 1.62F;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id)
    {
        if (id == 12)
        {
            this.spawnParticles(Particles.HEART);
        }
        else if (id == 13)
        {
            this.spawnParticles(Particles.ANGRY_VILLAGER);
        }
        else if (id == 14)
        {
            this.spawnParticles(Particles.HAPPY_VILLAGER);
        }
        else
        {
            super.handleStatusUpdate(id);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void spawnParticles(BasicParticleType particleType)
    {
        for (int i = 0; i < 5; ++i)
        {
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            double d2 = this.rand.nextGaussian() * 0.02D;
            this.world.spawnParticle(particleType, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + 1.0D + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d0, d1, d2);
        }
    }

    @Override
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData entityLivingData, @Nullable NBTTagCompound itemNbt) {
        return this.finalizeMobSpawn(difficulty, entityLivingData, true, itemNbt);
    }

    public IEntityLivingData finalizeMobSpawn(DifficultyInstance p_190672_1_, @Nullable IEntityLivingData p_190672_2_, boolean p_190672_3_,@Nullable NBTTagCompound itemNbt)
    {
        p_190672_2_ = super.onInitialSpawn(p_190672_1_, p_190672_2_,itemNbt);
        return p_190672_2_;
    }

    @Override
    public boolean canBeLeashedTo(EntityPlayer player)
    {
        return false;
    }

    @Override
    public void onStruckByLightning(EntityLightningBolt lightningBolt)
    {
        //雷にあたったときの処理
    }

    public IInventory getSmithInventory()
    {
        return this.smithInventory;
    }


    @Override
    protected void updateEquipmentIfNeeded(EntityItem itemEntity)
    {
    	// エンティティが何か拾った時の処理
    }

    private boolean canPickupItem(Item itemIn)
    {
        return false;
    }

    public void startWork(World world,BlockPos pos, ItemStack katana){
    	// ブロックの配置を作業用に変更
    	BlockPos pos1 = pos.offset(EnumFacing.NORTH);
    	IBlockState state = world.getBlockState(pos1);
    	if (state.getBlock() == Blocks.ANVIL){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, BlockCore.block_anvildummy.getDefaultState().with(BlockDummyAnvil.FACING, EnumFacing.EAST));
    	}else if (state.getBlock() == Blocks.FURNACE){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, BlockCore.block_furncedummy.getDefaultState().with(BlockDummyFurnce.FACING, EnumFacing.SOUTH));
    	}

    	pos1 = pos.offset(EnumFacing.SOUTH);
    	state = world.getBlockState(pos1);
    	if (state.getBlock() == Blocks.ANVIL){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, BlockCore.block_anvildummy.getDefaultState().with(BlockDummyAnvil.FACING, EnumFacing.WEST));
    	}else if (state.getBlock() == Blocks.FURNACE){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, BlockCore.block_furncedummy.getDefaultState().with(BlockDummyFurnce.FACING, EnumFacing.NORTH));
    	}

    	pos1 = pos.offset(EnumFacing.EAST);
    	state = world.getBlockState(pos1);
    	if (state.getBlock() == Blocks.ANVIL){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, BlockCore.block_anvildummy.getDefaultState().with(BlockDummyAnvil.FACING, EnumFacing.NORTH));
    	}else if (state.getBlock() == Blocks.FURNACE){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, BlockCore.block_furncedummy.getDefaultState().with(BlockDummyFurnce.FACING, EnumFacing.WEST));
    	}

    	pos1 = pos.offset(EnumFacing.WEST);
    	state = world.getBlockState(pos1);
    	if (state.getBlock() == Blocks.ANVIL){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, BlockCore.block_anvildummy.getDefaultState().with(BlockDummyAnvil.FACING, EnumFacing.SOUTH));
    	}else if (state.getBlock() == Blocks.FURNACE){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, BlockCore.block_furncedummy.getDefaultState().with(BlockDummyFurnce.FACING, EnumFacing.EAST));
    	}

    	// パラメータを設定
    	Dw_ISWORK(true);
    	Dw_Swing(0);
    	if (!ModCommon.isDebug){
    		Dw_WORKTIMER(6000); // 10 * 30 * 20 (5分をTicで)
    	}else{
    		Dw_WORKTIMER(1200); // 60 * 20 (1分をTicで)
    	}

    	this.smithInventory.setBlade(katana);
    }

    @Override
    public boolean checkWork(World world, BlockPos pos){
    	boolean flag_anvil = false;
    	boolean flag_furnce = false;
    	boolean flag_cauldron = false;
    	boolean flag_crafting = false;
    	BlockPos pos1 = pos.offset(EnumFacing.NORTH);
    	IBlockState state = world.getBlockState(pos1);
    	if (state.getBlock() == BlockCore.block_anvildummy){
    		if (state.get(BlockDummyAnvil.FACING) != EnumFacing.EAST){
        		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
        		world.setBlockState(pos1, BlockCore.block_anvildummy.getDefaultState().with(BlockDummyAnvil.FACING, EnumFacing.EAST));
    		}
    		flag_anvil = true;
    	}else if (state.getBlock() == BlockCore.block_furncedummy){
    		if (state.get(BlockDummyFurnce.FACING) != EnumFacing.SOUTH){
        		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
        		world.setBlockState(pos1, BlockCore.block_furncedummy.getDefaultState().with(BlockDummyFurnce.FACING, EnumFacing.SOUTH));
    		}
    		flag_furnce = true;
    	}else if (state.getBlock() == Blocks.CAULDRON && state.get(BlockCauldron.LEVEL) == 3){
    		flag_cauldron = true;
    	}else if (state.getBlock() == Blocks.CRAFTING_TABLE){
    		flag_crafting = true;
    	}

    	pos1 = pos.offset(EnumFacing.SOUTH);
    	state = world.getBlockState(pos1);
    	if (state.getBlock() == BlockCore.block_anvildummy && !flag_anvil ){
    		if (state.get(BlockDummyAnvil.FACING) != EnumFacing.WEST){
        		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
        		world.setBlockState(pos1, BlockCore.block_anvildummy.getDefaultState().with(BlockDummyAnvil.FACING, EnumFacing.WEST));
    		}
    		flag_anvil = true;
    	}else if (state.getBlock() == BlockCore.block_furncedummy && !flag_furnce){
    		if (state.get(BlockDummyFurnce.FACING) != EnumFacing.NORTH){
        		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
        		world.setBlockState(pos1, BlockCore.block_furncedummy.getDefaultState().with(BlockDummyFurnce.FACING, EnumFacing.NORTH));
    		}
    		flag_furnce = true;
    	}else if (state.getBlock() == Blocks.CAULDRON && state.get(BlockCauldron.LEVEL) == 3 && !flag_cauldron){
    		flag_cauldron = true;
    	}else if (state.getBlock() == Blocks.CRAFTING_TABLE && !flag_crafting){
    		flag_crafting = true;
    	}

    	pos1 = pos.offset(EnumFacing.EAST);
    	state = world.getBlockState(pos1);
    	if (state.getBlock() == BlockCore.block_anvildummy && !flag_anvil ){
    		if (state.get(BlockDummyAnvil.FACING) != EnumFacing.NORTH){
        		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
        		world.setBlockState(pos1, BlockCore.block_anvildummy.getDefaultState().with(BlockDummyAnvil.FACING, EnumFacing.NORTH));
    		}
    		flag_anvil = true;
    	}else if (state.getBlock() == BlockCore.block_furncedummy && !flag_furnce){
    		if (state.get(BlockDummyFurnce.FACING) != EnumFacing.WEST){
        		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
        		world.setBlockState(pos1, BlockCore.block_furncedummy.getDefaultState().with(BlockDummyFurnce.FACING, EnumFacing.WEST));
    		}
    		flag_furnce = true;
    	}else if (state.getBlock() == Blocks.CAULDRON && state.get(BlockCauldron.LEVEL) == 3 && !flag_cauldron){
    		flag_cauldron = true;
    	}else if (state.getBlock() == Blocks.CRAFTING_TABLE && !flag_crafting){
    		flag_crafting = true;
    	}

    	pos1 = pos.offset(EnumFacing.WEST);
    	state = world.getBlockState(pos1);
    	if (state.getBlock() == BlockCore.block_anvildummy && !flag_anvil ){
    		if (state.get(BlockDummyAnvil.FACING) != EnumFacing.SOUTH){
        		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
        		world.setBlockState(pos1, BlockCore.block_anvildummy.getDefaultState().with(BlockDummyAnvil.FACING, EnumFacing.SOUTH));
    		}
    		flag_anvil = true;
    	}else if (state.getBlock() == BlockCore.block_furncedummy && !flag_furnce){
    		if (state.get(BlockDummyFurnce.FACING) != EnumFacing.EAST){
        		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
        		world.setBlockState(pos1, BlockCore.block_furncedummy.getDefaultState().with(BlockDummyFurnce.FACING, EnumFacing.EAST));
    		}
    		flag_furnce = true;
    	}else if (state.getBlock() == Blocks.CAULDRON && state.get(BlockCauldron.LEVEL) == 3 && !flag_cauldron){
    		flag_cauldron = true;
    	}else if (state.getBlock() == Blocks.CRAFTING_TABLE && !flag_crafting){
    		flag_crafting = true;
    	}

    	return (flag_anvil && flag_furnce && flag_cauldron && flag_crafting);
    }

    @Override
    public void finishWork(World world,BlockPos pos, boolean success){

    	// ブロックをもとに戻す
    	BlockPos pos1 = pos.offset(EnumFacing.NORTH);
    	IBlockState state = world.getBlockState(pos1);
    	if (state.getBlock() == BlockCore.block_anvildummy){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, Blocks.ANVIL.getDefaultState().with(BlockDummyAnvil.FACING, EnumFacing.EAST));
    	}else if (state.getBlock() == BlockCore.block_furncedummy){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, Blocks.FURNACE.getDefaultState().with(BlockDummyFurnce.FACING, EnumFacing.SOUTH));
    	}else if (state.getBlock() == Blocks.CAULDRON){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, Blocks.CAULDRON.getDefaultState());
    	}

    	pos1 = pos.offset(EnumFacing.SOUTH);
    	state = world.getBlockState(pos1);
    	if (state.getBlock() == BlockCore.block_anvildummy){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, Blocks.ANVIL.getDefaultState().with(BlockDummyAnvil.FACING, EnumFacing.WEST));
    	}else if (state.getBlock() == BlockCore.block_furncedummy){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, Blocks.FURNACE.getDefaultState().with(BlockDummyFurnce.FACING, EnumFacing.NORTH));
    	}else if (state.getBlock() == Blocks.CAULDRON){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, Blocks.CAULDRON.getDefaultState());
    	}

    	pos1 = pos.offset(EnumFacing.EAST);
    	state = world.getBlockState(pos1);
    	if (state.getBlock() == BlockCore.block_anvildummy){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, Blocks.ANVIL.getDefaultState().with(BlockDummyAnvil.FACING, EnumFacing.NORTH));
    	}else if (state.getBlock() == BlockCore.block_furncedummy){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, Blocks.FURNACE.getDefaultState().with(BlockDummyFurnce.FACING, EnumFacing.WEST));
    	}else if (state.getBlock() == Blocks.CAULDRON){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, Blocks.CAULDRON.getDefaultState());
    	}

    	pos1 = pos.offset(EnumFacing.WEST);
    	state = world.getBlockState(pos1);
    	if (state.getBlock() == BlockCore.block_anvildummy){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, Blocks.ANVIL.getDefaultState().with(BlockDummyAnvil.FACING, EnumFacing.SOUTH));
    	}else if (state.getBlock() == BlockCore.block_furncedummy){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, Blocks.FURNACE.getDefaultState().with(BlockDummyFurnce.FACING, EnumFacing.EAST));
    	}else if (state.getBlock() == Blocks.CAULDRON){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, Blocks.CAULDRON.getDefaultState());
    	}

    	// パラメータをクリア
    	Dw_ISWORK(false);
    	Dw_Swing(0);
    	Dw_WORKTIMER(0);

    	// 道具乱を整理
    	int clear = 2;
		if (!success){
			// 失敗した場合できるはずだった刀もクリア
			clear = 3;
		}else{
			// 成功した場合経験値取得
//			this.addExp(nextExp);
		}
		nextExp = 0;

		// 作成に使ったアイテムをクリア
		for (int i = 0; i < clear; i++){
			if (this.smithInventory.getStackInSlot(i).getItem() == ItemCore.item_tamahagane){
				// 玉鋼は最高等級以外刀の作成に使わない
				continue;
			}
			this.smithInventory.setInventorySlotContents(i, ItemStack.EMPTY);
		}

		// 使った道具を破損させる
		for (int i = 3; i < this.smithInventory.getSizeInventory(); i++){
			ItemStack stack = this.smithInventory.getStackInSlot(i);
			if (stack.isDamageable()){
				stack.damageItem(1, this);
				if (stack.getDamage() == 0){
					// 道具が壊れた
					this.smithInventory.setInventorySlotContents(i, ItemStack.EMPTY);
				}
			}else{
				// 炭はすべて消費
				this.smithInventory.setInventorySlotContents(i, ItemStack.EMPTY);
			}
		}

    }





	public static boolean canStartArmorSmith(World world, BlockPos pos, boolean isWork, EntityArmorSmith smith) {
		boolean ret = false;
		boolean flag_fun = false;

		BlockPos pos1 = pos.add(1,1,0);
		BlockPos pos2 = pos.add(0,1,1);
		BlockPos pos3 = pos.add(-1,1,0);
		BlockPos pos4 = pos.add(0,1,-1);
		IBlockState state1 = world.getBlockState(pos1); // アンビル
		IBlockState state2 = world.getBlockState(pos2); // 竈
		IBlockState state3 = world.getBlockState(pos3); // 作業台
		IBlockState state4 = world.getBlockState(pos4); // 大釜


		IBlockState swap;
		BlockPos furnace_pos = pos2;;
		if ((state1.getBlock() == Blocks.ANVIL)){
			// 何もしない
		}else if (state2.getBlock() == Blocks.ANVIL){
			swap = state1;
			state1 = state2;
			state2 = state3;
			state3 = state4;
			state4 = swap;
			furnace_pos = pos3;
		}else if (state3.getBlock() == Blocks.ANVIL){
			swap = state1;
			state1 = state3;
			state3 = swap;
			swap = state4;
			state4 = state2;
			state2 = swap;
			furnace_pos = pos4;
		}else if (state4.getBlock() == Blocks.ANVIL){
			swap = state1;
			state1 = state4;
			state4 = state3;
			state3 = state2;
			state2 = swap;
			furnace_pos = pos1;
		}else{
			return ret;
		}

		if (!(state2.getBlock() ==  Blocks.FURNACE)){
			return ret;
		}

		if (!(state3.getBlock() == Blocks.CRAFTING_TABLE)){
			return ret;
		}

		if (!(state4.getBlock() == Blocks.CAULDRON)){
			return ret;
		}

		if (isWork){
			// 竈の中が空っぽか？
			try{
				TileEntityFurnace furnace = (TileEntityFurnace)world.getTileEntity(furnace_pos);
				for (int i = 0;i < furnace.getSizeInventory(); i++){
					if (furnace.getStackInSlot(i).isEmpty()){
						ret = true;
					}else{
						ret = false;
						break;
					}
				}
			}catch(Exception ex){}

			// 大釜に水が入っているか?
			if (ret && state4.get(BlockCauldron.LEVEL) != 3){
				ret = false;
			}

			// 所持品チェック
			if (ret && smith != null){
				ret = smith.itemCheck();
			}else{
				ret = false;
			}


		}else{
			ret = true;
		}

		return ret;
	}

	private boolean itemCheck() {
		return smithInventory.checkItem();
	}

	@Override
	public TextureInfo getTexture(){
		return Mod_FantomBlade.TextureManager().getTexture(NAME, Dw_Texture());
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


    private static final EnumBladePieceType[] names ={
    		EnumBladePieceType.NORMAL,EnumBladePieceType.SEIRYU,
    		EnumBladePieceType.SUZAKU,EnumBladePieceType.BYAKO,
    		EnumBladePieceType.GENBU,EnumBladePieceType.KIRIN,EnumBladePieceType.NIJI};
    private static float[] parcent = {100,0,0,0,0,0,0};

    public ItemStack createBlade(){
		ItemStack ret = ItemStack.EMPTY;
		int count = 0;
		int level = getLevel();

		ItemStack armor = this.smithInventory.getArmor();
		ItemStack material = this.smithInventory.getMaterial();
		if (armor.getCount() <= 0 || material.getCount() < 64){
			// ざいりょうがねーよん
			return ret;
		}

		// 出来上がるアイテム
		if (material.getItem() == ItemCore.item_tamahagane){
			if (armor.getItem() == Items.IRON_HELMET){
				ret = new ItemStack(ItemCore.item_haganehelmet,1);
			}else if (armor.getItem() == Items.IRON_CHESTPLATE){
				ret = new ItemStack(ItemCore.item_haganebody,1);
			}else if (armor.getItem() == Items.IRON_LEGGINGS){
				ret = new ItemStack(ItemCore.item_haganelegs,1);
			}else if (armor.getItem() == Items.IRON_BOOTS){
				ret = new ItemStack(ItemCore.item_haganeboots,1);
			}
		}else if (material.getItem() instanceof ItemBladePiece){
			switch(((ItemBladePiece)material.getItem()).getPieceType()){
			case BYAKO:
				if (armor.getItem() == Items.IRON_HELMET){
					ret = new ItemStack(ItemCore.item_byakohelmet,1);
				}else if (armor.getItem() == Items.IRON_CHESTPLATE){
					ret = new ItemStack(ItemCore.item_byakobody,1);
				}else if (armor.getItem() == Items.IRON_LEGGINGS){
					ret = new ItemStack(ItemCore.item_byakolegs,1);
				}else if (armor.getItem() == Items.IRON_BOOTS){
					ret = new ItemStack(ItemCore.item_byakoboots,1);
				}
				break;
			case GENBU:
				if (armor.getItem() == Items.IRON_HELMET){
					ret = new ItemStack(ItemCore.item_genbuhelmet,1);
				}else if (armor.getItem() == Items.IRON_CHESTPLATE){
					ret = new ItemStack(ItemCore.item_genbubody,1);
				}else if (armor.getItem() == Items.IRON_LEGGINGS){
					ret = new ItemStack(ItemCore.item_genbulegs,1);
				}else if (armor.getItem() == Items.IRON_BOOTS){
					ret = new ItemStack(ItemCore.item_genbuboots,1);
				}
				break;
			case KIRIN:
				if (armor.getItem() == Items.IRON_HELMET){
					ret = new ItemStack(ItemCore.item_kirinhelmet,1);
				}else if (armor.getItem() == Items.IRON_CHESTPLATE){
					ret = new ItemStack(ItemCore.item_kirinbody,1);
				}else if (armor.getItem() == Items.IRON_LEGGINGS){
					ret = new ItemStack(ItemCore.item_kirinlegs,1);
				}else if (armor.getItem() == Items.IRON_BOOTS){
					ret = new ItemStack(ItemCore.item_kirinboots,1);
				}
				break;
			case NIJI:
				if (armor.getItem() == Items.IRON_HELMET){
					ret = new ItemStack(ItemCore.item_nijihelmet,1);
				}else if (armor.getItem() == Items.IRON_CHESTPLATE){
					ret = new ItemStack(ItemCore.item_nijibody,1);
				}else if (armor.getItem() == Items.IRON_LEGGINGS){
					ret = new ItemStack(ItemCore.item_nijilegs,1);
				}else if (armor.getItem() == Items.IRON_BOOTS){
					ret = new ItemStack(ItemCore.item_nijiboots,1);
				}
				break;
			case SEIRYU:
				if (armor.getItem() == Items.IRON_HELMET){
					ret = new ItemStack(ItemCore.item_seiryuhelmet,1);
				}else if (armor.getItem() == Items.IRON_CHESTPLATE){
					ret = new ItemStack(ItemCore.item_seiryubody,1);
				}else if (armor.getItem() == Items.IRON_LEGGINGS){
					ret = new ItemStack(ItemCore.item_seiryulegs,1);
				}else if (armor.getItem() == Items.IRON_BOOTS){
					ret = new ItemStack(ItemCore.item_seiryuboots,1);
				}
				break;
			case SUZAKU:
				if (armor.getItem() == Items.IRON_HELMET){
					ret = new ItemStack(ItemCore.item_suzakuhelmet,1);
				}else if (armor.getItem() == Items.IRON_CHESTPLATE){
					ret = new ItemStack(ItemCore.item_suzakubody,1);
				}else if (armor.getItem() == Items.IRON_LEGGINGS){
					ret = new ItemStack(ItemCore.item_suzakulegs,1);
				}else if (armor.getItem() == Items.IRON_BOOTS){
					ret = new ItemStack(ItemCore.item_suzakuboots,1);
				}
				break;
			default:
				break;

			}

		}
    	return ret;
	}

	@Override
	public int getLevel() {
		// TODO 自動生成されたメソッド・スタブ
		return 1;
	}

	public void repairArmor() {
		this.smithInventory.repairArmor();

	}
}