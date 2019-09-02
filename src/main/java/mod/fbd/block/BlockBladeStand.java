package mod.fbd.block;

import mod.fbd.item.ItemKatana;
import mod.fbd.tileentity.TileEntityBladeStand;
import mod.fbd.tileentity.TileEntityBladeforge;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class BlockBladeStand extends BlockHorizontalContainer {
	private final EnumBladeStand standType;

	protected BlockBladeStand(Block.Properties property, EnumBladeStand stype) {
		super(property);
		standType = stype;
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader world) {
		return new TileEntityBladeStand();
	}

	@Override
	public TileEntity createTileEntity(IBlockState state, IBlockReader world) {
		return new TileEntityBladeforge(state);
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
    	super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
    	ItemStack stack = playerIn.getHeldItemMainhand();

    	TileEntity ent = worldIn.getTileEntity(pos);
    	if (ent instanceof TileEntityBladeStand){
    		if (stack.getItem() instanceof ItemKatana){
    			if (((TileEntityBladeStand)ent).setKatana(stack)){
    				if (!worldIn.isRemote){
        				stack.shrink(1);
        				if (stack.isEmpty()){
        					playerIn.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
        				}
    				}
    				//lightValue = ((TileEntityBladeStand)ent).getLightLevel();
    				//this.setLightLevel(((TileEntityBladeStand)ent).getLightLevel());
    			}
    		}else if(stack.isEmpty()){
    			ItemStack blade = ((TileEntityBladeStand)ent).getKatana();
    			if (!blade.isEmpty()){
    				if (!worldIn.isRemote){
    					playerIn.setHeldItem(EnumHand.MAIN_HAND, blade.copy());
    				}
    				//lightValue = ((TileEntityBladeStand)ent).getLightLevel();
    				//this.setLightLevel(((TileEntityBladeStand)ent).getLightLevel());
    			}
    		}
    	}
        return true;
    }

    @Override
    public int getLightValue(IBlockState state, IWorldReader world, BlockPos pos) {
    	TileEntity ent = world.getTileEntity(pos);
    	if (ent instanceof TileEntityBladeStand){
    		return ((TileEntityBladeStand)ent).getLightLevel();
    	}
    	return 0;
    }

    public static enum EnumBladeStand{
    	STAND1(0,2),
    	STAND2(1,1);

    	private int index;
    	private int inventorySize;

    	private EnumBladeStand(int idx, int size){
    		index = idx;
    		inventorySize = size;
    	}

    	public int getIndex(){return index;}
    	public int getInventorySize(){return this.inventorySize;}

    	private static EnumBladeStand[] values ={STAND1,STAND2};
		public static EnumBladeStand getFromIndex(int integer) {
			if (integer < 0 && values.length <= integer){
				integer = 0;
			}
			return values[integer];
		}
    }
}
