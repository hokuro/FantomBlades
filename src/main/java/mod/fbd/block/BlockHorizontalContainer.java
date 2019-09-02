package mod.fbd.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public abstract class BlockHorizontalContainer extends BlockContainer {


	public static final DirectionProperty FACING =  BlockStateProperties.FACING;
	protected BlockHorizontalContainer(Block.Properties property) {
		super(property);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, EnumFacing.NORTH));
	}

	@Override
    protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder)
    {
		builder.add(FACING);
    }

	@Override
	public TileEntity createNewTileEntity(IBlockReader world) {
		return null;
	}

    @Override
    public void onBlockAdded(IBlockState state, World worldIn, BlockPos pos, IBlockState oldState) {
        this.setDefaultFacing(worldIn, pos, state);
    }

    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!worldIn.isRemote)
        {
            IBlockState iblockstate = worldIn.getBlockState(pos.north());
            IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
            IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
            IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = (EnumFacing)state.get(FACING);

            if (enumfacing == EnumFacing.NORTH && iblockstate.isFullCube())
            {
                enumfacing = EnumFacing.SOUTH;
            }
            else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullCube())
            {
                enumfacing = EnumFacing.NORTH;
            }
            else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullCube())
            {
                enumfacing = EnumFacing.EAST;
            }
            else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullCube())
            {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.with(FACING, enumfacing), 2);
        }
    }




    @Override
    public IBlockState rotate(IBlockState state, Rotation rot)
    {
        return state.with(FACING, rot.rotate((EnumFacing)state.get(FACING)));
    }

    @Override
    public IBlockState mirror(IBlockState state, Mirror mirrorIn)
    {
        return state.rotate(mirrorIn.toRotation((EnumFacing)state.get(FACING)));
    }


    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        worldIn.setBlockState(pos, state.with(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }

    public EnumFacing getFront(IBlockState state){
    	return state.get(FACING);
    }

    public IBlockState setFront(IBlockState state, EnumFacing front){
    	return state.with(FACING, front);
    }

    public IBlockState setFront(IBlockState state, int front){
    	return setFront(state, EnumFacing.byHorizontalIndex(front));
    }
}
