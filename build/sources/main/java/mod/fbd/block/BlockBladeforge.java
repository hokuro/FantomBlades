package mod.fbd.block;

import java.util.Random;

import mod.fbd.core.ModGui;
import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.network.MessageBladeforgeSmeltingStart;
import mod.fbd.tileentity.TileEntityBladeforge;
import mod.fbd.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class BlockBladeforge extends BlockContainer {
	public static final int STAGE_1=0;
	public static final int STAGE_2=1;
	public static final int STAGE_3=2;
	public static final int STAGE_4=3;
	public static final int STAGE_5=4;

	public static final PropertyInteger FRAME = PropertyInteger.create("frame", 0, 4);

	protected BlockBladeforge() {
		super(Material.GROUND);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FRAME, Integer.valueOf(0)));
		this.setHardness(0.8F);
		this.setSoundType(SoundType.STONE);
    }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {

		return new TileEntityBladeforge();
	}

	@Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FRAME});
    }

	public int getFrame(IBlockState state){
		return state.getValue(FRAME);
	}

	public IBlockState setFrame(IBlockState state, int stage){
		if (stage < STAGE_1 || stage > STAGE_5){stage = 0;}
		return state.withProperty(FRAME, stage);
	}

	public boolean isEmpty(IBlockState state){
		return state.getValue(FRAME) == STAGE_1;
	}

	public boolean isFull(IBlockState state){
		return state.getValue(FRAME) == STAGE_2;
	}

	public boolean onWorks(IBlockState state){
		return state.getValue(FRAME) == STAGE_3;
	}

	public boolean finishWorks(IBlockState state){
		return state.getValue(FRAME) == STAGE_4;
	}


    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return setFrame(this.getDefaultState(),meta);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return getFrame(state);
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

    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
    	if (!worldIn.isRemote){
    		TileEntity ent = worldIn.getTileEntity(pos);
    		if (ent instanceof TileEntityBladeforge){
    			for (ItemStack drop : ((TileEntityBladeforge)ent).getDropItem()){
    				ModUtil.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), drop, worldIn.rand);
    			}
    		}
    	}
    	super.breakBlock(worldIn, pos, state);
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
    	if (this.isEmpty(state) || this.isFull(state)){
    		return Item.getItemFromBlock(this);
    	}
    	return null;
    }

    protected ItemStack getSilkTouchDrop(IBlockState state)
    {
    	if (this.isEmpty(state) || this.isFull(state)){
            Item item = Item.getItemFromBlock(this);
            int i = 0;

            if (item.getHasSubtypes())
            {
                i = this.getMetaFromState(state);
            }

            return new ItemStack(item, 1, i);
    	}
    	return ItemStack.EMPTY;
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
    	ItemStack stack = playerIn.getHeldItemMainhand();
        if (!worldIn.isRemote)
        {
        	if (this.isEmpty(state) || (this.isFull(state) && stack.getItem() != Items.FLINT_AND_STEEL)){
        		// GUI表示
        		playerIn.openGui(Mod_FantomBlade.instance, ModGui.GUI_ID_BLADEFORGE, worldIn, pos.getX(), pos.getY(), pos.getZ());
        		//playerIn.displayGui(new BlockAnvil.Anvil(worldIn, pos));
        	}else if (this.isFull(state) && stack.getItem() == Items.FLINT_AND_STEEL){
        		if (worldIn.getBlockState(pos.offset(EnumFacing.UP)).getMaterial() == Material.AIR){
            		// 精錬開始
            		Mod_FantomBlade.Net_Instance.sendToServer(new MessageBladeforgeSmeltingStart(pos.getX(),pos.getY(),pos.getZ()));
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
        		Mod_FantomBlade.Net_Instance.sendToServer(new MessageBladeforgeSmeltingStart(pos.getX(),pos.getY(),pos.getZ()));
			}
		}
    }

}
