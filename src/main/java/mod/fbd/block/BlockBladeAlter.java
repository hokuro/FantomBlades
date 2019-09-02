package mod.fbd.block;

import mod.fbd.intaractionobject.IntaractionObjectBladealter;
import mod.fbd.tileentity.TileEntityBladeAlter;
import mod.fbd.tileentity.TileEntityBladeStand;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class BlockBladeAlter extends BlockHorizontalContainer {

	protected BlockBladeAlter(Block.Properties property) {
		super(property);
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader world) {
		return new TileEntityBladeAlter();
	}

	@Override
	public TileEntity createTileEntity(IBlockState state, IBlockReader world) {
		return new TileEntityBladeAlter(state);
	}

	@Override
    protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder)
    {
		super.fillStateContainer(builder);
    }

	@Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }


    @Override
    public void onReplaced(IBlockState state, World worldIn, BlockPos pos, IBlockState newState, boolean isMoving) {
    	if (!worldIn.isRemote){
    		TileEntity ent = worldIn.getTileEntity(pos);
    		if (ent instanceof TileEntityBladeStand){
    			InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)ent);
    		}
    	}
    	super.onReplaced(state,worldIn, pos, state,isMoving);
    }

    @Override
    public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
    	ItemStack stack = playerIn.getHeldItemMainhand();
        if (!worldIn.isRemote)
        {
    		// GUI表示
    		NetworkHooks.openGui((EntityPlayerMP)playerIn,
        			new IntaractionObjectBladealter(pos),
        			(buf)->{
						buf.writeInt(pos.getX());
						buf.writeInt(pos.getY());
						buf.writeInt(pos.getZ());
					});
        	//playerIn.openGui(Mod_FantomBlade.instance, ModGui.GUI_ID_BLADEALTER, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }
}
