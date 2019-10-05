package mod.fbd.block;

import javax.annotation.Nullable;

import mod.fbd.inventory.ContainerBladeAlter;
import mod.fbd.tileentity.TileEntityBladeAlter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new TileEntityBladeAlter(state);
	}

	@Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder){
		super.fillStateContainer(builder);
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
    	if (!worldIn.isRemote){
    		TileEntity ent = worldIn.getTileEntity(pos);
    		if (ent instanceof TileEntityBladeAlter){
    			InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)ent);
    		}
    	}
    	super.onReplaced(state,worldIn, pos, state,isMoving);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, BlockRayTraceResult hit) {
    	ItemStack stack = playerIn.getHeldItemMainhand();
        if (!worldIn.isRemote) {
    		// GUI表示
    		NetworkHooks.openGui((ServerPlayerEntity)playerIn,
    				new INamedContainerProvider() {
						@Override
						@Nullable
						public Container createMenu(int id, PlayerInventory playerInv, PlayerEntity player) {
							TileEntity ent = worldIn.getTileEntity(pos);
							if (ent instanceof TileEntityBladeAlter) {
								return new ContainerBladeAlter(id, playerInv, (TileEntityBladeAlter)ent);
							}
							return null;
						}

						@Override
						public ITextComponent getDisplayName() {
							return new TranslationTextComponent("container.bladealter");
						}
    				},
    				(buf)->{
						buf.writeInt(pos.getX());
						buf.writeInt(pos.getY());
						buf.writeInt(pos.getZ());
					});
        }
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
}
