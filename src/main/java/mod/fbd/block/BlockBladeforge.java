package mod.fbd.block;

import mod.fbd.intaractionobject.IntaractionObjectBladeForge;
import mod.fbd.network.MessageHandler;
import mod.fbd.tileentity.TileEntityBladeforge;
import mod.fbd.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class BlockBladeforge extends BlockContainer {
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
	public TileEntity createTileEntity(IBlockState state, IBlockReader world) {
		return new TileEntityBladeforge();
	}

	@Override
    protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder)
    {
		builder.add(FRAME);
    }

	public int getFrame(IBlockState state){
		return state.get(FRAME);
	}

	public IBlockState setFrame(IBlockState state, int stage){
		if (stage < STAGE_1 || stage > STAGE_5){stage = 0;}
		return state.with(FRAME, stage);
	}

	public boolean isEmpty(IBlockState state){
		return state.get(FRAME) == STAGE_1;
	}

	public boolean isFull(IBlockState state){
		return state.get(FRAME) == STAGE_2;
	}

	public boolean onWorks(IBlockState state){
		return state.get(FRAME) == STAGE_3;
	}

	public boolean finishWorks(IBlockState state){
		return state.get(FRAME) == STAGE_4;
	}

    @Override
    public int getLightValue(IBlockState state)
    {
    	if (this.onWorks(state)){
    		return 15;
    	}
    	return 0;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public void onReplaced(IBlockState state, World worldIn, BlockPos pos, IBlockState newState, boolean isMoving) {
    	if (!worldIn.isRemote){
    		TileEntity ent = worldIn.getTileEntity(pos);
    		if (ent instanceof TileEntityBladeforge){
    			for (ItemStack drop : ((TileEntityBladeforge)ent).getDropItem()){
    				ModUtil.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), drop, worldIn.rand);
    			}
    		}
    	}
    	super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public IItemProvider getItemDropped(IBlockState state, World worldIn, BlockPos pos, int fortune) {
    	if (this.isEmpty(state) || this.isFull(state)){
    		return Item.getItemFromBlock(this);
    	}
    	return null;
    }

    @Override
    protected ItemStack getSilkTouchDrop(IBlockState state)
    {
    	if (this.isEmpty(state) || this.isFull(state)){
            return new ItemStack(this,1);
    	}
    	return ItemStack.EMPTY;
    }

    @Override
    public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
    	ItemStack stack = playerIn.getHeldItemMainhand();
        if (!worldIn.isRemote)
        {
        	if (this.isEmpty(state) || (this.isFull(state) && stack.getItem() != Items.FLINT_AND_STEEL)){
        		// GUI表示
        		NetworkHooks.openGui((EntityPlayerMP)playerIn,
            			new IntaractionObjectBladeForge(pos),
            			(buf)->{
    						buf.writeInt(pos.getX());
    						buf.writeInt(pos.getY());
    						buf.writeInt(pos.getZ());
    					});
            	//playerIn.openGui(Mod_FantomBlade.instance, ModGui.GUI_ID_BLADEFORGE, worldIn, pos.getX(), pos.getY(), pos.getZ());
        		//playerIn.displayGui(new BlockAnvil.Anvil(worldIn, pos));
        	}else if (this.isFull(state) && stack.getItem() == Items.FLINT_AND_STEEL){
        		if (worldIn.getBlockState(pos.offset(EnumFacing.UP)).getMaterial() == Material.AIR){
            		// 精錬開始
            		MessageHandler.Send_MessageBladeforgeSmeltingStart(pos.getX(),pos.getY(),pos.getZ());
            		//Mod_FantomBlade.Net_Instance.sendToServer(new MessageBladeforgeSmeltingStart(pos.getX(),pos.getY(),pos.getZ()));
        		}else{
					playerIn.sendStatusMessage(new TextComponentString("can't smelting: need blank to upper"),false);
        		}
        	}else if (this.onWorks(state)){
        		// あっついからやけどする
        		playerIn.attackEntityFrom(DamageSource.IN_FIRE, 1.0F);
        	}
        }
        return true;
    }


	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
		if (worldIn.isRemote && this.isFull(state)){
			if (worldIn.getBlockState(pos.offset(EnumFacing.UP)).getBlock() == Blocks.FIRE){
				// 準備完了後上に火を乗せると精錬開始
        		MessageHandler.Send_MessageBladeforgeSmeltingStart(pos.getX(),pos.getY(),pos.getZ());
				//Mod_FantomBlade.Net_Instance.sendToServer(new MessageBladeforgeSmeltingStart(pos.getX(),pos.getY(),pos.getZ()));
			}
		}
    }

}
