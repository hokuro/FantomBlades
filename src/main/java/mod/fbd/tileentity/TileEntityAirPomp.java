package mod.fbd.tileentity;

import mod.fbd.block.BlockCore;
import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.core.log.ModLog;
import mod.fbd.network.MessageHandler;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class TileEntityAirPomp extends TileEntity implements ITickableTileEntity {

	public static final String NAME = "airpomp";

	private boolean isBack;
	private Direction facing;
	private BlockPos targetForge;
	private int count;

	public TileEntityAirPomp(){
		super(Mod_FantomBlade.RegistryEvents.AIRPOMP);
		facing = Direction.DOWN;
		targetForge = null;
		count = 30;
		isBack = false;
	}

	public int getCount(){
		return count;
	}

	@Override
	public void tick() {
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
		// iまだターゲットがいなかったり、ターゲットがいなかったりターゲットが炉じゃなければ
		// i未稼働
		return false;
	}


	public boolean setRun(BlockPos requestPos){
		facing = Direction.DOWN;
		TileEntity te = world.getTileEntity(requestPos);
		if (te instanceof TileEntityBladeforge){
			targetForge = requestPos;
			if (requestPos.equals(this.pos.offset(Direction.EAST))){
				facing = Direction.EAST;
			}else if (requestPos.equals(this.pos.offset(Direction.WEST))){
				facing = Direction.WEST;
			}else if (requestPos.equals(this.pos.offset(Direction.NORTH))){
				facing = Direction.NORTH;
			}else if (requestPos.equals(this.pos.offset(Direction.SOUTH))){
				facing = Direction.SOUTH;
			}
			if (facing != Direction.UP){
				if (!world.isRemote) {
					MessageHandler.Send_MessageSetRunAirPomp((ServerWorld)world, this.pos, requestPos ,facing.getIndex());
				}
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
		facing = Direction.DOWN;
		targetForge = null;
		if (world.getTileEntity(pos.offset(Direction.EAST)) instanceof TileEntityBladeforge
				&& world.getBlockState(pos.offset(Direction.EAST)).getBlock() == BlockCore.block_bladeforge){
			this.targetForge = pos.offset(Direction.EAST);
			this.facing = Direction.EAST;
		}else if (world.getTileEntity(pos.offset(Direction.WEST)) instanceof TileEntityBladeforge
				&& world.getBlockState(pos.offset(Direction.WEST)).getBlock() == BlockCore.block_bladeforge){
			this.targetForge = pos.offset(Direction.WEST);
			this.facing = Direction.WEST;
		}else if (world.getTileEntity(pos.offset(Direction.NORTH)) instanceof TileEntityBladeforge
				&& world.getBlockState(pos.offset(Direction.NORTH)).getBlock() == BlockCore.block_bladeforge){
			this.targetForge = pos.offset(Direction.NORTH);
			this.facing = Direction.NORTH;
		}else if (world.getTileEntity(pos.offset(Direction.SOUTH)) instanceof TileEntityBladeforge
				&& world.getBlockState(pos.offset(Direction.SOUTH)).getBlock() == BlockCore.block_bladeforge){
			this.targetForge = pos.offset(Direction.SOUTH);
			this.facing = Direction.SOUTH;
		}

		ModLog.log().debug("Serch facing :" + facing.toString() + " idx :" + facing.getHorizontalIndex() );
	}


	@Override
    public void read(CompoundNBT compound) {
		super.read(compound);
		try{
			if (compound.getBoolean("hasTarget")){
				int x = compound.getInt("xforge");
				int y = compound.getInt("yforge");
				int z = compound.getInt("zforge");
				this.targetForge = new BlockPos(x,y,z);
			}else{
				this.targetForge = null;
			}

			int face = compound.getInt("face");
			this.facing = Direction.byIndex(face);
			this.count = compound.getInt("count");
		}catch(Exception ex){
			ModLog.log().fatal(ex.getMessage());
		}
    }

	@Override
    public CompoundNBT write(CompoundNBT compound) {
		try{
			compound = super.write(compound);
			if (this.targetForge != null){
				compound.putBoolean("hasTarget", true);
				compound.putInt("xforge", targetForge.getX());
				compound.putInt("yforge", targetForge.getY());
				compound.putInt("zforge", targetForge.getZ());
			}else{
				compound.putBoolean("hasTarget", false);
			}
			compound.putInt("face",this.facing.getIndex());
			compound.putInt("count", count);
		}catch(Exception ex){
			ModLog.log().fatal(ex.getMessage());
		}
        return compound;
    }

	@Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT cp = super.getUpdateTag();
        return this.write(cp);
    }

	@Override
    public void handleUpdateTag(CompoundNBT tag) {
		super.handleUpdateTag(tag);
		this.read(tag);
    }

	@Override
	public SUpdateTileEntityPacket getUpdatePacket(){
        CompoundNBT CompoundNBT = new CompoundNBT();
        return new SUpdateTileEntityPacket(this.pos, 1,  this.write(CompoundNBT));
    }
}
