package mod.fbd.block;

import mod.fbd.intaractionobject.IntaractionObjectGunCustomizer;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class BlockGunCustomizer extends Block {

	public BlockGunCustomizer(Block.Properties property) {
		super(property);
	}

	@Override
	public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote){
			// GUI表示
    		NetworkHooks.openGui((EntityPlayerMP)playerIn,
        			new IntaractionObjectGunCustomizer(),
        			(buf)->{
						buf.writeInt(pos.getX());
						buf.writeInt(pos.getY());
						buf.writeInt(pos.getZ());
					});
        	//playerIn.openGui(Mod_FantomBlade.instance, ModGui.GUI_ID_GUNCUSTOMAIZER, worldIn, (int)hitX, (int)hitY, (int)hitZ);
		}
        return false;
    }


}
