package mod.fbd.block;

import mod.fbd.core.ModGui;
import mod.fbd.core.Mod_FantomBlade;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockGunCustomizer extends Block {

	public BlockGunCustomizer() {
		super(Material.GROUND);
		this.setHardness(1.5F);
		this.setSoundType(SoundType.WOOD);
		this.setResistance(10.0F);
	}

	@Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		if (!worldIn.isRemote){
			playerIn.openGui(Mod_FantomBlade.instance, ModGui.GUI_ID_GUNCUSTOMAIZER, worldIn, (int)hitX, (int)hitY, (int)hitZ);
		}
        return false;
    }


}
