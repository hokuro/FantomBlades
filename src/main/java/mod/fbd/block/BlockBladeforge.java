package mod.fbd.block;

import javax.annotation.Nullable;

import mod.fbd.inventory.ContainerBladeforge;
import mod.fbd.network.MessageHandler;
import mod.fbd.tileentity.TileEntityBladeforge;
import mod.fbd.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class BlockBladeforge extends ContainerBlock {
	public static final int STAGE_1=0;
	public static final int STAGE_2=1;
	public static final int STAGE_3=2;
	public static final int STAGE_4=3;
	public static final int STAGE_5=4;

	public static final IntegerProperty FRAME = IntegerProperty.create("frame", 0, 4);

	protected BlockBladeforge(Block.Properties property) {
		super(property);
		this.setDefaultState(this.stateContainer.getBaseState().with(FRAME, Integer.valueOf(0)));
    }

	@Override
	public TileEntity createNewTileEntity(IBlockReader world) {

		return new TileEntityBladeforge();
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new TileEntityBladeforge();
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FRAME);
    }

	public int getFrame(BlockState state){
		return state.get(FRAME);
	}

	public BlockState setFrame(BlockState state, int stage){
		if (stage < STAGE_1 || stage > STAGE_5){stage = 0;}
		return state.with(FRAME, stage);
	}

	public boolean isEmpty(BlockState state){
		return state.get(FRAME) == STAGE_1;
	}

	public boolean isFull(BlockState state){
		return state.get(FRAME) == STAGE_2;
	}

	public boolean onWorks(BlockState state){
		return state.get(FRAME) == STAGE_3;
	}

	public boolean finishWorks(BlockState state){
		return state.get(FRAME) == STAGE_4;
	}

    @Override
    public int getLightValue(BlockState state){
    	if (this.onWorks(state)){
    		return 15;
    	}
    	return 0;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state){
        return BlockRenderType.MODEL;
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
    	if (!worldIn.isRemote){
    		TileEntity ent = worldIn.getTileEntity(pos);
    		if (state.getBlock() == BlockCore.block_bladeforge && newState.getBlock() == BlockCore.block_bladeforge && ent instanceof TileEntityBladeforge) {
    			((TileEntityBladeforge)ent).setField(TileEntityBladeforge.FIELD_STATE, newState.get(FRAME));
    		}else {
	    		if (ent instanceof TileEntityBladeforge){
	    			for (ItemStack drop : ((TileEntityBladeforge)ent).getDropItem()){
	    				ModUtil.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), drop, worldIn.rand);
	    			}
	    		}
    		}
    	}
    	super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, BlockRayTraceResult hit) {
    	ItemStack stack = playerIn.getHeldItemMainhand();
        if (!worldIn.isRemote)
        {
        	if (this.isEmpty(state) || (this.isFull(state) && stack.getItem() != Items.FLINT_AND_STEEL)){
        		// GUI表示
        		NetworkHooks.openGui((ServerPlayerEntity)playerIn,
            			new INamedContainerProvider() {
        					@Override
        					@Nullable
        					public Container createMenu(int id, PlayerInventory playerInv, PlayerEntity player) {
        						TileEntity ent = worldIn.getTileEntity(pos);
        						if (ent instanceof TileEntityBladeforge) {
        							return new ContainerBladeforge(id, playerInv, (TileEntityBladeforge)ent);
        						}
        						return null;
        					}

        					@Override
        					public ITextComponent getDisplayName() {
        						return new TranslationTextComponent("container.bladeforge");
        					}
        				},
            			(buf)->{
    						buf.writeInt(pos.getX());
    						buf.writeInt(pos.getY());
    						buf.writeInt(pos.getZ());
    					});
        	}else if (this.isFull(state) && stack.getItem() == Items.FLINT_AND_STEEL){
        		if (worldIn.getBlockState(pos.offset(Direction.UP)).getMaterial() == Material.AIR){
            		// i精錬開始
            		MessageHandler.Send_MessageBladeforgeSmeltingStart(pos.getX(),pos.getY(),pos.getZ());
            		//Mod_FantomBlade.Net_Instance.sendToServer(new MessageBladeforgeSmeltingStart(pos.getX(),pos.getY(),pos.getZ()));
        		}else{
					playerIn.sendStatusMessage(new StringTextComponent("can't smelting: need blank to upper"),false);
        		}
        	}else if (this.onWorks(state)){
        		// iあっついからやけどする
        		playerIn.attackEntityFrom(DamageSource.IN_FIRE, 1.0F);
        	}
        }
        return true;
    }


	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		if (worldIn.isRemote && this.isFull(state)){
			if (worldIn.getBlockState(pos.offset(Direction.UP)).getBlock() == Blocks.FIRE){
				// i準備完了後上に火を乗せると精錬開始
        		MessageHandler.Send_MessageBladeforgeSmeltingStart(pos.getX(),pos.getY(),pos.getZ());
			}
		}
    }

}