package mod.fbd.block;

import mod.fbd.item.katana.AbstractItemKatana;
import mod.fbd.tileentity.TileEntityBladeStand;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new TileEntityBladeStand(this.standType, state.get(this.FACING));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
    	if (state.getBlock() != newState.getBlock()) {
	    	if (!worldIn.isRemote){
	    		TileEntity ent = worldIn.getTileEntity(pos);
	    		if (ent instanceof TileEntityBladeStand){
	    			InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)ent);
	                worldIn.updateComparatorOutputLevel(pos, this);
	    		}
	    	}
	    	super.onReplaced(state, worldIn, pos, newState, isMoving);
    	}
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, BlockRayTraceResult hit) {
    	ItemStack stack = playerIn.getHeldItemMainhand();

    	TileEntity ent = worldIn.getTileEntity(pos);
    	if (ent instanceof TileEntityBladeStand){
    		if (stack.getItem() instanceof AbstractItemKatana){
    			((TileEntityBladeStand)ent).makeInventory(this.standType);
    			if (((TileEntityBladeStand)ent).setKatana(stack)){
    				if (!worldIn.isRemote){
        				stack.shrink(1);
        				if (stack.isEmpty()){
        					playerIn.setHeldItem(Hand.MAIN_HAND, ItemStack.EMPTY);
        				}
    				}
    				//lightValue = ((TileEntityBladeStand)ent).getLightLevel();
    				//this.setLightLevel(((TileEntityBladeStand)ent).getLightLevel());
    			}
    		}else if(stack.isEmpty()){
    			ItemStack blade = ((TileEntityBladeStand)ent).getKatana();
    			if (!blade.isEmpty()){
    				if (!worldIn.isRemote){
    					playerIn.setHeldItem(Hand.MAIN_HAND, blade.copy());
    				}
    				//lightValue = ((TileEntityBladeStand)ent).getLightLevel();
    				//this.setLightLevel(((TileEntityBladeStand)ent).getLightLevel());
    			}
    		}
    	}
        return true;
    }

    @Override
    public int getLightValue(BlockState state, IEnviromentBlockReader world, BlockPos pos) {
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

    @OnlyIn(Dist.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
}
