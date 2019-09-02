package mod.fbd.entity.mob;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import mod.fbd.block.BlockCore;
import mod.fbd.block.BlockDummyAnvil;
import mod.fbd.block.BlockDummyFurnce;
import mod.fbd.config.ConfigValue;
import mod.fbd.core.ModCommon;
import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.core.SoundManager;
import mod.fbd.core.log.ModLog;
import mod.fbd.entity.ai.EntityAISmithTask;
import mod.fbd.entity.ai.EntityAIWatchClosestSmith;
import mod.fbd.intaractionobject.IntaractionObjectBladeSmith;
import mod.fbd.inventory.InventorySmith;
import mod.fbd.item.ItemBladePiece;
import mod.fbd.item.ItemBladePiece.EnumBladePieceType;
import mod.fbd.item.ItemCore;
import mod.fbd.item.ItemKatana;
import mod.fbd.item.ItemKatanaByako;
import mod.fbd.item.ItemKatanaGenbu;
import mod.fbd.item.ItemKatanaKirin;
import mod.fbd.item.ItemKatanaNiji;
import mod.fbd.item.ItemKatanaSeiryu;
import mod.fbd.item.ItemKatanaSuzaku;
import mod.fbd.resource.TextureInfo;
import mod.fbd.util.ModUtil;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.INpc;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Particles;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntityBladeSmith extends EntitySmithBase implements INpc
{
	public static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation("fbd","textures/entity/bladesmith_default.png");
	public static final String NAME= "bladesmith";
	public static final int REGISTERID = 1;
	public static final ResourceLocation loot = new ResourceLocation("fbd:"+NAME);
	public static final int MAX_LEVEL = 255;

	public void addExp(int nextExp) {
		int value = Dw_Exp() + nextExp;
		if (this.EXP_TABLE[EXP_TABLE.length-1] < value){
			value = EXP_TABLE[EXP_TABLE.length-1];
		}
		Dw_Exp(value);
	}

	private int randomTickDivider;
    private final InventorySmith smithInventory;
    private int nextExp=0;


    public EntityBladeSmith(World worldIn)
    {
        super(Mod_FantomBlade.RegistryEvents.BLADESMITH, worldIn);
        this.smithInventory = new InventorySmith();
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
            			new IntaractionObjectBladeSmith(this.getEntityId()),
            			(buf)->{
    						buf.writeInt(this.getEntityId());
    					});
            	//player.openGui(Mod_FantomBlade.instance, ModGui.GUI_ID_BLADESMITH + this.getEntityId(), world, this.getPos().getX(), this.getPos().getY(), this.getPos().getZ());
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

    public IEntityLivingData finalizeMobSpawn(DifficultyInstance p_190672_1_, @Nullable IEntityLivingData p_190672_2_, boolean p_190672_3_, @Nullable NBTTagCompound itemNbt)
    {
        p_190672_2_ = super.onInitialSpawn(p_190672_1_, p_190672_2_, itemNbt);
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
			this.addExp(nextExp);
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





	public static boolean canStartBladeSmith(World world, BlockPos pos, boolean isWork, EntityBladeSmith smith) {
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
	@Override
	public boolean updateTexture() {
		return textureUpdate;
	}
	@Override
	public void updateTexture(boolean flag) {
		textureUpdate = flag;
	}

	public static final int[] EXP_TABLE = {0,100,300,600,1000,1500,2100,3000,3900,4900,
			6100,7400,8900,10700,12500,14000,15700,17900,19800,21800,
			24400,27100,29400,32500,35600,38500,41500,44600,48400,51400,
			54500,58900,62200,66000,70200,74000,78800,83600,88800,94000,
			98800,103500,109000,114300,120000,126200,132600,138500,143300,148500,
			154600,161600,166900,173800,181300,188900,196200,204400,211700,217900,
			224100,231100,238200,246000,252900,261900,271300,278000,286500,294600,
			304000,312400,321200,328600,337600,348000,358100,367900,379300,390700,
			399900,409000,417800,430100,440500,449200,460200,470100,482400,494900,
			508200,520200,533300,545500,556600,568100,577700,588900,602000,614800,
			629500,640700,652700,667600,679300,692400,703600,717100,730300,743500,
			756700,771900,783400,796400,809000,822900,836800,849700,865400,881700,
			895400,908500,923400,941300,959200,973800,989800,1005100,1021100,1039000,
			1054000,1067600,1085500,1101100,1120000,1138400,1157100,1174800,1194200,1208300,
			1226300,1245400,1259600,1276000,1291300,1307300,1324800,1341900,1356800,1371900,
			1387900,1410400,1427800,1447900,1465900,1483000,1502600,1523300,1543800,1562200,
			1582200,1599200,1616500,1637700,1661100,1682100,1705100,1729300,1754300,1771800,
			1790000,1808600,1830200,1848500,1872900,1898000,1923900,1949900,1972300,1998400,
			2022700,2047400,2072300,2098800,2123100,2142800,2161400,2185300,2204100,2225800,
			2250000,2270600,2293900,2319600,2341900,2365000,2393000,2416200,2445400,2470500,
			2500100,2520200,2547700,2569200,2598900,2625600,2654000,2676800,2707800,2737200,
			2765000,2787100,2809100,2832800,2859000,2890700,2913500,2941400,2970700,2997600,
			3024800,3050100,3082000,3106500,3132400,3162700,3185500,3215000,3238100,3265000,
			3290100,3319500,3346000,3377500,3409000,3443900,3473100,3501800,3536300,3570500,
			3600600,3632300,3665300,3691900,3727700,3763400,3789700,3820800,3850300,3875400,
			3910300,3944000,3972200,4005500,4036100};

	public int getExpParcent() {
		if (getLevel() < MAX_LEVEL){
			return (Dw_Exp()*100)/EXP_TABLE[getLevel()+1];
		}
		return 100;
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

		ItemStack tamahagane = this.smithInventory.getTamahagane();
		if (tamahagane.getCount() <= 0){
			// ざいりょうがねーよん
			return ret;
		}

		ItemStack piece = this.smithInventory.getBladePiece();
		EnumBladePieceType makeItem = EnumBladePieceType.NORMAL;
		boolean exblade = false;
		if (piece.getCount() > 0 && level >= 10){
			//　10レベル以上のスミスでかつ欠片が設定されている
			if (level >= 250){
				// レベル250以上の場合確実に特殊刀ができる
				exblade = true;
			}else{
				// 特殊刀ができる確率を計算 最大70%
				if (Math.min(level/10.0F*5.0F, 70.0F) >= ModUtil.random(101)){
					exblade = true;
					makeItem = EnumBladePieceType.getFromIndex(((ItemBladePiece)piece.getItem()).getPieceType().getIndex());

				}
			}
		}

    	// 経験値
    	nextExp = makeItem.getBaseExp() + ModUtil.random(makeItem.getOptionExp());
		if (exblade){
			switch(makeItem){
			case BYAKO:
					ret = ItemKatanaByako.getDefaultStack();
				break;
			case GENBU:
					ret =ItemKatanaGenbu.getDefaultStack();
				break;
			case KIRIN:
					ret = ItemKatanaKirin.getDefaultStack();
				break;
			case NIJI:
					ret = ItemKatanaNiji.getDefaultStack();
				break;
			case SEIRYU:
					ret = ItemKatanaSeiryu.getDefaultStack();
				break;
			case SUZAKU:
					ret = ItemKatanaSuzaku.getDefaultStack();
				break;
			default:
				break;
	    	}
		}else{
			float maxLevel = 0.0F;
			float attackDamage=0.0F;
			float attackSpeed=0.0F;
			float endurance=0.0F;

			// レベルから作れるランクを決定
			// 最大レベルは製作者のレベル-0～10の範囲
			maxLevel = MathHelper.clamp(level - ModUtil.random(11),1,300);
			// 攻撃力は製作者のレベル+-1の範囲
			attackDamage = Math.min(Math.max(((int)(level/10))+1 + (ModUtil.random(3)-1),1),10);
			// スピード (レベル+-0~10の60%を最低値から引く)
			attackSpeed = MathHelper.clamp((-3.0F+(0.5F * (level + (ModUtil.random(11)*20F-10F))) / 60F), -3.0F, -0.5F);
			// 耐久度
			endurance = 2000 + (level/10) * (ModUtil.random(100) - ModUtil.random(255-level));

			// 玉鋼の数によるランク補正
			// 最大レベル補正(なし)
			// 攻撃力補正(なし)
			// 攻撃速度補正(なし)
			// 玉鋼のランクによる力ランク補正
//			if (tamahagane.getMetadata() == EnumTamahagane.FIRST_GRADE.getIndex()){
				// スピード補正(速度を0.0~0.9の範囲で下げる)
				attackSpeed = MathHelper.clamp(attackSpeed + ModUtil.random(10)/10, -3.0F, -0.5F);
				// 耐久度補正(玉鋼の数の10倍の耐久力)
				endurance = endurance + tamahagane.getCount() * 10;
				// 耐久度補正(1.2倍)
				endurance = endurance *1.2F;
//			}else if (tamahagane.getMetadata() == EnumTamahagane.SECOND_GRADE.getIndex()){
//				// セカンドランク
//				//　最大レベル補正(最大レベルを0～10の範囲で下げる)
//				maxLevel = Math.min(level - ModUtil.random(11), 1);
//				// 攻撃力補正
//				attackDamage = attackDamage - ((ModUtil.random(10)+1)*0.1F)/10.0F;
//				//　スピード補正(何もしない)
//				// 耐久度補正(何もしない)
//				// 耐久度補正(玉鋼の数の10倍の耐久力)
//				endurance = endurance + tamahagane.getCount() * 5;
//			}else if(tamahagane.getMetadata() == EnumTamahagane.THERD_GRADE.getIndex()){
//				//　最大レベル補正(最大レベルを10～30の範囲で下げる)
//				maxLevel = Math.min(level - ModUtil.random(31)+10, 1);
//				// 攻撃力補正
//				attackDamage = attackDamage - ((ModUtil.random(10)+1)*0.5F)/10.0F;
//				// スピード補正(速度を0.0~0.9の範囲で上げる)
//				attackSpeed = MathHelper.clamp(attackSpeed - ModUtil.random(10)/10, -3.0F, -0.5F);
//				// 耐久度補正(玉鋼の数の10倍の耐久力)
//				endurance = endurance + tamahagane.getCount() * 3;
//				// 耐久度補正(0.8倍)
//				endurance = endurance *0.8F;
//			}else{
//				//　最大レベル補正(レベル上げを不可にする)
//				maxLevel = 1;
//				// 攻撃力補正
//				attackDamage = attackDamage - ((ModUtil.random(10)+1)*0.7F)/10.0F;
//				// スピード補正(速度を1.0~1.9の範囲で上げる)
//				attackSpeed = MathHelper.clamp(attackSpeed -1.0F - ModUtil.random(10)/10, -3.0F, -0.5F);
//				// 耐久度補正(玉鋼の数の3倍の耐久力が減る)
//				endurance = endurance - tamahagane.getCount() * 3;
//				// 耐久度補正(0.5倍)
//				endurance = endurance *0.5F;
//			}
			ret = new ItemStack(ItemCore.item_katana);
			ItemKatana.setMaxLevel(ret,(int)maxLevel);
			ItemKatana.setAttackDamage(ret,attackDamage);
			ItemKatana.setAttackSpeed(ret,attackSpeed);
			ItemKatana.setEndurance(ret, (int)endurance);
			ItemKatana.setExp(ret,0);
			ItemKatana.setRustValue(ret, 0);

			// ポーション効果とエンチャントは最上級玉鋼を使用した場合のみ付加する
			if (level > 10){
				setPotionEffect(ret,level,tamahagane.getCount());
			}
			if (level > 5){
				setEnchant(ret,level,tamahagane.getCount());
			}
			String bladeName = ConfigValue.GENERAL.getRandomName();
			if (bladeName != null){
				ret.setDisplayName(new TextComponentTranslation(bladeName));
			}

		}
    	return ret;
	}


//	public ItemStack createBlade(){
//		ItemStack ret = ItemStack.EMPTY;
//		boolean isPeace = true;
//		int count = 0;
//		int level = getLevel();
//		int tamahagane = this.smithInventory.getTamahagane(EnumTamahagane.FIRST_GRADE);
//		if (tamahagane <= 0){
//			// ざいりょうがねーよん
//			return ret;
//		}
//    	int piece_seiryu = this.smithInventory.getBladePiece(EnumBladePieceType.SEIRYU);
//    	int piece_suzaku = this.smithInventory.getBladePiece(EnumBladePieceType.SUZAKU);
//    	int piece_byako = this.smithInventory.getBladePiece(EnumBladePieceType.BYAKO);
//    	int piece_genbu = this.smithInventory.getBladePiece(EnumBladePieceType.GENBU);
//    	int piece_kirin = this.smithInventory.getBladePiece(EnumBladePieceType.KIRIN);
//    	int piece_niji = this.smithInventory.getBladePiece(EnumBladePieceType.NIJI);
//    	int[] pieces = {piece_seiryu,piece_suzaku,piece_byako,piece_genbu,piece_genbu,piece_kirin,piece_niji};
//
//    	// 欠片が使用されているか、レベルが10未満なら欠片はできない
//    	if ((piece_seiryu + piece_suzaku + piece_byako + piece_genbu + piece_kirin) <= 0){
//        	// レベル10以上必要
//    		if (level >= 10){
//            	for (int i = 1; i < parcent.length-1; i++){
//            		// レベル補正
//            		parcent[i] += Math.min(level / 10.0F * 5.0F,50.0F);
//
//            		// 玉鋼補正
//            		if (parcent[i] != 0){
//            			parcent[i] += tamahagane / 20.0F;
//            		}
//            		count = ModUtil.random((int)(tamahagane/20.0F));
//            		if (count == 0){
//            			count = ModUtil.random(level/2) + 1;
//            		}
//            	}
//    		}
//    	}else{
//    		for (int i = 1; i < parcent.length; i++){
//    			// 素材がない
//    			if (pieces[i-1] == 0 || level < names[i].getMakeLevel()){continue;}
//    			// 素材補正
//        		parcent[i] += tamahagane / 50.0F + pieces[i-1]/20.0F;
//        		// レベル補正
//        		parcent[i] += Math.min(level / 20.0F * 5.0F, 50.0F);
//    		}
//    	}
//
//    	// 確率計算
//    	float sum = 0;
//    	for (float f:parcent){sum+=f;}
//    	List<EnumBladePieceType> nameList = new ArrayList<EnumBladePieceType>();
//    	List<Float> parList = new ArrayList<Float>();
//
//    	for (int i = 0; i < parcent.length; i++){
//    		if (parcent[i] != 0){
//    			nameList.add(names[i]);
//    			parList.add(parcent[1]/sum + (i==0?0:parList.get(parList.size()-1)));
//    		}
//    	}
//
//    	double val = ModUtil.randomD();
//    	EnumBladePieceType makeItem = EnumBladePieceType.NORMAL;
//    	for (int i = 0; i < parList.size(); i++){
//    		if (val <= parList.get(i)){
//    			makeItem = nameList.get(i);
//    		}
//    	}
//
//    	// 経験値
//    	nextExp = makeItem.getBaseExp() + ModUtil.random(makeItem.getOptionExp()/(isPeace?2:1));
//    	switch(makeItem){
//    			case NORMAL:
//    					ret = new ItemStack(ItemCore.item_katana);
//    					ItemKatana.setMaxLevel(ret,maxLevel(level,tamahagane));
//    					ItemKatana.setAttackDamage(ret,attackDamage(level,tamahagane));
//    					ItemKatana.setAttackSpeed(ret,attackSpeed(level,tamahagane));
//    					ItemKatana.setEndurance(ret, endurance(level,tamahagane));
//    					ItemKatana.setExp(ret,0);
//    					ItemKatana.setRustValue(ret, 0);
//    					if (level > 10){
//    						setPotionEffect(ret,level,tamahagane);
//    					}
//    					if (level > 5){
//    						setEnchant(ret,level,tamahagane);
//    					}
//    					String bladeName = ConfigValue.General.getRandomName();
//    					if (bladeName != null){
//    						ret.setStackDisplayName(bladeName);
//    					}
//    				break;
//    			case BYAKO:
//    				if (isPeace){
//    					ret = new ItemStack(ItemCore.item_bladepiece,ModUtil.random(count)+1,EnumBladePieceType.BYAKO.getIndex());
//    				}else{
//    					ret = ItemKatanaByako.getDefaultStack();
//    				}
//    				break;
//    			case GENBU:
//    				if (isPeace){
//    					ret = new ItemStack(ItemCore.item_bladepiece,ModUtil.random(count)+1,EnumBladePieceType.GENBU.getIndex());
//    				}else{
//    					ret =ItemKatanaGenbu.getDefaultStack();
//    				}
//    				break;
//    			case KIRIN:
//    				if (isPeace){
//    					ret = new ItemStack(ItemCore.item_bladepiece,ModUtil.random(count)+1,EnumBladePieceType.KIRIN.getIndex());
//    				}else{
//    					ret = ItemKatanaKirin.getDefaultStack();
//    				}
//    				break;
//    			case NIJI:
//    				if (isPeace){
//    					ret = new ItemStack(ItemCore.item_bladepiece,ModUtil.random(count)+1,EnumBladePieceType.NIJI.getIndex());
//    				}else{
//    					ret = ItemKatanaNiji.getDefaultStack();
//    				}
//    				break;
//    			case SEIRYU:
//    				if (isPeace){
//    					ret = new ItemStack(ItemCore.item_bladepiece,ModUtil.random(count)+1,EnumBladePieceType.SEIRYU.getIndex());
//    				}else{
//    					ret = ItemKatanaSeiryu.getDefaultStack();
//    				}
//    				break;
//    			case SUZAKU:
//    				if (isPeace){
//    					ret = new ItemStack(ItemCore.item_bladepiece,ModUtil.random(count)+1,EnumBladePieceType.SUZAKU.getIndex());
//    				}else{
//    					ret = ItemKatanaSuzaku.getDefaultStack();
//    				}
//    				break;
//    			default:
//    				break;
//    	    	}
//    	    	return ret;
//
//	}
    private ItemStack setPotionEffect(ItemStack katana, int level, int t1){
    	List<ItemStack> p = this.smithInventory.getPotions();
    	int cnt = 1;
    	int par = 40 + t1/100;
    	cnt = ModUtil.random(Math.min(MathHelper.floor(level/20) + 1, 3));
    	if (cnt != 0){
    		List<PotionEffect> effects = new ArrayList<PotionEffect>();
        	for (ItemStack stack : p){
        		for (PotionEffect effect : PotionUtils.getEffectsFromStack(stack)){
        			if (ModUtil.random(100) < par){
        				effects.add(effect);
        				cnt--;
        				if (cnt <= 0){break;}
        			}
        		}
        	}
			ItemKatana.setPotionEffects(katana, effects);
    	}
    	return katana;
    }

    private static final ItemKatana dummyKatana = new ItemKatana(new Item.Properties());
    private static final ItemStack enchantedDummy = new ItemStack(dummyKatana);
    private ItemStack setEnchant(ItemStack katana, int level, int t1){
    	List<ItemStack> p = this.smithInventory.getPotions();
    	int cnt = 1;
    	int cntm = 0;
    	int par = 40 + t1/100;
    	cnt = ModUtil.random(Math.min(MathHelper.floor(level/5) + 1, 7));
    	if (cnt != 0){
    		List<Enchantment> effects = new ArrayList<Enchantment>();
        	for (ItemStack stack : p){
        		Map<Enchantment,Integer> enchants = EnchantmentHelper.getEnchantments(stack);
        		for (Enchantment effect : enchants.keySet()){
        			if (effect.canApply(enchantedDummy)){
            			if (ModUtil.random(100) < par){
            				katana.addEnchantment(effect, enchants.get(effect));
            				cnt--;
            				if (cnt <= 0){break;}
            			}
        			}
        		}
        	}
    	}

    	return katana;
    }


	private int maxLevel(int level, int t1){
		int value = 0;
		value = (int)Math.min(
				ModUtil.random(level) +
				(t1/100 * (ModUtil.random(t1/100)+1)),
				level+10);
		return value;
	}

	public int endurance(int level, int t1){
		int value = 0;
		value = (int)MathHelper.clamp(100+((t1/100))*(ModUtil.random(MathHelper.clamp(t1/100,1,7))/2+1)*200,100,4000);
		return value;
	}

	private float[] damageLevel = {2.0F,4.0F,6.0F,7.0F,8.0F,9.0F,10.0F};
    private float attackDamage(int level, int t1){
    	float value = 0.0F;
    	int index = (int) (ModUtil.random(level/10) +
    			MathHelper.absMax((t1)/300,0));
    	index = MathHelper.clamp(index, 0, damageLevel.length-1);
    	value = damageLevel[index];

    	return value;
    }

    private double attackSpeed(int level, int t1){
    	double value = -3.0F;
    	value = MathHelper.clamp(value + (level/50.0D),-3.0D,-0.5D);
    	return value;
    }


	public void setCreateBlade(ItemStack katana){
		if (katana.isEmpty()){
			this.smithInventory.setInventorySlotContents(12, katana);
		}
	}

	public void repairBlade() {
		this.smithInventory.repairBlade();

	}

	@Override
	public int getLevel() {
		int ret = EXP_TABLE.length;
		int exp = this.Dw_Exp();
		for (int i = 0; i < EXP_TABLE.length-1; i++){
			if (EXP_TABLE[i] <= exp && exp < EXP_TABLE[i+1]){
				ret = i+1;
				break;
			}
		}
		return ret;
	}


}