package mod.fbd.creative;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTabElmWepon  extends CreativeTabs {
	public CreativeTabElmWepon(String label){
		super(label);
	}

	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel(){
		return "factory box";
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(mod.fbd.block.BlockCore.block_bladealter);
	}

	@SideOnly(Side.CLIENT)
	public int getIconItemDamage(){
		return 8;
	}
}