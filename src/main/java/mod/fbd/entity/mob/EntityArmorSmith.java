package mod.fbd.entity.mob;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import mod.fbd.block.BlockCore;
import mod.fbd.block.BlockDummyAnvil;
import mod.fbd.block.BlockDummyFurnce;
import mod.fbd.core.ModCommon;
import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.core.log.ModLog;
import mod.fbd.inventory.ContainerArmorSmith;
import mod.fbd.inventory.InventoryArmorSmith;
import mod.fbd.inventory.InventoryBladeSmith;
import mod.fbd.inventory.InventorySmithBase;
import mod.fbd.item.ItemCore;
import mod.fbd.item.armor.ItemHaganeAromor;
import mod.fbd.item.piece.ItemBladePiece.EnumBladePieceType;
import mod.fbd.util.ModUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CauldronBlock;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.PotionUtils;
import net.minecraft.tileentity.FurnaceTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntityArmorSmith extends EntitySmithBase {
	public static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation("fbd","textures/entity/armorsmith_default.png");
	public static final String NAME= "armorsmith";
	public static final ResourceLocation loot = new ResourceLocation("fbd:"+NAME);
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
	private final InventoryArmorSmith smithInventory;
    private int nextExp=0;


    public EntityArmorSmith(World worldIn) {
        this(Mod_FantomBlade.RegistryEvents.ARMORSMITH, worldIn);
    }

	public EntityArmorSmith(FMLPlayMessages.SpawnEntity packet, World world) {
		this(Mod_FantomBlade.RegistryEvents.ARMORSMITH, world);
	}

    public EntityArmorSmith(EntityType<? extends CreatureEntity> etype, World worldIn) {
        super(etype ,worldIn);
        this.smithInventory = new InventoryArmorSmith(this);
    }


    @Override
    public boolean processInteract(PlayerEntity player, Hand hand){
    	if (player.isSneaking()) {return false;}
    	if (!player.world.isRemote){
        	if (!Dw_ISWORK()){
        		ModLog.log().debug("open gui entity id:"+this.getEntityId());
        		NetworkHooks.openGui((ServerPlayerEntity)player,
            			new INamedContainerProvider() {
        					@Override
        					@Nullable
        					public Container createMenu(int id, PlayerInventory playerInv, PlayerEntity player) {
        						return new ContainerArmorSmith(id, playerInv, smithInventory);
        					}

        					@Override
        					public ITextComponent getDisplayName() {
        						return new TranslationTextComponent("container.bladesmith");
        					}
        				},
            			(buf)->{
    						buf.writeInt(this.getEntityId());
    					});
        	}
    	}
    	return true;
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);

        ListNBT ListNBT = new ListNBT();
        for (int i = 0; i < this.smithInventory.getSizeInventory(); ++i) {
            ItemStack itemstack = this.smithInventory.getStackInSlot(i);

            if (!itemstack.isEmpty()) {
				CompoundNBT CompoundNBT = new CompoundNBT();
				CompoundNBT.putInt("Slot", i);
				itemstack.write(CompoundNBT);
				ListNBT.add(CompoundNBT);
            }
        }
        compound.put("InventorySmith", ListNBT);

        compound.putInt("nextExp", nextExp);
    }

    @Override
    public void readAdditional(CompoundNBT compound)
    {
        super.readAdditional(compound);

        ListNBT ListNBT = compound.getList("InventorySmith", 10);
        for (int i = 0; i < ListNBT.size(); ++i) {
			CompoundNBT CompoundNBT = ListNBT.getCompound(i);
			int j = CompoundNBT.getInt("Slot");

			if (j >= 0 && j < this.smithInventory.getSizeInventory()) {
				this.smithInventory.setInventorySlotContents(j, ItemStack.read(CompoundNBT));
			}
        }

    	nextExp = compound.getInt("nextExp");
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return loot;
    }

    @Override
	public ResourceLocation getDefaultTexture() {
		return this.TEXTURE_DEFAULT;
	}

    public void startWork(World world,BlockPos pos, ItemStack armor){
    	// ブロックの配置を作業用に変更
    	BlockPos pos1 = pos.offset(Direction.NORTH);
    	BlockState state = world.getBlockState(pos1);
    	if (state.getBlock() == Blocks.ANVIL){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, BlockCore.block_anvildummy.getDefaultState().with(BlockDummyAnvil.FACING, Direction.EAST));
    	}else if (state.getBlock() == Blocks.FURNACE){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, BlockCore.block_furncedummy.getDefaultState().with(BlockDummyFurnce.FACING, Direction.SOUTH));
    	}

    	pos1 = pos.offset(Direction.SOUTH);
    	state = world.getBlockState(pos1);
    	if (state.getBlock() == Blocks.ANVIL){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, BlockCore.block_anvildummy.getDefaultState().with(BlockDummyAnvil.FACING, Direction.WEST));
    	}else if (state.getBlock() == Blocks.FURNACE){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, BlockCore.block_furncedummy.getDefaultState().with(BlockDummyFurnce.FACING, Direction.NORTH));
    	}

    	pos1 = pos.offset(Direction.EAST);
    	state = world.getBlockState(pos1);
    	if (state.getBlock() == Blocks.ANVIL){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, BlockCore.block_anvildummy.getDefaultState().with(BlockDummyAnvil.FACING, Direction.NORTH));
    	}else if (state.getBlock() == Blocks.FURNACE){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, BlockCore.block_furncedummy.getDefaultState().with(BlockDummyFurnce.FACING, Direction.WEST));
    	}

    	pos1 = pos.offset(Direction.WEST);
    	state = world.getBlockState(pos1);
    	if (state.getBlock() == Blocks.ANVIL){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, BlockCore.block_anvildummy.getDefaultState().with(BlockDummyAnvil.FACING, Direction.SOUTH));
    	}else if (state.getBlock() == Blocks.FURNACE){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, BlockCore.block_furncedummy.getDefaultState().with(BlockDummyFurnce.FACING, Direction.EAST));
    	}

    	// パラメータを設定
    	Dw_ISWORK(true);
    	Dw_Swing(0);
    	if (!ModCommon.isDebug){
    		Dw_WORKTIMER(6000); // 10 * 30 * 20 (5分をTicで)
    	}else{
    		Dw_WORKTIMER(1200); // 60 * 20 (1分をTicで)
    	}

    	this.smithInventory.setResult(armor);
    }

    @Override
    public boolean checkWork(World world, BlockPos pos){
    	boolean flag_anvil = false;
    	boolean flag_furnce = false;
    	boolean flag_cauldron = false;
    	boolean flag_crafting = false;
    	BlockPos pos1 = pos.offset(Direction.NORTH);
    	BlockState state = world.getBlockState(pos1);
    	if (state.getBlock() == BlockCore.block_anvildummy){
    		if (state.get(BlockDummyAnvil.FACING) != Direction.EAST){
        		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
        		world.setBlockState(pos1, BlockCore.block_anvildummy.getDefaultState().with(BlockDummyAnvil.FACING, Direction.EAST));
    		}
    		flag_anvil = true;
    	}else if (state.getBlock() == BlockCore.block_furncedummy){
    		if (state.get(BlockDummyFurnce.FACING) != Direction.SOUTH){
        		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
        		world.setBlockState(pos1, BlockCore.block_furncedummy.getDefaultState().with(BlockDummyFurnce.FACING, Direction.SOUTH));
    		}
    		flag_furnce = true;
    	}else if (state.getBlock() == Blocks.CAULDRON && state.get(CauldronBlock.LEVEL) == 3){
    		flag_cauldron = true;
    	}else if (state.getBlock() == Blocks.CRAFTING_TABLE){
    		flag_crafting = true;
    	}

    	pos1 = pos.offset(Direction.SOUTH);
    	state = world.getBlockState(pos1);
    	if (state.getBlock() == BlockCore.block_anvildummy && !flag_anvil ){
    		if (state.get(BlockDummyAnvil.FACING) != Direction.WEST){
        		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
        		world.setBlockState(pos1, BlockCore.block_anvildummy.getDefaultState().with(BlockDummyAnvil.FACING, Direction.WEST));
    		}
    		flag_anvil = true;
    	}else if (state.getBlock() == BlockCore.block_furncedummy && !flag_furnce){
    		if (state.get(BlockDummyFurnce.FACING) != Direction.NORTH){
        		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
        		world.setBlockState(pos1, BlockCore.block_furncedummy.getDefaultState().with(BlockDummyFurnce.FACING, Direction.NORTH));
    		}
    		flag_furnce = true;
    	}else if (state.getBlock() == Blocks.CAULDRON && state.get(CauldronBlock.LEVEL) == 3 && !flag_cauldron){
    		flag_cauldron = true;
    	}else if (state.getBlock() == Blocks.CRAFTING_TABLE && !flag_crafting){
    		flag_crafting = true;
    	}

    	pos1 = pos.offset(Direction.EAST);
    	state = world.getBlockState(pos1);
    	if (state.getBlock() == BlockCore.block_anvildummy && !flag_anvil ){
    		if (state.get(BlockDummyAnvil.FACING) != Direction.NORTH){
        		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
        		world.setBlockState(pos1, BlockCore.block_anvildummy.getDefaultState().with(BlockDummyAnvil.FACING, Direction.NORTH));
    		}
    		flag_anvil = true;
    	}else if (state.getBlock() == BlockCore.block_furncedummy && !flag_furnce){
    		if (state.get(BlockDummyFurnce.FACING) != Direction.WEST){
        		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
        		world.setBlockState(pos1, BlockCore.block_furncedummy.getDefaultState().with(BlockDummyFurnce.FACING, Direction.WEST));
    		}
    		flag_furnce = true;
    	}else if (state.getBlock() == Blocks.CAULDRON && state.get(CauldronBlock.LEVEL) == 3 && !flag_cauldron){
    		flag_cauldron = true;
    	}else if (state.getBlock() == Blocks.CRAFTING_TABLE && !flag_crafting){
    		flag_crafting = true;
    	}

    	pos1 = pos.offset(Direction.WEST);
    	state = world.getBlockState(pos1);
    	if (state.getBlock() == BlockCore.block_anvildummy && !flag_anvil ){
    		if (state.get(BlockDummyAnvil.FACING) != Direction.SOUTH){
        		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
        		world.setBlockState(pos1, BlockCore.block_anvildummy.getDefaultState().with(BlockDummyAnvil.FACING, Direction.SOUTH));
    		}
    		flag_anvil = true;
    	}else if (state.getBlock() == BlockCore.block_furncedummy && !flag_furnce){
    		if (state.get(BlockDummyFurnce.FACING) != Direction.EAST){
        		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
        		world.setBlockState(pos1, BlockCore.block_furncedummy.getDefaultState().with(BlockDummyFurnce.FACING, Direction.EAST));
    		}
    		flag_furnce = true;
    	}else if (state.getBlock() == Blocks.CAULDRON && state.get(CauldronBlock.LEVEL) == 3 && !flag_cauldron){
    		flag_cauldron = true;
    	}else if (state.getBlock() == Blocks.CRAFTING_TABLE && !flag_crafting){
    		flag_crafting = true;
    	}

    	return (flag_anvil && flag_furnce && flag_cauldron && flag_crafting);
    }

    @Override
    public void finishWork(World world,BlockPos pos, boolean success){
    	// iブロックをもとに戻す
    	BlockPos pos1 = pos.offset(Direction.NORTH);
    	BlockState state = world.getBlockState(pos1);
    	if (state.getBlock() == BlockCore.block_anvildummy){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, Blocks.ANVIL.getDefaultState().with(BlockDummyAnvil.FACING, Direction.EAST));
    	}else if (state.getBlock() == BlockCore.block_furncedummy){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, Blocks.FURNACE.getDefaultState().with(BlockDummyFurnce.FACING, Direction.SOUTH));
    	}else if (state.getBlock() == Blocks.CAULDRON){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, Blocks.CAULDRON.getDefaultState());
    	}

    	pos1 = pos.offset(Direction.SOUTH);
    	state = world.getBlockState(pos1);
    	if (state.getBlock() == BlockCore.block_anvildummy){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, Blocks.ANVIL.getDefaultState().with(BlockDummyAnvil.FACING, Direction.WEST));
    	}else if (state.getBlock() == BlockCore.block_furncedummy){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, Blocks.FURNACE.getDefaultState().with(BlockDummyFurnce.FACING, Direction.NORTH));
    	}else if (state.getBlock() == Blocks.CAULDRON){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, Blocks.CAULDRON.getDefaultState());
    	}

    	pos1 = pos.offset(Direction.EAST);
    	state = world.getBlockState(pos1);
    	if (state.getBlock() == BlockCore.block_anvildummy){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, Blocks.ANVIL.getDefaultState().with(BlockDummyAnvil.FACING, Direction.NORTH));
    	}else if (state.getBlock() == BlockCore.block_furncedummy){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, Blocks.FURNACE.getDefaultState().with(BlockDummyFurnce.FACING, Direction.WEST));
    	}else if (state.getBlock() == Blocks.CAULDRON){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, Blocks.CAULDRON.getDefaultState());
    	}

    	pos1 = pos.offset(Direction.WEST);
    	state = world.getBlockState(pos1);
    	if (state.getBlock() == BlockCore.block_anvildummy){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, Blocks.ANVIL.getDefaultState().with(BlockDummyAnvil.FACING, Direction.SOUTH));
    	}else if (state.getBlock() == BlockCore.block_furncedummy){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, Blocks.FURNACE.getDefaultState().with(BlockDummyFurnce.FACING, Direction.EAST));
    	}else if (state.getBlock() == Blocks.CAULDRON){
    		world.setBlockState(pos1,Blocks.AIR.getDefaultState());
    		world.setBlockState(pos1, Blocks.CAULDRON.getDefaultState());
    	}

    	// パラメータをクリア
    	Dw_ISWORK(false);
    	Dw_Swing(0);
    	Dw_WORKTIMER(0);

    	// 道具乱を整理
    	int clear = InventoryArmorSmith.INV_05_RESULT;
		if (!success){
			// 失敗した場合できるはずだった防具もクリア
			clear = InventoryArmorSmith.INV_06_TOOL_BIGHAMMER;
		}else{
			// 成功した場合経験値取得
			this.addExp(nextExp);
		}
		nextExp = 0;

		// 作成に使ったアイテムをクリア
		for (int i = 0; i < clear; i++){
			this.smithInventory.setInventorySlotContents(i, ItemStack.EMPTY);
		}

		// 使った道具を破損させる
		for (int i = InventoryBladeSmith.INV_06_TOOL_BIGHAMMER; i < this.smithInventory.getSizeInventory(); i++){
			ItemStack stack = this.smithInventory.getStackInSlot(i);
			if (stack.isDamageable()){
				stack.damageItem(1, this, (entity)->{entity.sendBreakAnimation(Hand.MAIN_HAND);});
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

    @Override
	public boolean canStartSmith(World world, BlockPos pos, boolean isWork, EntitySmithBase smith) {
		boolean ret = true;

		BlockPos pos1 = pos.offset(Direction.NORTH);
		BlockPos pos2 = pos.offset(Direction.EAST);
		BlockPos pos3 = pos.offset(Direction.SOUTH);
		BlockPos pos4 = pos.offset(Direction.WEST);
		BlockState state1 = world.getBlockState(pos1); // アンビル
		BlockState state2 = world.getBlockState(pos2); // 竈
		BlockState state3 = world.getBlockState(pos3); // 作業台
		BlockState state4 = world.getBlockState(pos4); // 大釜

		// 周囲のブロックで同じブロックがあるか確認
		if (state1.getBlock() == state2.getBlock() || state1.getBlock() == state3.getBlock() || state1.getBlock() == state4.getBlock() ||
			state2.getBlock() == state3.getBlock() || state2.getBlock() == state4.getBlock() || state3.getBlock() == state4.getBlock()) {
			// 同じブロックがある場合作業できない
			return false;
		}

		// ブロックの種類と状態のチェック
		if (!checkBlockState(state1, pos1) ||
			!checkBlockState(state2, pos2) ||
			!checkBlockState(state3, pos3) ||
			!checkBlockState(state4, pos4)) {
			return false;
		}

		if (isWork){
			// 所持品チェック
			if (ret && smith != null){
				ret = smithInventory.checkItem();
			}else{
				ret = false;
			}
		}else{
			ret = true;
		}

		return ret;
	}

	private boolean checkBlockState(BlockState state, BlockPos pos) {
		boolean ret = false;
		if (state.getBlock() == Blocks.ANVIL || state.getBlock() == Blocks.CRAFTING_TABLE || state.getBlock() == BlockCore.block_anvildummy || state.getBlock() == BlockCore.block_furncedummy) {
			// アンビルか、ワークベンチの場合特に問題なし
			ret = true;
		}else if (state.getBlock() == Blocks.CAULDRON && state.get(CauldronBlock.LEVEL) ==3) {
			// 大釜の場合満タンでなきゃダメ
			ret = true;
		}else if (state.getBlock() == Blocks.FURNACE) {
			try {
				// 竈の場合空っぽじゃなきゃダメ
				FurnaceTileEntity ent = (FurnaceTileEntity)this.world.getTileEntity(pos);
				ret = ent.isEmpty();
			}catch(Throwable e) {
				// 竈以外のTileEntityの場合失敗にする
			}
		}
		return ret;
	}

	@Override
	public int getMaxExp() {
		return EXP_TABLE[EXP_TABLE.length-1];
	}

	@Override
	public int getExpValue(int level) {
		if (level >= EXP_TABLE.length) {
			return EXP_TABLE[EXP_TABLE.length-1];
		}
		return EXP_TABLE[level];
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

	@Override
	public InventorySmithBase getInventory() {
		return this.smithInventory;
	}

	@Override
	public ItemStack create() {
		return this.createArmor();
	}

	@Override
	public void repair() {
		this.smithInventory.repair();
	}

	@Override
	public String getSmithName() {
		return NAME;
	}

    public ItemStack createArmor(){
		ItemStack ret = ItemStack.EMPTY;
		int count = 0;
		int level = getLevel();

		// i材料の数を取得
		int tamahagane = this.smithInventory.getTamahagane(ItemCore.item_tamahagane);
		List<ItemStack> base = this.smithInventory.getBaseEqipent();
		int[] piece = this.smithInventory.getBladePiece();
		int seiryu = piece[EnumBladePieceType.SEIRYU.getIndex()];
		int suzaku = piece[EnumBladePieceType.SUZAKU.getIndex()];
		int kirin = piece[EnumBladePieceType.KIRIN.getIndex()];
		int byako = piece[EnumBladePieceType.BYAKO.getIndex()];
		int genbu = piece[EnumBladePieceType.GENBU.getIndex()];
		int niji = piece[EnumBladePieceType.NIJI.getIndex()];

		// i五行の強弱をつける
		seiryu = seiryu + genbu - byako;
		suzaku = suzaku + seiryu - genbu;
		kirin = kirin + suzaku - seiryu;
		byako = kirin + suzaku - seiryu;
		genbu = genbu + byako - kirin;
		// i欠片の最大数を計算
		int max = ModUtil.IntMax(niji, seiryu, suzaku, kirin, byako, genbu);

		// i玉鋼がないか、ベースが指定されているのに欠片の力が弱い場合作れない
		if (tamahagane <= 0 || (base.size() != 0 && max <= 0)) {
			// iざいりょうがねーよん
			return ret;
		}

		Item baseEquipment;
		if (base.size() > 0) {
			// aベースが指定されている場合指定されているベースのうちどれができるかを決める
			baseEquipment = base.get(ModUtil.random(base.size())).getItem();
		}else {
			// a指定がない場合ランダム
			Item[] work = new Item[]{ItemCore.item_haganebody,ItemCore.item_haganeboots, ItemCore.item_haganehelmet, ItemCore.item_haganelegs};
			baseEquipment = work[ModUtil.random(work.length)];
		}

		// aデフォルト
		EnumBladePieceType pieceType = EnumBladePieceType.NORMAL;

		// a出来上がる防具を決める
		if (base.size() > 0) {
			// aベース指定がある場合は確定
			baseEquipment = getBaseEquipment(max, niji, seiryu, suzaku, kirin, byako, genbu, baseEquipment);
		}else {
			// a欠片が使用されている場合
			if (max > 0) {
				if (level >= 250){
					// aレベル250以上の場合確実に特殊鎧ができる
					baseEquipment = getBaseEquipment(max, niji, seiryu, suzaku, kirin, byako, genbu, baseEquipment);
				}else{
					// a特殊鎧ができる確率を計算 最大70%
					// aレベル/10*5(5% からstart 70%になるのはLv140以上)
					// a最初の1スタック分は 欠片/10.0F*3.0F(0.3% ～ 19.2%分の補助
					// 2スタック目以降は欠片/64%分の補助(最大2%)
					if (Math.min((level/10.0F*5.0F) + ((max%65)/10.0F*5.0F) + Math.max((max-64)/64,0), 70.0F) >= ModUtil.random(101)){
						baseEquipment = getBaseEquipment(max, niji, seiryu, suzaku, kirin, byako, genbu, baseEquipment);
					}
				}
			}
		}

		pieceType = ((ItemHaganeAromor)baseEquipment).getPieceType();

		// a返却用アイテムを作る
		ret = new ItemStack(baseEquipment);
		// a経験値確定
		nextExp = pieceType.getBaseExp();
		if (base.size()>0) {
			ItemHaganeAromor.setHardness(ret, ModUtil.random(60)/100 + 0.2D);
			ItemHaganeAromor.setWeight(ret, -1 * ((ModUtil.random(300)/1000)+0.2D));
			ItemHaganeAromor.setEndurance(ret, ModUtil.random(60)/100 + 0.2D);
		}else {
			// aベースを使用していない場合だけ追加経験値を得られる
			nextExp += pieceType.getOptionExp();
			// aレベルと玉鋼の数による強化処理
			int rd = 0;
			List<ItemStack> tm = this.smithInventory.getTamahagane();
			for (int i = 0; i < tm.size(); i++) {
				rd += (tm.get(i).getCount()/((i+1)*10));
			}
			// i重さは設定しない
			ItemHaganeAromor.setHardness(ret, 1 + (ModUtil.random(rd) + (level / 20))/1000);
			ItemHaganeAromor.setEndurance(ret, 1 + (ModUtil.random(rd) + (level / 20))/1000);
		}

		// レベル5以下とベース指定の場合エンチャントはつけられない
		if (base.size() <= 0 && this.getLevel() > 5) {
			// エンチャント本を取り出す
			List<ItemStack> encbook = this.smithInventory.getEnchangedBooks();

			// レベル80で5つ確定、レベル60で4つ確定、レベル40で3つ確定、レベル20で2つ確定、レベル10で1つ確定
			int confirm = level >= 80?5:level>=60?4:level>=40?3:level>=20?2:level>10?1:0;
			Map<Enchantment, Integer> encMap = Maps.newHashMap();

			// エンチャントチェック用のゾンビ
			ZombieEntity living = EntityType.ZOMBIE.create(world);
			living.setItemStackToSlot(((ItemHaganeAromor)baseEquipment).getEquipmentSlot(), new ItemStack(baseEquipment));

			// 登録用マップを作る
			for (ItemStack stack: encbook) {
				for (Entry<Enchantment,Integer> enc : EnchantmentHelper.getEnchantments(stack).entrySet()) {
					Enchantment enchant = enc.getKey();
					int encLevel = enc.getValue();
					// エンチャントできるか確認
					if (enchant.getEntityEquipment(living).size() != 0) {
						// 同じエンチャントが登録済みの場合レベルを高い方に更新
						if (encMap.containsKey(enchant)) {
							if (encMap.get(enchant) < encLevel) {
								encMap.put(enchant, encLevel);
							}
						}else {
							// 確定の残りあり又はレベル200越えもしくは特殊刀の場合100%成功
							if (confirm > 0 || level > 200) {
								encMap.put(enchant, encLevel);
								confirm--;
							}else {
								// 20%の確率でエンチャント成功
								if (ModUtil.random(1000) < 200) {
									encMap.put(enchant, encLevel);
									confirm--;
								}
							}
						}
					}
				}
			}
			if (encMap.size() > 0) {
				// エンチャント追加
				EnchantmentHelper.setEnchantments(encMap, ret);
			}
		}

		// レベル10以下とベース指定の場合及び、にじ以外の防具エンチャントはつけられない
		if (base.size() <= 0 && this.getLevel() > 10 && pieceType == EnumBladePieceType.NIJI) {
			// レベル10以上ならポーション効果をつける
			List<ItemStack> potion = this.smithInventory.getPotions();
			// レベル50以上なら2つ確定,レベル25以上なら1つ確定
			int confirm = level>=50?2:level>=25?1:0;

			// エフェクトリストを作る
			List<EffectInstance> effects = new ArrayList<EffectInstance>();
			for (ItemStack stack : potion) {
				for (EffectInstance effect2 : PotionUtils.getFullEffectsFromItem(stack)){
					boolean update = false;
					int updateIdx = 0;
					for (int i = 0; i < effects.size() && !update; i++) {
						if (effects.get(i).getPotion() == effect2.getPotion() && effects.get(i).getAmplifier() < effect2.getAmplifier()) {
							// 同じ効果で強度が強い場合更新
							updateIdx = i;
							update = true;
						}
					}
					if (update) {
						// エフェクト更新
						effects.set(updateIdx, new EffectInstance(effect2.getPotion(),effect2.getDuration(), effect2.getAmplifier()));
					} else {
						// 確定分が残っているなら登録
						if (confirm > 0) {
							effects.add(new EffectInstance(effect2.getPotion(),effect2.getDuration(), effect2.getAmplifier()));
							confirm--;
						}else {
							// 30%の確率でエンチャント成功
							if (ModUtil.random(1000) < 300) {
								effects.add(new EffectInstance(effect2.getPotion(),effect2.getDuration(), effect2.getAmplifier()));
								confirm--;
							}
						}
					}
				}
			}

			// エフェクト登録
			if (effects.size() > 0) {
				ItemHaganeAromor.setPotionEffects(ret, effects);
			}
		}

    	return ret;
	}

    public Item getBaseEquipment(int max, int niji, int seiryu, int suzaku, int kirin, int byako, int genbu, Item base) {
		Item baseItem;
    	if (max == niji) {
			// にじシリーズ
			if (base == ItemCore.item_haganehelmet) {baseItem = ItemCore.item_nijihelmet;}
			else if(base == ItemCore.item_haganebody) {baseItem = ItemCore.item_nijibody;}
			else if(base == ItemCore.item_haganelegs) {baseItem = ItemCore.item_nijilegs;}
			else {baseItem = ItemCore.item_nijiboots;}
		}else if (max == seiryu) {
			// 青龍シリーズ
			if (base == ItemCore.item_haganehelmet) {baseItem = ItemCore.item_seiryuhelmet;}
			else if(base == ItemCore.item_haganebody) {baseItem = ItemCore.item_seiryubody;}
			else if(base == ItemCore.item_haganelegs) {baseItem = ItemCore.item_seiryulegs;}
			else {baseItem = ItemCore.item_seiryuboots;}
		}else if (max == suzaku) {
			// 朱雀シリーズ
			if (base == ItemCore.item_haganehelmet) {baseItem = ItemCore.item_suzakuhelmet;}
			else if(base == ItemCore.item_haganebody) {baseItem = ItemCore.item_suzakubody;}
			else if(base == ItemCore.item_haganelegs) {baseItem = ItemCore.item_suzakulegs;}
			else {baseItem = ItemCore.item_suzakuboots;}
		}else if (max == kirin) {
			// 麒麟シリーズ
			if (base == ItemCore.item_haganehelmet) {baseItem = ItemCore.item_kirinhelmet;}
			else if(base == ItemCore.item_haganebody) {baseItem = ItemCore.item_kirinbody;}
			else if(base == ItemCore.item_haganelegs) {baseItem = ItemCore.item_kirinlegs;}
			else {baseItem = ItemCore.item_kirinboots;}
		}else if (max == byako) {
			// 白虎シリーズ
			if (base == ItemCore.item_haganehelmet) {baseItem = ItemCore.item_byakohelmet;}
			else if(base == ItemCore.item_haganebody) {baseItem = ItemCore.item_byakobody;}
			else if(base == ItemCore.item_haganelegs) {baseItem = ItemCore.item_byakolegs;}
			else {baseItem = ItemCore.item_byakoboots;}
		}else {
			// 玄武シリーズ
			if (base == ItemCore.item_haganehelmet) {baseItem = ItemCore.item_genbuhelmet;}
			else if(base == ItemCore.item_haganebody) {baseItem = ItemCore.item_genbubody;}
			else if(base == ItemCore.item_haganelegs) {baseItem = ItemCore.item_genbulegs;}
			else {baseItem = ItemCore.item_genbuboots;}
		}
    	return baseItem;
    }
}
