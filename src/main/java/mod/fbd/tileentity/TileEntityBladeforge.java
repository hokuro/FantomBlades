package mod.fbd.tileentity;

import mod.fbd.block.BlockBladeforge;
import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.item.ItemCore;
import mod.fbd.network.MessageHandler;
import mod.fbd.util.ModUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;

public class TileEntityBladeforge extends TileEntity implements IInventory, ITickableTileEntity {
	public static final String NAME = "bladeforge";

	public static final int FIELD_STATE = 0;
	public static final int FIELD_IRON = 1;
	public static final int FIELD_COAL = 2;
	public static final int FIELD_COUNT = 3;
	public static final int SMELTING_FINISH = 36000;
	public static final int MAX_IRON = 3456;
	public static final int MAX_COAL = 3456;

	private final NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(2,ItemStack.EMPTY);

	private int frame_state;
	private int tank_iron;
	private int tank_coal;

	private int smelting_cnt;
	private Direction airPomp;
	private int missing_cnt = 0;

	public boolean isRun(){
		return smelting_cnt > 0;
	}

	public TileEntityBladeforge(){
		super(Mod_FantomBlade.RegistryEvents.BLADEFORGE);
		frame_state = 0;
		tank_iron = 0;
		tank_coal = 0;
		smelting_cnt = 0;
		airPomp = Direction.UP;
	}

	public TileEntityBladeforge(BlockState state){
		this();
		frame_state = state.get(BlockBladeforge.FRAME);
	}

	public void onWork(){
		if (isRun()){
			if (this.frame_state == BlockBladeforge.STAGE_2){
				this.frame_state = BlockBladeforge.STAGE_3;
				// iブロックステートを性連中に設定
				BlockState state = world.getBlockState(pos);
				world.setBlockState(pos, ((BlockBladeforge)state.getBlock()).setFrame(state, BlockBladeforge.STAGE_3));
			}
			smelting_cnt--;
			if (smelting_cnt <= 0){
				// i精錬完了
				this.frame_state = BlockBladeforge.STAGE_4;
				// iブロックステートを精錬中に設定
				BlockState state = world.getBlockState(pos);
				world.setBlockState(pos, ((BlockBladeforge)state.getBlock()).setFrame(state, BlockBladeforge.STAGE_4));
				if (!world.isRemote) {
					// iクライアント側に通知
					MessageHandler.Send_MessageSetRunBladeforge((ServerWorld)world, pos,false);
				}
			}else{
				// i精錬中たまに上で炎をもやす
				if (!(world.getBlockState(pos.offset(Direction.UP)).getBlock() == Blocks.FIRE) && world.rand.nextDouble() < 0.4D){
					world.setBlockState(pos.offset(Direction.UP),Blocks.FIRE.getDefaultState());
				}
				if (!this.checkAirPomp(airPomp,
						((airPomp==Direction.EAST)?Direction.WEST:Direction.SOUTH),false)){
					missing_cnt++;
					// 5秒間の猶予
					if (missing_cnt > 100){
						// iポンプが見つからないので精錬失敗
						tank_iron = 0;
						tank_coal = 0;
						this.smelting_cnt = 0;
						missing_cnt = 0;

						// 精錬を停止
						BlockState state = world.getBlockState(pos);
						world.setBlockState(pos, ((BlockBladeforge)state.getBlock()).setFrame(state, BlockBladeforge.STAGE_5));
						if (!world.isRemote) {
							// iクライアント側に通知
							MessageHandler.Send_MessageSetRunBladeforge((ServerWorld)world, pos,false);
						}

						// i火を消す
						if ((world.getBlockState(pos.offset(Direction.UP)).getBlock() == Blocks.FIRE)){
							world.removeBlock(pos.offset(Direction.UP),false);
						}
					}
				}else{
					if (missing_cnt != 0){
						missing_cnt = 0;
					}
				}
			}
		}else{
			// i動いてないよー
			if (frame_state == BlockBladeforge.STAGE_1 || frame_state == BlockBladeforge.STAGE_2){
				// i砂鉄をタンクに詰める
				if (this.getStackInSlot(0).getCount() > 0){
					tank_iron+=this.getStackInSlot(0).getCount();
					if (tank_iron > MAX_IRON){
						int over = tank_iron - MAX_IRON;
						tank_iron = MAX_IRON;
						this.setInventorySlotContents(0, new ItemStack(ItemCore.item_satetu,over));
					}else{
						this.setInventorySlotContents(0, ItemStack.EMPTY);
					}
				}

				// i木炭をタンクに詰める
				if (this.getStackInSlot(1).getCount() > 0){
					tank_coal+=this.getStackInSlot(1).getCount();
					if (tank_coal > MAX_COAL){
						int over = tank_coal - MAX_COAL;
						tank_coal = MAX_COAL;
						this.setInventorySlotContents(1, new ItemStack(Items.CHARCOAL,over));
					}else{
						this.setInventorySlotContents(1, ItemStack.EMPTY);
					}
				}

				// i砂鉄の量が足りない状態
				if (frame_state == BlockBladeforge.STAGE_1){
					// i砂鉄と木炭の量は十分か(木炭以上の砂鉄、砂鉄の半分以上の木炭)
					if ((tank_iron >= tank_coal) && (tank_coal >= tank_iron/2) && (tank_iron > 128)){
						BlockState state = this.world.getBlockState(pos);
						if (state.getBlock() instanceof BlockBladeforge){
							// i精錬可能状態に遷移
							this.world.setBlockState(pos, ((BlockBladeforge)state.getBlock()).setFrame(state, BlockBladeforge.STAGE_2));
						}
					}
				}else if (frame_state==BlockBladeforge.STAGE_2){
					// i砂鉄と木炭の量は不十分になった？
					if (!((tank_iron >= tank_coal) && (tank_coal >= tank_iron/2) && (tank_iron > 128))){
						BlockState state = this.world.getBlockState(pos);
						if (state.getBlock() instanceof BlockBladeforge){
							// i精錬不可状態に遷移
							this.world.setBlockState(pos, ((BlockBladeforge)state.getBlock()).setFrame(state, BlockBladeforge.STAGE_1));
						}
					}
				}
			}
		}
	}

	public void StartSmelting(PlayerEntity player){
		if (frame_state == BlockBladeforge.STAGE_2){
			if (!checkAirPomp(Direction.EAST,Direction.WEST,true)){
				if ((!checkAirPomp(Direction.NORTH,Direction.SOUTH,true))){
					player.sendStatusMessage(new StringTextComponent("can't smelting: not found suitable airpomps"),false);
					return;
				}
			}
			// iタイマーを起動
			this.smelting_cnt = this.SMELTING_FINISH;
			if (!world.isRemote) {
				// iクライアント側に通知
				MessageHandler.Send_MessageSetRunBladeforge((ServerWorld)world, pos,true);
			}
		}else{
			player.sendStatusMessage(new StringTextComponent("can't smelting: state error"),false);
			if (world.getBlockState(pos.offset(Direction.UP)).getBlock() == Blocks.FIRE){
				// 上が燃えているなら鎮火する
				world.removeBlock(pos.offset(Direction.UP),false);
			}
		}
	}

	public void setRun(boolean isRun) {
		if (isRun){
			smelting_cnt = SMELTING_FINISH;
		}else{
			smelting_cnt = 0;
		}
	}

	public boolean checkAirPomp(Direction face1, Direction face2, boolean toRun){
		TileEntity ent1 = this.world.getTileEntity(pos.offset(face1));
		TileEntity ent2 = this.world.getTileEntity(pos.offset(face2));
		if (ent1 instanceof TileEntityAirPomp && ent2 instanceof TileEntityAirPomp){
			TileEntityAirPomp pomp1 = (TileEntityAirPomp)ent1;
			TileEntityAirPomp pomp2 = (TileEntityAirPomp)ent2;
			if (!pomp1.isRun(pos) && !pomp2.isRun(pos)){
				if (toRun){
					// 左右を未使用の鞴に挟まれているなら両鞴を稼働させる
					if (pomp1.setRun(this.pos) && pomp2.setRun(this.pos)){
						airPomp = face1;
						return true;
					}
				}else{
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void updateContainingBlockInfo() {
		super.updateContainingBlockInfo();
	}
//    @Override
//    public boolean shouldRefresh(World world, BlockPos pos, BlockState oldState, BlockState newSate)｛
//    	if (newSate.getBlock() instanceof BlockBladeforge){
//    		this.setField(FIELD_STATE, ((BlockBladeforge)newSate.getBlock()).getFrame(newSate));
//    	}
//        return false;
//    }

	@Override
    public void read(CompoundNBT compound){
		super.read(compound);
		frame_state = compound.getInt("state");
		tank_iron = compound.getInt("iron");
		tank_coal = compound.getInt("coal");
		smelting_cnt = compound.getInt("cnt");
		airPomp = Direction.byIndex(compound.getInt("airpomp"));
    }

	@Override
    public CompoundNBT write(CompoundNBT compound){
		compound = super.write(compound);
		compound.putInt("state",frame_state);
		compound.putInt("iron",tank_iron);
		compound.putInt("coal",tank_coal);
		compound.putInt("cnt",smelting_cnt);
		compound.putInt("airpomp", airPomp.getIndex());
        return compound;
    }

	@Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT cp = super.getUpdateTag();
        return this.write(cp);
    }

	@Override
    public void handleUpdateTag(CompoundNBT tag){
		super.handleUpdateTag(tag);
		this.read(tag);
    }

	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT CompoundNBT = new CompoundNBT();
        return new SUpdateTileEntityPacket(this.pos, 1,  this.write(CompoundNBT));
    }



	@Override
	public void clear() {
		this.setField(FIELD_IRON, 0);
		this.setField(FIELD_COAL, 0);
	}

	@Override
	public void tick() {
		if (!world.isRemote){
			onWork();
		}else{
			if (isRun()){
				this.smelting_cnt--;
			}
		}
	}

	@Override
	public int getSizeInventory() {
		return stacks.size();
	}

	@Override
	public boolean isEmpty() {
        for (ItemStack itemstack : this.stacks)
        {
            if (!itemstack.isEmpty())
            {
                return false;
            }
        }
        return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return stacks.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.stacks, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.stacks, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		stacks.set(index, stack);
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 0 && stack.getItem() == ItemCore.item_satetu){
			return true;
		}else if (index == 1 && (stack.getItem() == Items.CHARCOAL)){
			return true;
		}
		return false;
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity player) {
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}

	public int getField(int id) {
		int ret = 0;
		switch(id){
		case FIELD_STATE:
			ret = frame_state;
			break;
		case FIELD_IRON:
			ret = tank_iron;
			break;
		case FIELD_COAL:
			ret = tank_coal;
			break;
		case FIELD_COUNT:
			ret = smelting_cnt;
			break;
		}
		return ret;
	}

	public void setField(int id, int value) {
		switch(id){
		case FIELD_STATE :
			frame_state = value;
			break;
		case FIELD_IRON:
			tank_iron = value;
			break;
		case FIELD_COAL :
			tank_coal = value;
			break;
		case FIELD_COUNT:
			smelting_cnt = value;
			break;
		}
	}

	public int getFieldCount() {
		return 4;
	}


	public NonNullList<ItemStack> getDropItem(){
		NonNullList<ItemStack> ret = NonNullList.<ItemStack>create();
		int iron = this.getField(FIELD_IRON);
		int coal = this.getField(FIELD_COAL);
		if (this.getField(FIELD_STATE) == 0 || this.getField(FIELD_STATE) == 1){
			addStack(ret,ItemCore.item_satetu,iron);
			addStack(ret,Items.CHARCOAL, coal);
		}else if (this.getField(FIELD_STATE)==3){
			// i木炭と砂鉄の比率に差があるほど精錬効率が下がる
			iron = MathHelper.ceil((float)iron * Math.min((float)iron/(float)coal,0.75));
			int t1 = randomStack(iron,20,28,100);
			addStack(ret,ItemCore.item_tamahagane,t1);

			int zuku = randomStack(iron,20,30,100);
			addStack(ret,ItemCore.item_ironscrap,zuku);

			// i各欠片のできる数それぞれ別々に最大10個できる
			int rd = Math.min(iron/100,10)*2+1;
			int md = Math.min(rd/2, 10);
			int genbu = ModUtil.random(rd)-10;
			if (genbu > 0){
				addStack(ret, ItemCore.item_bladepiece_genbu, genbu);
			}
			int suzaku = ModUtil.random(rd)-10;
			if (suzaku > 0){
				addStack(ret, ItemCore.item_bladepiece_suzaku, suzaku);
			}
			int seiryu = ModUtil.random(rd)-10;
			if (seiryu > 0){
				addStack(ret, ItemCore.item_bladepiece_seiryu, seiryu);
			}
			int byako = ModUtil.random(rd)-10;
			if (byako > 0){
				addStack(ret, ItemCore.item_bladepiece_byako, byako);
			}

			int kirin = ModUtil.random(rd)-10;
			if (kirin > 0){
				addStack(ret, ItemCore.item_bladepiece_kirin, kirin);
			}
		}
		this.setField(FIELD_IRON, 0);
		this.setField(FIELD_COAL, 0);
		return ret;
	}

	public void addStack(NonNullList<ItemStack> lst, Item item, int cnt){
		while(cnt > 0){
			lst.add(new ItemStack(item,cnt>=64?64:cnt));
			cnt-=64;
		}
	}

	public int randomStack(int base, int rateMin, int rateMax, int malt){
		return base*(this.world.rand.nextInt(rateMax+1-rateMin)+rateMin) / malt;
	}

}
