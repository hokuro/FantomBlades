package mod.fbd.block;

import mod.fbd.item.ItemKatana;
import mod.fbd.tileentity.TileEntityBladeStand;
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
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBladeStand extends BlockHorizontalContainer {
	private final EnumBladeStand standType;

	protected BlockBladeStand(EnumBladeStand stype) {
		super(Material.GROUND);
		this.setHardness(1.0F);
		this.setResistance(3.0F);
		standType = stype;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityBladeStand(standType, EnumFacing.getFront(meta));
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
    				this.setLightLevel(((TileEntityBladeStand)ent).getLightLevel());
    			}
    		}else if(stack.isEmpty()){
    			ItemStack blade = ((TileEntityBladeStand)ent).getKatana();
    			if (!blade.isEmpty()){
    				if (!worldIn.isRemote){
    					playerIn.setHeldItem(EnumHand.MAIN_HAND, blade.copy());
    				}
    				this.setLightLevel(((TileEntityBladeStand)ent).getLightLevel());
    			}
    		}
    	}
        return true;
    }

    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
    {
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




	@Override
	protected AxisAlignedBB getRealBoundingBox(IBlockState state) {
		return FULL_BLOCK_AABB;
	}


}
