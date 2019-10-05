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
import mod.fbd.inventory.ContainerBladeSmith;
import mod.fbd.inventory.InventoryBladeSmith;
import mod.fbd.inventory.InventorySmithBase;
import mod.fbd.item.ItemCore;
import mod.fbd.item.katana.AbstractItemKatana;
import mod.fbd.item.katana.ItemKatana;
import mod.fbd.item.piece.ItemBladePiece.EnumBladePieceType;
import mod.fbd.util.ModUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CauldronBlock;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.PotionUtils;
import net.minecraft.tileentity.FurnaceTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntityBladeSmith extends EntitySmithBase {
	public static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation("fbd","textures/entity/bladesmith_default.png");
	public static final String NAME= "bladesmith";
	public static final ResourceLocation loot = new ResourceLocation("fbd:" + NAME);

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
	private final InventoryBladeSmith smithInventory;
    private int nextExp=0;


    public EntityBladeSmith(World worldIn) {
        this(Mod_FantomBlade.RegistryEvents.BLADESMITH, worldIn);
    }

	public EntityBladeSmith(FMLPlayMessages.SpawnEntity packet, World world) {
		this(Mod_FantomBlade.RegistryEvents.BLADESMITH, world);
	}

    public EntityBladeSmith(EntityType<? extends CreatureEntity> etype, World worldIn) {
        super(etype, worldIn);
        this.smithInventory = new InventoryBladeSmith(this);
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
    	if (player.isSneaking()) {return false;}
    	if (!player.world.isRemote){
        	if (!Dw_ISWORK()){
        		ModLog.log().debug("open gui entity id:"+this.getEntityId());
        		NetworkHooks.openGui((ServerPlayerEntity)player,
            			new INamedContainerProvider() {
        					@Override
        					@Nullable
        					public Container createMenu(int id, PlayerInventory playerInv, PlayerEntity player) {
        						return new ContainerBladeSmith(id, playerInv, smithInventory);
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


    // 作業を開始する
    @Override
    public void startWork(World world,BlockPos pos, ItemStack katana){
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
    	this.smithInventory.setResult(katana);
    }

    // 作業が開始できるか確認
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

    // 作業を終了する
    @Override
    public void finishWork(World world,BlockPos pos, boolean success){
    	// ブロックをもとに戻す
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
    	int clear = InventoryBladeSmith.INV_05_RESULT;
		if (!success){
			// 失敗した場合できるはずだった刀もクリア
			clear = InventoryBladeSmith.INV_06_TOOL_BIGHAMMER;
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
			ret = false;
		}

		// ブロックの種類と状態のチェック
		if (!checkBlockState(state1, pos1) ||
			!checkBlockState(state2, pos2) ||
			!checkBlockState(state3, pos3) ||
			!checkBlockState(state4, pos4)) {
			ret = false;
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
		return this.createBlade();
	}

	@Override
	public void repair() {
		this.smithInventory.repair();
	}

	@Override
	public String getSmithName() {
		return NAME;
	}

    public ItemStack createBlade(){
		ItemStack ret = ItemStack.EMPTY;
		int count = 0;
		int level = getLevel();
		boolean exblade = false;
		int tamahagane = this.smithInventory.getTamahagane(ItemCore.item_tamahagane);
		if (tamahagane <= 0){
			// ざいりょうがねーよん
			return ret;
		}

		EnumBladePieceType makeItem = EnumBladePieceType.NORMAL;

		if (level >= 10){
			// 欠片の数を取得
			int[] piece = this.smithInventory.getBladePiece();
			int seiryu = piece[EnumBladePieceType.SEIRYU.getIndex()];
			int suzaku = piece[EnumBladePieceType.SUZAKU.getIndex()];
			int kirin = piece[EnumBladePieceType.KIRIN.getIndex()];
			int byako = piece[EnumBladePieceType.BYAKO.getIndex()];
			int genbu = piece[EnumBladePieceType.GENBU.getIndex()];
			int niji = piece[EnumBladePieceType.NIJI.getIndex()];

			// 五行の強弱をつける
			seiryu = seiryu + genbu - byako;
			suzaku = suzaku + seiryu - genbu;
			kirin = kirin + suzaku - seiryu;
			byako = kirin + suzaku - seiryu;
			genbu = genbu + byako - kirin;

			// 欠片の最大数を計算
			int max = ModUtil.IntMax(niji, seiryu, suzaku, kirin, byako, genbu);
			if (max > 0) {
				EnumBladePieceType pieceType = EnumBladePieceType.NORMAL;
				if (max == niji) {
					// にじが一番多ければにじにする
					pieceType = EnumBladePieceType.NIJI;
				} else if (max == kirin) {
					pieceType = EnumBladePieceType.KIRIN;
				} else if (max == byako) {
					pieceType = EnumBladePieceType.BYAKO;
				} else if (max == genbu) {
					pieceType = EnumBladePieceType.GENBU;
				} else if (max == seiryu) {
					pieceType = EnumBladePieceType.SEIRYU;
				} else {
					pieceType = EnumBladePieceType.SUZAKU;
				}
				//　10レベル以上のスミスでかつ欠片が設定されている
				if (level >= 250){
					// レベル250以上の場合確実に特殊刀ができる
					exblade = true;
					makeItem = pieceType;
				}else{
					// 特殊刀ができる確率を計算 最大70%
					// レベル/10*5(5% からstart 70%になるのはLv140以上)
					// 最初の1スタック分は 欠片/10.0F*3.0F(0.3% ～ 19.2%分の補助
					// 2スタック目以降は欠片/64%分の補助(最大2%)
					if (Math.min((level/10.0F*5.0F) + ((max%65)/10.0F*5.0F) + Math.max((max-64)/64,0), 70.0F) >= ModUtil.random(101)){
						exblade = true;
						makeItem = pieceType;

					}
				}
			}
		}

    	// 経験値
    	nextExp = makeItem.getBaseExp() + ModUtil.random(makeItem.getOptionExp());
		if (exblade){
			switch(makeItem){
			case BYAKO:
				ret = AbstractItemKatana.getDefaultStack((AbstractItemKatana)ItemCore.item_katana_byako);
				break;
			case GENBU:
				ret = AbstractItemKatana.getDefaultStack((AbstractItemKatana)ItemCore.item_katana_genbu);
				break;
			case KIRIN:
				ret = AbstractItemKatana.getDefaultStack((AbstractItemKatana)ItemCore.item_katana_kirin);
				break;
			case NIJI:
				ret = AbstractItemKatana.getDefaultStack((AbstractItemKatana)ItemCore.item_katana_niji);
				break;
			case SEIRYU:
				ret = AbstractItemKatana.getDefaultStack((AbstractItemKatana)ItemCore.item_katana_seiryu);
				break;
			case SUZAKU:
				ret = AbstractItemKatana.getDefaultStack((AbstractItemKatana)ItemCore.item_katana_suzaku);
				break;
			default:
				break;
			}
			int range = 0;
			if (level < 20) {range = 31;}
			else if (level < 40) {range = 21;}
			else if (level < 60) {range = 11;}
			// 最小10 最大 40
			int rd = ModUtil.random(range)+10;

			// ランダム補正
			int hosei = ModUtil.random(tamahagane/rd);

			// 補正分攻撃力と耐久力を上げる
			float damage = AbstractItemKatana.getAttackDamage(ret);
			damage *= hosei==0?1:hosei;
			AbstractItemKatana.setAttackDamage(ret, damage);

			int endurance = AbstractItemKatana.getEndurance(ret);
			endurance *= hosei==0?1:hosei;
			AbstractItemKatana.setEndurance(ret, endurance);

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

			// スピード補正(速度を0.0~0.9の範囲で下げる)
			attackSpeed = MathHelper.clamp(attackSpeed + ModUtil.random(10)/10, -3.0F, -0.5F);
			// 耐久度補正(玉鋼の数の10倍の耐久力)
			endurance = endurance + tamahagane * 10;
			// 耐久度補正(1.2倍)
			endurance = endurance *1.2F;

			ret = new ItemStack(ItemCore.item_katana);
			ItemKatana.setMaxLevel(ret,(int)maxLevel);
			ItemKatana.setAttackDamage(ret,attackDamage);
			ItemKatana.setAttackSpeed(ret,attackSpeed);
			ItemKatana.setEndurance(ret, (int)endurance);
			ItemKatana.setExp(ret,0);
			ItemKatana.setRustValue(ret, 0);
		}

		if (level >= 5) {
			// レベル5以上ならなら エンチャント効果をつける
			List<ItemStack> encbook = this.smithInventory.getEnchangedBooks();
			// レベル80で5つ確定、レベル60で4つ確定、レベル40で3つ確定、レベル20で2つ確定、レベル10で1つ確定
			int confirm = level >= 80?5:level>=60?4:level>=40?3:level>=20?2:level>10?1:0;
			Map<Enchantment, Integer> encMap = Maps.newHashMap();

			// エンチャントチェック用のゾンビ
			LivingEntity living = EntityType.ZOMBIE.create(world);
			living.setHeldItem(Hand.MAIN_HAND, Items.STICK.getDefaultInstance());

			// 登録用マップを作る
			for (ItemStack stack: encbook) {
				for (Entry<Enchantment,Integer> enc : EnchantmentHelper.getEnchantments(stack).entrySet()) {
					// メインハンドにエンチャントできるか確認
					Enchantment enchant = enc.getKey();
					int encLevel = enc.getValue();
					if (enchant.getEntityEquipment(living).size() != 0) {
						// 同じエンチャントが登録済みの場合レベルを高い方に更新
						if (encMap.containsKey(enchant)) {
							if (encMap.get(enchant) < encLevel) {
								encMap.put(enchant, encLevel);
							}
						}else {
							// 確定の残りあり又はレベル200越えもしくは特殊刀の場合100%成功
							if (confirm > 0 || level > 200 || exblade) {
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
				// 朱雀刀の場合flameのエンチャントがデフォルトであるのでここでマップに追加
				encMap.putAll(EnchantmentHelper.getEnchantments(ret));
				// エンチャント追加
				EnchantmentHelper.setEnchantments(encMap, ret);
			}
		}
		if (level >= 10) {
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
							// 10%の確率でエンチャント成功(特殊刀の場合+10%)
							if (ModUtil.random(1000) < (100 + (exblade?100:0))) {
								effects.add(new EffectInstance(effect2.getPotion(),effect2.getDuration(), effect2.getAmplifier()));
								confirm--;
							}
						}
					}
				}
			}

			// エフェクト登録
			if (effects.size() > 0) {
				// 既存のエフェクトがある場合は上書き
				AbstractItemKatana.getPotionEffects(ret).forEach((effect2)->{
					boolean update = false;
					int updateIdx = 0;
					for (int i = 0; i < effects.size() && !update; i++) {
						if (effects.get(i).getPotion() == effect2.getPotion()) {
							// 同じ効果は既存を上書き
							updateIdx = i;
							update = true;
						}
					}
					if (update) {
						// エフェクト更新
						effects.set(updateIdx, new EffectInstance(effect2.getPotion(),effect2.getDuration(), effect2.getAmplifier()));
					} else {
						// エフェクト追加
						effects.add(new EffectInstance(effect2.getPotion(),effect2.getDuration(), effect2.getAmplifier()));
					}
				});
				AbstractItemKatana.setPotionEffects(ret, effects);
			}
		}
    	return ret;
	}
}
