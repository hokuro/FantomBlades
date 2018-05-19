package mod.fbd.block;

import mod.fbd.tileentity.TileEntityAirPomp;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAirPomp extends BlockContainer {

	protected BlockAirPomp() {
		super(Material.GROUND);
		this.setHardness(0.5F);
		this.setSoundType(SoundType.WOOD);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityAirPomp();
	}

	@Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

	@Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

	@Override
    public boolean isFullBlock(IBlockState state)
    {
        return false;
    }

	@Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        TileEntity ent = worldIn.getTileEntity(pos);
        if ( ent instanceof TileEntityAirPomp){
        	if (!((TileEntityAirPomp)ent).isRun(null)){
        		((TileEntityAirPomp)ent).searchForge();
        	}
    		return true;
        }
        return false;
    }

	private static final AxisAlignedBB colligeBox = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5625D, 1.0D);	// SOUTH
	@Override
	 public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
		return colligeBox;
	}

    @Override
	public boolean hasCustomBreakingProgress(IBlockState state){
    	return true;
    }
}
