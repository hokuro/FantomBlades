package mod.fbd.block;

import mod.fbd.tileentity.TileEntityAirPomp;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockAirPomp extends BlockContainer {

	protected BlockAirPomp(Block.Properties property) {
		super(property);
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader world) {
		return new TileEntityAirPomp();
	}

	@Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

	@Override
	public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileEntity ent = worldIn.getTileEntity(pos);
        if ( ent instanceof TileEntityAirPomp){
        	if (!((TileEntityAirPomp)ent).isRun(null)){
        		((TileEntityAirPomp)ent).searchForge();
        	}
    		return true;
        }
        return false;
    }

	private static final VoxelShape colligeBox = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 0.5625D, 1.0D);	// SOUTH
	@Override
	public VoxelShape getShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
		return colligeBox;
	}

    @Override
	public boolean hasCustomBreakingProgress(IBlockState state){
    	return true;
    }
}
