package mod.fbd.block;

import mod.fbd.core.ModGui;
import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.tileentity.TileEntityBladeAlter;
import mod.fbd.tileentity.TileEntityBladeStand;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBladeAlter extends BlockHorizontalContainer {

	protected BlockBladeAlter() {
		super(Material.WOOD);
		this.setHardness(0.5F);
		this.setSoundType(SoundType.WOOD);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityBladeAlter(EnumFacing.getFront(meta));
	}


	@Override
	protected AxisAlignedBB getRealBoundingBox(IBlockState state) {
		return FULL_BLOCK_AABB;
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
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
    	if (!worldIn.isRemote){
    		TileEntity ent = worldIn.getTileEntity(pos);
    		if (ent instanceof TileEntityBladeStand){
    			InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)ent);
    		}
    	}
    	super.breakBlock(worldIn, pos, state);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
    	ItemStack stack = playerIn.getHeldItemMainhand();
        if (!worldIn.isRemote)
        {
    		// GUI表示
    		playerIn.openGui(Mod_FantomBlade.instance, ModGui.GUI_ID_BLADEALTER, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }


}
