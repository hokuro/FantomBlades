package mod.fbd.tileentity;

import mod.fbd.block.BlockBladeforge;
import mod.fbd.block.BlockHorizontalContainer;
import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.item.ItemBladePiece.EnumBladePieceType;
import mod.fbd.item.ItemCore;
import mod.fbd.network.MessageSetRunBladeforge;
import mod.fbd.util.ModUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class TileEntityBladeforge extends TileEntity implements IInventory, ITickable {

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
	private EnumFacing airPomp;
	private int missing_cnt = 0;

	public boolean isRun(){
		return smelting_cnt > 0;
	}

	public TileEntityBladeforge(){
		frame_state = 0;
		tank_iron = 0;
		tank_coal = 0;
		smelting_cnt = 0;
		airPomp = EnumFacing.UP;
	}

	public TileEntityBladeforge(IBlockState meta){
		this();
		frame_state = meta.get(BlockHorizontalContainer.FACING).getHorizontalIndex();
	}

	public void onWork(){
		if (isRun()){
			if (this.frame_state == BlockBladeforge.STAGE_2){
				this.frame_state = BlockBladeforge.STAGE_3;
				// ブロックステートを性連中に設定
				IBlockState state = world.getBlockState(pos);
				world.setBlockState(pos, ((BlockBladeforge)state.getBlock()).setFrame(state, BlockBladeforge.STAGE_3));
			}
			smelting_cnt--;
			if (smelting_cnt <= 0){
				// 精錬完了
				this.frame_state = BlockBladeforge.STAGE_4;
				// ブロックステートを精錬中に設定
				IBlockState state = world.getBlockState(pos);
				world.setBlockState(pos, ((BlockBladeforge)state.getBlock()).setFrame(state, BlockBladeforge.STAGE_4));
				// クライアント側に通知
				Mod_FantomBlade.Net_Instance.sendToAll(new MessageSetRunBladeforge(pos,false));
			}else{
				// 精錬中たまに上で炎をもやす
				if (!(world.getBlockState(pos.offset(EnumFacing.UP)).getBlock() == Blocks.FIRE) && world.rand.nextDouble() < 0.4D){
					world.setBlockState(pos.offset(EnumFacing.UP),Blocks.FIRE.getDefaultState());
				}
				if (!this.checkAirPomp(airPomp,
						((airPomp==EnumFacing.EAST)?EnumFacing.WEST:EnumFacing.SOUTH),false)){
					missing_cnt++;
					// 5秒間の猶予
					if (missing_cnt > 100){
						// ポンプが見つからないので精錬失敗
						tank_iron = 0;
						tank_coal = 0;
						this.smelting_cnt = 0;
						missing_cnt = 0;

						// 精錬を停止
						IBlockState state = world.getBlockState(pos);
						world.setBlockState(pos, ((BlockBladeforge)state.getBlock()).setFrame(state, BlockBladeforge.STAGE_5));
						Mod_FantomBlade.Net_Instance.sendToAll(new MessageSetRunBladeforge(pos,false));

						// 火を消す
						if ((world.getBlockState(pos.offset(EnumFacing.UP)).getBlock() == Blocks.FIRE)){
							world.setBlockToAir(pos.offset(EnumFacing.UP));
						}
					}
				}else{
					if (missing_cnt != 0){
						missing_cnt = 0;
					}
				}
			}
		}else{
			// 動いてないよー
			if (frame_state == BlockBladeforge.STAGE_1 || frame_state == BlockBladeforge.STAGE_2){
				// 砂鉄をタンクに詰める
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

				// 木炭をタンクに詰める
				if (this.getStackInSlot(1).getCount() > 0){
					tank_coal+=this.getStackInSlot(1).getCount();
					if (tank_coal > MAX_COAL){
						int over = tank_coal - MAX_COAL;
						tank_coal = MAX_COAL;
						this.setInventorySlotContents(1, new ItemStack(Items.COAL,over,1));
					}else{
						this.setInventorySlotContents(1, ItemStack.EMPTY);
					}
				}

				// 砂鉄の量が足りない状態
				if (frame_state == BlockBladeforge.STAGE_1){
					// 砂鉄と木炭の量は十分か(木炭以上の砂鉄、砂鉄の半分以上の木炭)
					if ((tank_iron >= tank_coal) && (tank_coal >= tank_iron/2) && (tank_iron > 128)){
						IBlockState state = this.world.getBlockState(pos);
						if (state.getBlock() instanceof BlockBladeforge){
							// 精錬可能状態に遷移
							this.world.setBlockState(pos, ((BlockBladeforge)state.getBlock()).setFrame(state, BlockBladeforge.STAGE_2));
						}
					}
				}else if (frame_state==BlockBladeforge.STAGE_2){
					// 砂鉄と木炭の量は不十分になった？
					if (!((tank_iron >= tank_coal) && (tank_coal >= tank_iron/2) && (tank_iron > 128))){
						IBlockState state = this.world.getBlockState(pos);
						if (state.getBlock() instanceof BlockBladeforge){
							// 精錬不可状態に遷移
							this.world.setBlockState(pos, ((BlockBladeforge)state.getBlock()).setFrame(state, BlockBladeforge.STAGE_1));
						}
					}
				}
			}
		}
	}

	public void StartSmelting(EntityPlayer player){
		if (frame_state == BlockBladeforge.STAGE_2){
			if (!checkAirPomp(EnumFacing.EAST,EnumFacing.WEST,true)){
				if ((!checkAirPomp(EnumFacing.NORTH,EnumFacing.SOUTH,true))){
					player.sendStatusMessage(new TextComponentString("can't smelting: not found suitable airpomps"),false);
					return;
				}
			}
			// タイマーを起動
			this.smelting_cnt = this.SMELTING_FINISH;
			Mod_FantomBlade.Net_Instance.sendToAll(new MessageSetRunBladeforge(this.pos,true));
		}else{
			player.sendStatusMessage(new TextComponentString("can't smelting: state error"),false);
			if (world.getBlockState(pos.offset(EnumFacing.UP)).getBlock() == Blocks.FIRE){
				// 上が燃えているなら鎮火する
				world.setBlockToAir(pos.offset(EnumFacing.UP));
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

	public boolean checkAirPomp(EnumFacing face1, EnumFacing face2, boolean toRun){
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
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
    {
    	if (newSate.getBlock() instanceof BlockBladeforge){
    		this.setField(FIELD_STATE, ((BlockBladeforge)newSate.getBlock()).getFrame(newSate));
    	}
        return false;
    }

	@Override
    public void readFromNBT(NBTTagCompound compound)
    {
		super.readFromNBT(compound);
		frame_state = compound.getInteger("state");
		tank_iron = compound.getInteger("iron");
		tank_coal = compound.getInteger("coal");
		smelting_cnt = compound.getInteger("cnt");
		airPomp = EnumFacing.getFront(compound.getInteger("airpomp"));
    }

	@Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
		compound = super.writeToNBT(compound);
		compound.setInteger("state",frame_state);
		compound.setInteger("iron",tank_iron);
		compound.setInteger("coal",tank_coal);
		compound.setInteger("cnt",smelting_cnt);
		compound.setInteger("airpomp", airPomp.getIndex());
        return compound;
    }

	@Override
    public NBTTagCompound getUpdateTag()
    {
        NBTTagCompound cp = super.getUpdateTag();
        return this.writeToNBT(cp);
    }

	@Override
    public void handleUpdateTag(NBTTagCompound tag)
    {
		super.handleUpdateTag(tag);
		this.readFromNBT(tag);
    }

	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
    {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        return new SPacketUpdateTileEntity(this.pos, 1,  this.writeToNBT(nbtTagCompound));
    }


	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public void update() {

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
	public boolean isUsableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void closeInventory(EntityPlayer player) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 0 && stack.getItem() == ItemCore.item_satetu){
			return true;
		}else if (index == 1 && (stack.getItem() == Items.COAL && stack.getMetadata() == 1)){
			return true;
		}
		return false;
	}

	@Override
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

	@Override
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

	@Override
	public int getFieldCount() {
		return 4;
	}

	@Override
	public void clear() {
		this.setField(FIELD_IRON, 0);
		this.setField(FIELD_COAL, 0);
	}


	public NonNullList<ItemStack> getDropItem(){
		NonNullList<ItemStack> ret = NonNullList.<ItemStack>create();
		int iron = this.getField(FIELD_IRON);
		int coal = this.getField(FIELD_COAL);
		if (this.getField(FIELD_STATE) == 0 || this.getField(FIELD_STATE) == 1){
			addStack(ret,ItemCore.item_satetu,0,iron);
			addStack(ret,Items.COAL,1,coal);
		}else if (this.getField(FIELD_STATE)==3){
			// 木炭と砂鉄の比率に差があるほど精錬効率が下がる
			iron = MathHelper.ceil((float)iron * Math.min((float)iron/(float)coal,0.75));
			int t1 = randomStack(iron,20,28,1000);
			addStack(ret,ItemCore.item_tamahagane,0,t1);

			int t2 = randomStack(iron,50,58,1000);
			addStack(ret,ItemCore.item_tamahagane,1,t2);

			int t3 = randomStack(iron,70,78,1000);
			addStack(ret,ItemCore.item_tamahagane,2,t3);

			int t4 = randomStack(iron,100,108,1000);
			addStack(ret,ItemCore.item_tamahagane,3,t4);

			int zuku = randomStack(iron,20,30,100);
			addStack(ret,ItemCore.item_zuku,0,zuku);

			int noro = randomStack(coal,14,16,10);
			addStack(ret,ItemCore.item_noro,0,noro);


			// 各欠片のできる数それぞれ別々に最大10個できる
			int rd = Math.min(iron/100,10)*2+1;
			int md = Math.min(rd/2, 10);
			int genbu = ModUtil.random(rd)-10;
			if (genbu > 0){
				addStack(ret, ItemCore.item_bladepiece,EnumBladePieceType.GENBU.getIndex(),genbu);
			}
			int suzaku = ModUtil.random(rd)-10;
			if (suzaku > 0){
				addStack(ret, ItemCore.item_bladepiece,EnumBladePieceType.SUZAKU.getIndex(),suzaku);
			}
			int seiryu = ModUtil.random(rd)-10;
			if (seiryu > 0){
				addStack(ret, ItemCore.item_bladepiece,EnumBladePieceType.SEIRYU.getIndex(),seiryu);
			}
			int byako = ModUtil.random(rd)-10;
			if (byako > 0){
				addStack(ret, ItemCore.item_bladepiece,EnumBladePieceType.BYAKO.getIndex(),byako);
			}
		}
		this.setField(FIELD_IRON, 0);
		this.setField(FIELD_COAL, 0);
		return ret;
	}

	public void addStack(NonNullList<ItemStack> lst, Item item, int meta, int cnt){
		while(cnt > 0){
			lst.add(new ItemStack(item,cnt>=64?64:cnt,meta));
			cnt-=64;
		}
	}

	public int randomStack(int base, int rateMin, int rateMax, int malt){
		return base*(this.world.rand.nextInt(rateMax+1-rateMin)+rateMin) / malt;
	}

}
