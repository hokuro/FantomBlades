package mod.fbd.tileentity;

import mod.fbd.block.BlockCore;
import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.core.log.ModLog;
import mod.fbd.network.MessageSetRunAirPomp;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityAirPomp extends TileEntity implements ITickable {

	public static final String NAME = "airpomp";

	private boolean isBack;
	private EnumFacing facing;
	private BlockPos targetForge;
	private int count;

	public TileEntityAirPomp(){
		super(Mod_FantomBlade.RegistryEvents.AIRPOMP);
		facing = EnumFacing.DOWN;
		targetForge = null;
		count = 30;
		isBack = false;
	}

	public int getCount(){
		return count;
	}

	@Override
	public void update() {
		if (world.isRemote){
			if (isRun(null)){
				if (isBack){
					count--;
				}else{
					count++;
				}
				if (count <= 0 || count > 59){
					isBack = !isBack;
				}
			}else{
				if (count != 30){
					if (count < 30){
						count++;
					}else{
						count--;
					}
				}
			}
		}
	}

	public boolean isRun(BlockPos pos){
		if (targetForge != null){
			TileEntity te = world.getTileEntity(targetForge);
			if (te instanceof TileEntityBladeforge){
				// 今くっついてる炉の状態が稼働状態
				return ((TileEntityBladeforge)te).isRun() && (pos == null ||(pos!=null && !targetForge.equals(pos)));
			}
		}
		// まだターゲットがいなかったり、ターゲットがいなかったりターゲットが炉じゃなければ
		// 未稼働
		return false;
	}


	public boolean setRun(BlockPos requestPos){
		facing = EnumFacing.DOWN;
		TileEntity te = world.getTileEntity(requestPos);
		if (te instanceof TileEntityBladeforge){
			targetForge = requestPos;
			if (requestPos.equals(this.pos.offset(EnumFacing.EAST))){
				facing = EnumFacing.EAST;
			}else if (requestPos.equals(this.pos.offset(EnumFacing.WEST))){
				facing = EnumFacing.WEST;
			}else if (requestPos.equals(this.pos.offset(EnumFacing.NORTH))){
				facing = EnumFacing.NORTH;
			}else if (requestPos.equals(this.pos.offset(EnumFacing.SOUTH))){
				facing = EnumFacing.SOUTH;
			}
			if (facing != EnumFacing.UP){
				Mod_FantomBlade.Net_Instance.sendToAll(new MessageSetRunAirPomp(this.pos, requestPos ,facing.getIndex()));
				return true;
			}
		}
		targetForge = null;
		return false;
	}

	public int getFace(){
		return this.facing.getHorizontalIndex();
	}

	public void searchForge() {
		facing = EnumFacing.DOWN;
		targetForge = null;
		if (world.getTileEntity(pos.offset(EnumFacing.EAST)) instanceof TileEntityBladeforge
				&& world.getBlockState(pos.offset(EnumFacing.EAST)).getBlock() == BlockCore.block_bladeforge){
			this.targetForge = pos.offset(EnumFacing.EAST);
			this.facing = EnumFacing.EAST;
		}else if (world.getTileEntity(pos.offset(EnumFacing.WEST)) instanceof TileEntityBladeforge
				&& world.getBlockState(pos.offset(EnumFacing.WEST)).getBlock() == BlockCore.block_bladeforge){
			this.targetForge = pos.offset(EnumFacing.WEST);
			this.facing = EnumFacing.WEST;
		}else if (world.getTileEntity(pos.offset(EnumFacing.NORTH)) instanceof TileEntityBladeforge
				&& world.getBlockState(pos.offset(EnumFacing.NORTH)).getBlock() == BlockCore.block_bladeforge){
			this.targetForge = pos.offset(EnumFacing.NORTH);
			this.facing = EnumFacing.NORTH;
		}else if (world.getTileEntity(pos.offset(EnumFacing.SOUTH)) instanceof TileEntityBladeforge
				&& world.getBlockState(pos.offset(EnumFacing.SOUTH)).getBlock() == BlockCore.block_bladeforge){
			this.targetForge = pos.offset(EnumFacing.SOUTH);
			this.facing = EnumFacing.SOUTH;
		}

		ModLog.log().debug("Serch facing :" + facing.toString() + " idx :" + facing.getHorizontalIndex() );
	}


	@Override
    public void readFromNBT(NBTTagCompound compound)
    {
		super.readFromNBT(compound);
		try{
			if (compound.getBoolean("hasTarget")){
				int x = compound.getInteger("xforge");
				int y = compound.getInteger("yforge");
				int z = compound.getInteger("zforge");
				this.targetForge = new BlockPos(x,y,z);
			}else{
				this.targetForge = null;
			}

			int face = compound.getInteger("face");
			this.facing = EnumFacing.getFront(face);
			this.count = compound.getInteger("count");
		}catch(Exception ex){
			ModLog.log().fatal(ex.getMessage());
		}
    }

	@Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
		try{
			compound = super.writeToNBT(compound);
			if (this.targetForge != null){
				compound.setBoolean("hasTarget", true);
				compound.setInteger("xforge", targetForge.getX());
				compound.setInteger("yforge", targetForge.getY());
				compound.setInteger("zforge", targetForge.getZ());
			}else{
				compound.setBoolean("hasTarget", false);
			}
			compound.setInteger("face",this.facing.getIndex());
			compound.setInteger("count", count);
		}catch(Exception ex){
			ModLog.log().fatal(ex.getMessage());
		}
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





}
